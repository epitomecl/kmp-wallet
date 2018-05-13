package com.epitomecl.kmpwallet.api

import com.epitomecl.kmpwallet.model.Test
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface TestService {

    @GET("/test")
    fun getTest(@Path("param") param : Number): Observable<Test>
}