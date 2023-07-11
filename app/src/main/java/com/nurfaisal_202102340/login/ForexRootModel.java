package com.nurfaisal_202102340.login;

import com.google.gson.annotations.SerializedName;

import kotlin.jvm.internal.SerializedIr;

public class ForexRootModel
{
    @SerializedName("rates")
    private ForexRatesModel ratesModel;

    public ForexRootModel() {}

    public ForexRatesModel getRatesModel() { return ratesModel; }

    public void setRatesModel (ForexRatesModel ratesModel) {
        this.ratesModel = ratesModel; }
}

