package com.github.kettoleon.succumber.reports.gherkin;

public class Asset<T> {
    private final String asset;

    private final T data;

    public Asset(String assetName, T data) {
        this.asset = assetName;
        this.data = data;
    }

    public String getAsset() {
        return asset;
    }

    public T getData() {
        return data;
    }

}
