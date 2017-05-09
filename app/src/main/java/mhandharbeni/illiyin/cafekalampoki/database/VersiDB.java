package mhandharbeni.illiyin.cafekalampoki.database;

import io.realm.RealmObject;

/**
 * Created by root on 01/05/17.
 */

public class VersiDB extends RealmObject {
    int id;
    String versi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersi() {
        return versi;
    }

    public void setVersi(String versi) {
        this.versi = versi;
    }
}
