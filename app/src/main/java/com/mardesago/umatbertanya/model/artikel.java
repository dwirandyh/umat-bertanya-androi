package com.mardesago.umatbertanya.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dwi Randy Herdinanto on 11/3/2017.
 */

public class artikel {
    private String judul;
    private String deskripsi;
    private String id_artikel;
    private String plain_deskripsi;

    @SerializedName("mazhab")
    @Expose
    private List<mazhab> mazhab = null;

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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPlain_deskripsi() {
        return plain_deskripsi;
    }

    public void setPlain_deskripsi(String plain_deskripsi) {
        this.plain_deskripsi = plain_deskripsi;
    }

    public List<mazhab> getMazhab() {
        return mazhab;
    }

    public void setMazhab(List<mazhab> mazhab) {
        this.mazhab = mazhab;
    }

}
