package com.epitomecl.kmpwallet.mvp;

/**
 * Created by elegantuniv on 2017. 6. 17..
 */

public interface BaseView {
    public void showLoading();
    public void hideLoading();
    public void onFailureRequest(String msg);

}
