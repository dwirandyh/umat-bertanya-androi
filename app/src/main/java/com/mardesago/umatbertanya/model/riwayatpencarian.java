package com.mardesago.umatbertanya.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class riwayatpencarian extends RealmObject {
    @Required
    @PrimaryKey
    private Integer id_riwayat;
    @Required
    private String keyword;

    public Integer getId_riwayat() {
        return id_riwayat;
    }

    public void setId_riwayat(Integer id_riwayat) {
        this.id_riwayat = id_riwayat;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}