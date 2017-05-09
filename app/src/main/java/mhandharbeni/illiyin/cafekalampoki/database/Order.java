package mhandharbeni.illiyin.cafekalampoki.database;

import io.realm.RealmObject;

/**
 * Created by root on 07/05/17.
 */

public class Order extends RealmObject {
    int id;
    int kdMenu;
    String nama;
    int jumlah;
    int harga;


    public int getKdMenu() {
        return kdMenu;
    }

    public void setKdMenu(int kdMenu) {
        this.kdMenu = kdMenu;
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

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
