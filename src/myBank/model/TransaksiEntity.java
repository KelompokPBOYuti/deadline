/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.model;

import java.sql.Date;

/**
 *
 * @author Yoshino
 */
public class TransaksiEntity {
    private String idTransaksi;

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIdNasabah() {
        return idNasabah;
    }

    public void setIdNasabah(String idNasabah) {
        this.idNasabah = idNasabah;
    }

    public String getNamaNasabah() {
        return namaNasabah;
    }

    public void setNamaNasabah(String namaNasabah) {
        this.namaNasabah = namaNasabah;
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public double getNominalTs() {
        return nominalTs;
    }

    public void setNominalTs(double nominalTs) {
        this.nominalTs = nominalTs;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public Date getTglTs() {
        return tglTs;
    }

    public void setTglTs(Date tglTs) {
        this.tglTs = tglTs;
    }

    public Date getWaktuTs() {
        return waktuTs;
    }

    public void setWaktuTs(Date waktuTs) {
        this.waktuTs = waktuTs;
    }
    private String idNasabah;
    private String namaNasabah;
    private String idPegawai;
    private String jenisTransaksi;
    private double nominalTs;
    private String ket;
    private Date tglTs;
    private Date waktuTs;
    
    
}
