package com.mardesago.umatbertanya.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Febrian Reza on 21-Nov-17.
 */

public class cari {

    @SerializedName("artikel")
    @Expose
    private List<artikel> artikel = null;
    @SerializedName("referensi")
    @Expose
    private List<referensi> referensi = null;

    public List<artikel> getArtikel() {
        return artikel;
    }

    public void setArtikel(List<artikel> artikel) {
        this.artikel = artikel;
    }

    public List<referensi> getReferensi() {
        return referensi;
    }

    public void setReferensi(List<referensi> referensi) {
        this.referensi = referensi;
    }
}
