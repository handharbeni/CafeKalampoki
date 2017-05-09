package mhandharbeni.illiyin.cafekalampoki.adapter.model;

/**
 * Created by root on 05/05/17.
 */

public class MagzModel {

    int id;
    String judul, foto_cover, deskripsi, tanggal;

    public MagzModel(int id, String judul, String foto_cover, String deskripsi, String tanggal){
        this.id = id;
        this.judul = judul;
        this.foto_cover = foto_cover;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getFoto_cover() {
        return foto_cover;
    }

    public void setFoto_cover(String foto_cover) {
        this.foto_cover = foto_cover;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
