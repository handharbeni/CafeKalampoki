package mhandharbeni.illiyin.cafekalampoki.adapter.model;

/**
 * Created by root on 05/05/17.
 */

public class MenuCafeModel {
    int kategori, id;
    String nama, nama_kategori, deskripsi, foto, harga;

    public MenuCafeModel(int kategori,
                         int id,
                         String nama,
                         String nama_kategori,
                         String deskripsi,
                         String foto,
                         String harga) {
        this.kategori = kategori;
        this.id = id;
        this.nama = nama;
        this.nama_kategori = nama_kategori;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.harga = harga;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
