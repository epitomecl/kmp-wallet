package com.epitomecl.kmpwallet.api

import com.epitomecl.kmpwallet.model.Test
import com.epitomecl.kmpwallet.model.UTXO
import com.epitomecl.kmpwallet.model.UserVO
import io.reactivex.Observable
import retrofit2.http.*
import java.math.BigInteger

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
    @POST("/spendtxo-count")
    fun getSpendTXOCount(@Field("address") address : String,
                     @Field("api_code") api_code : String): Observable<Int>

    @FormUrlEncoded
    @POST("/send")
    fun pushTX(@Field("hashtx") hashtx : String,
                         @Field("api_code") api_code : String): Observable<String>
}