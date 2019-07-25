package com.mardesago.umatbertanya.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Dwi Randy Herdinanto on 11/6/2017.
 */

public class riwayat extends RealmObject {
    @Required
    @PrimaryKey
    private Integer id_riwayat;

    private String id_artikel;
    private String tgl, keterangan;

    private Integer id_mazhab;

    public int getId_riwayat() {
        return id_riwayat;
    }

    public void setId_riwayat(int id_riwayat) {
        this.id_riwayat = id_riwayat;
    }

    public String getId_artikel() {
        return id_artikel;
    }

    public void setId_artikel(String id_artikel) {
        this.id_artikel = id_artikel;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setId_riwayat(Integer id_riwayat) {
        this.id_riwayat = id_riwayat;
    }

    public Integer getId_mazhab() {
        return id_mazhab;
    }

    public void setId_mazhab(Integer id_mazhab) {
        this.id_mazhab = id_mazhab;
    }
}
