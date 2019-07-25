package com.mardesago.umatbertanya.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Dwi Randy Herdinanto on 11/6/2017.
 */

public class daftarbacaan extends RealmObject {
    @Required
    @PrimaryKey
    private Integer id_daftar;
    @Required
    private String judul;
    private String id_artikel;
    private Integer id_mazhab;


    public int getId_daftar() {
        return id_daftar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getId_artikel() {
        return id_artikel;
    }

    public void setId_artikel(String id_artikel) {
        this.id_artikel = id_artikel;
    }

    public void setId_daftar(Integer id_daftar) {
        this.id_daftar = id_daftar;
    }

    public Integer getId_mazhab() {
        return id_mazhab;
    }

    public void setId_mazhab(Integer id_mazhab) {
        this.id_mazhab = id_mazhab;
    }
}
