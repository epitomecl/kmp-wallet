## 아키텍처 소개 (Will)
 - https://docs.google.com/document/d/18keQ57NyTg51eSX-Q7DC160H919qU2LHuj6DBrC5yNo/edit

## KMP-Wallet 빌드 및 사용방법
- https://github.com/epitomecl/kmp-server  KMPWallet_Develop.md 도움말 참고(아래 링크)
- https://github.com/epitomecl/kmp-server/blob/master/KMPWallet_Develop.md

## HD Wallet 의 정의

### BIP39
* 지갑의 복구를 위한 seed값을 단어의 리스트로 특정하여 만드는 방식
* https://github.com/bitcoin/bips/blob/master/bip-0039.mediawiki
* 어떠한 wordlist가 있고(2048개의 단어) 이중 seed값을 복구하기 위한 n개의 단어를 뽑아 나열하여 Mnemonic code를 만든다. 단어의 개수를 보통 12개를 사용.

### BIP32
* 계층 결정적 지갑을 정의.
* https://github.com/bitcoin/bips/blob/master/bip-0032.mediawiki
* 상위 키로부터 여러 하위키를 유도하여 생성하는 함수를 정의함.
* 계층적 결정성 키 생성 방법은 다음 링크를 참고. https://wikidocs.net/14538
![HDKey](https://wikidocs.net/images/page/14538/21.PNG)

* 유도되는 확장 키에는 $2^{31}$개의 일반 자식 키와  $2^{31}$개의 강화된 자식 키가 있다. 이것들은 각각의 인덱스로 보여진다.
* 일반 자식 키의 인덱스는 0~ $2^{31}$-1
* 강화된 자식 키의 인덱스는  $2^{31}$~ $2^{32}$-1 이것은 범위 0~ $2^{31}$-1 숫자 i 로 표기되며 실제 인덱스로 사용될 때는 i + $2^{31}$를 사용한다.
* 강화된 키가 필요한 이유는 일반적인 자식 키 유도 공식의 헛점을 이용해 공격자가 확장 공개 키와 그로부터 파생된 임의의 개인 키를 확득하면 이로부터 확장 공개 키의 개인키와 그로부터 유래된 모든 키를 복구할수 있기 때문입니다. https://wikidocs.net/14539 참고
![HDKey_hardened](https://wikidocs.net/images/page/14539/24.PNG)
* 강화된 자식 키 유도 공식은 부모의 개인키를 해쉬하여 하위 키와 체인을 만드는데 사용하므로 부모 개인키를 모르면 자식 공개 키를 만들 수 없다.


### BIP43
* BIP32 의 계층구조의 자유도를 정리하여 여러 지갑들과 호환 가능하도록 논리적 구조를 만듬
* BIP32 트리 구조의 첫번째 위치를 다음과 같이 나타냄
~~~
m / purpose '/ *
~~~

### BIP44
* BIP32, BIP43 으로부터 정의된 계층 결정적 지갑의 논리적 계층을 설정한다.
* https://github.com/bitcoin/bips/blob/master/bip-0044.mediawiki
~~~
m / 목적 '/ 코인 타입 '/ 계정 '/ change '/ 주소 인덱스
m / 44H '/ 0H '/ 0H '/ 0 '/ 0  <== receive address path
m / 44H '/ 0H '/ 0H '/ 1 '/ 0  <== change address path
~~~
* 코인 타입은 0(비트코인), 1(테스트넷), ...
* 코인 타입은 다음 링크를 참고. https://github.com/satoshilabs/slips/blob/master/slip-0044.md
* 'H'의 뜻은 강화된 키(Hardened Keys)로 일반적인 확장키의 문제점을 배제하기 위함.
* 계정의 공개 키와 위의 계층 경로를 알면 하위 주소들을 유도해 낼 수 있다. 이를 통해 특정 계정의 잔액 조회를 위한 watch-only wallet을 만들 수 있다.
* 계정 조회시 주소 갭 제한 확인. 거래가 없는 연속적인 주소가 20개를 넘어설 경우 블록체인 검색을 중단한다.
* 외부 체인(receive address)이 해당되며 주소 갭 제한에 걸릴경우 스캔을 중단.
* 내부 체인(change address)은 거래없는 주소가 하나만 발견되어도 중단.

##### BIP44 계층 구조
~~~
Mnemonic
│
Seed
│
Master Private Key
│
Derive child key(EX: BIP44. purpose 44 hardened key)
│
Derive child key(EX: COIN-TYPE. 0 hardened key is BTC-mainnet. 1 hardened key is BTC-testnet...)
│
├Derive child key(EX: ACCOUNT. 0 hardened key) ─────┬─ Derive child key(EX: 0 normal key is receive chain) ─┬─ Derive child key(EX: 0 normal key is receive address)
├Derive child key(EX: ACCOUNT. 1 hardened key)      │                                                       ├─ Derive child key(EX: 1 normal key is receive address)
└Derive child key(EX: ACCOUNT. 2~2^31 hardened key) │                                                       ├─ Derive child key(EX: 2 normal key is receive address)
                                                    │                                                       └─ Derive child key(EX: 3~2^31 normal key is receive address)
                                                    └─ Derive child key(EX: 1 normal key is change chain) ──┬─ Derive child key(EX: 0 normal key is change address)
                                                                                                            ├─ Derive child key(EX: 1 normal key is change address)
                                                                                                            └─ Derive child key(EX: 2~2^31 normal key is change address)
~~~
