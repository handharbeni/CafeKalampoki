package mhandharbeni.illiyin.cafekalampoki.database;

import io.realm.RealmObject;

/**
 * Created by root on 04/05/17.
 */

public class Magz extends RealmObject {
    int id;
    String judul, foto_cover, deskripsi, tanggal;

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
