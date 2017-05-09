package mhandharbeni.illiyin.cafekalampoki.database;

import io.realm.RealmObject;

/**
 * Created by root on 01/05/17.
 */

public class Menu extends RealmObject {
    int id;
    String nama;
    String action;

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
