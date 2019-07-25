package com.mardesago.umatbertanya.model;

import java.io.Serializable;

/**
 * Created by Dwi Randy Herdinanto on 11/3/2017.
 */

public class imam implements Serializable {
    private int id_imam;
    private String nama, keterangan;

    public imam() {

    }

    public imam(int id, String nama, String keterangan) {
        this.id_imam = id;
        this.nama = nama;
        this.keterangan = keterangan;
    }

    public int getId_imam() {
        return id_imam;
    }

    public void setId_imam(int id_imam) {
        this.id_imam = id_imam;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
