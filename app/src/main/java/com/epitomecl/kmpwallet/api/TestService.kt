package com.epitomecl.kmpwallet.api

import android.support.annotation.VisibleForTesting
import com.epitomecl.kmp.core.wallet.UTXO
import com.epitomecl.kmpwallet.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface TestService {

    @GET("/test")
    fun getTest(@Path("param") param : Number): Observable<Test>

    @FormUrlEncoded
    @POST("/login")
    fun postLogin(@Field("id") id : String,
                  @Field("pw") pw : String): Observable<UserVO>

    @FormUrlEncoded
    @POST("/regist")
    fun postRegist(@Field("id") id : String,
                  @Field("pw") pw : String): Observable<UserVO>

    @FormUrlEncoded
    @POST("/balance-ex")
    fun getBalanceEx(@Field("xpub") xpub : String,
                     @Field("api_code") api_code : String): Observable<List<UTXO>>

    @FormUrlEncoded
    @POST("/activereceiveaddress")
    fun getActiveReceiveAddress(@Field("xpub") xpub : String,
                               @Field("api_code") api_code : String): Observable<ActiveAddress>

    @FormUrlEncoded
    @POST("/activechangeaddress")
    fun getActiveChangeAddress(@Field("xpub") xpub : String,
                     @Field("api_code") api_code : String): Observable<ActiveAddress>

    @FormUrlEncoded
    @POST("/spendtxo-count")
    fun getSpendTXOCount(@Field("address") address : String,
                     @Field("api_code") api_code : String): Observable<Int>

    @FormUrlEncoded
    @POST("/send")
    fun pushTX(@Field("hashtx") hashtx : String,
                         @Field("api_code") api_code : String): Observable<SendTXResult>

    @FormUrlEncoded
    @POST("/sharingdatalist")
    fun getSharingDataList(@Field("index") index : Int,
                        @Field("api_code") api_code : String): Observable<List<String>>

    @FormUrlEncoded
    @POST("/sharingdataone")
    fun getSharingDataOne(@Field("index") index : Int,
                           @Field("label") label : String,
                           @Field("api_code") api_code : String): Observable<SecretSharingVO>

    @FormUrlEncoded
    @POST("/sharingdatatwo")
    fun getSharingDataTwo(@Field("index") index : Int,
                           @Field("label") label : String,
                           @Field("api_code") api_code : String): Observable<SecretSharingVO>

    @FormUrlEncoded
    @POST("/backupsharingdataone")
    fun backupSharingDataOne(@Field("index") index : Int,
                          @Field("label") label : String,
                          @Field("shareddata") shareddata : String,
                          @Field("api_code") api_code : String): Observable<SecretSharingResult>

    @FormUrlEncoded
    @POST("/backupsharingdatatwo")
    fun backupSharingDataTwo(@Field("index") index : Int,
                          @Field("label") label : String,
                          @Field("shareddata") shareddata : String,
                          @Field("api_code") api_code : String): Observable<SecretSharingResult>

    @FormUrlEncoded
    @POST("/getencrypted")
    fun getEncrypted(@Field("index") index : Int,
                     @Field("label") label : String,
                     @Field("api_code") api_code : String): Observable<EncryptedResult>

    @FormUrlEncoded
    @POST("/setencrypted")
    fun setEncrypted(@Field("index") index : Int,
                     @Field("label") label : String,
                     @Field("encrypted") encrypted : String,
                     @Field("api_code") api_code : String): Observable<EncryptedResult>

    @GET("/api/tx/{txid}")
    fun getTransactionInfo(@Path("txid") txid : String): Observable<String>

    @VisibleForTesting
    @FormUrlEncoded
    @POST("/coinfromfaucet")
    fun coinFromFaucet(@Field("address") address : String,
               @Field("api_code") api_code : String): Observable<SendTXResult>

    @VisibleForTesting
    @FormUrlEncoded
    @POST("/addressfaucet")
    fun addressFaucet(@Field("api_code") api_code : String): Observable<ActiveAddress>

    @VisibleForTesting
    @FormUrlEncoded
    @POST("/checktx")
    fun checkTX(@Field("txid") txid : String,
                @Field("api_code") api_code : String): Observable<List<UTXO>>
}