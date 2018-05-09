package com.epitomecl.kmpwallet.apis;

import com.epitomecl.kmpwallet.data.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by willpark on 2017. 9. 6..
 */

public class BaseService {

    protected static Retrofit.Builder mBuilder;

    protected static Retrofit createBuilder() {
        if(mBuilder==null){
            mBuilder =
                    new Retrofit.Builder()
//                            .client(CookieManagerClient.getCookieClient())
//                            .addConverterFactory(StringConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(Constants.SERVER_BASE_URL);
        }
        return mBuilder.build();
    }
}
