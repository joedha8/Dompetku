package com.rackspira.epenting.database;

/**
 * Created by kikiosha on 11/30/17.
 */

public class Hutang {
    private String id_hutang;
    private String pemberiPinjaman;
    private String nominal;
    private String status;
    private String tgl_pinjam;
    private String tgl_kembali;
    private String cicilan;
    private String tgl_bayar_cicilan;

    public Hutang() {
    }

    public Hutang(String id_hutang, String pemberiPinjaman, String nominal, String status, String tgl_pinjam, String tgl_kembali) {
        this.id_hutang = id_hutang;
        this.pemberiPinjaman = pemberiPinjaman;
        this.nominal = nominal;
        this.status = status;
        this.tgl_pinjam = tgl_pinjam;
        this.tgl_kembali = tgl_kembali;
    }

    public Hutang(String id_hutang, String pemberiPinjaman, String nominal, String status, String tgl_pinjam, String cicilan, String tgl_bayar_cicilan) {
        this.id_hutang = id_hutang;
        this.pemberiPinjaman = pemberiPinjaman;
        this.nominal = nominal;
        this.status = status;
        this.tgl_pinjam = tgl_pinjam;
        this.cicilan = cicilan;
        this.tgl_bayar_cicilan = tgl_bayar_cicilan;
    }

    public String getId_hutang() {
        return id_hutang;
    }

    public void setId_hutang(String id) {
        this.id_hutang = id;
    }

    public String getPemberiPinjaman() {
        return pemberiPinjaman;
    }

    public void setPemberiPinjaman(String pemberiPinjaman) {
        this.pemberiPinjaman = pemberiPinjaman;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getTgl_pinjam() {
        return tgl_pinjam;
    }

    public void setTgl_pinjam(String tgl_pinjam) {
        this.tgl_pinjam = tgl_pinjam;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCicilan() {
        return cicilan;
    }

    public void setCicilan(String cicilan) {
        this.cicilan = cicilan;
    }

    public String getTgl_bayar_cicilan() {
        return tgl_bayar_cicilan;
    }

    public void setTgl_bayar_cicilan(String tgl_bayar_cicilan) {
        this.tgl_bayar_cicilan = tgl_bayar_cicilan;
    }
}
