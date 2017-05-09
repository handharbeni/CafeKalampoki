package mhandharbeni.illiyin.cafekalampoki.adapter.model;

/**
 * Created by root on 05/05/17.
 */

public class BlogModel {
    int kategori;
    int id;
    String judul;
    String isi;
    String foto;
    String nama_kategori;
    String tanggal;


    public BlogModel(int kategori,
                     int id,
                     String judul,
                     String isi,
                     String foto,
                     String nama_kategori,
                     String tanggal){
        this.kategori = kategori;
        this.id = id;
        this.judul = judul;
        this.isi = isi;
        this.foto = foto;
        this.nama_kategori = nama_kategori;
        this.tanggal = tanggal;

    }
    public int getKategori() {
        return kategori;
    }

    public void setKategori(int kategori) {
        this.kategori = kategori;
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

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
