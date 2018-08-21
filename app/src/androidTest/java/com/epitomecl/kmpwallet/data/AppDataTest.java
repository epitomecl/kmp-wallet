package com.epitomecl.kmpwallet.data;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.epitomecl.kmpwallet.R;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class AppDataTest {
    Context appContext = InstrumentationRegistry.getTargetContext();
    AppData appData = new AppData(appContext, appContext.getSharedPreferences(appContext.getString(R.string.KMP), Context.MODE_PRIVATE));

    @Test
    public void test1LoginType() {
        System.out.println("loginAPITest BEGIN");

        AppData.LoginType loginType = AppData.LoginType.EMAIL_LOGIN;

        AppData.Companion.setLoginType(loginType);

        AppData.LoginType result = AppData.Companion.getLoginType();

        assertEquals(loginType, result);
    }

}
