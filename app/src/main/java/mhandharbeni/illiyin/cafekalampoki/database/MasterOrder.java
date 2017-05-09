package mhandharbeni.illiyin.cafekalampoki.database;

import io.realm.RealmObject;

/**
 * Created by root on 07/05/17.
 */

public class MasterOrder extends RealmObject {
    int id;
    String nomeja;
    String atasnama;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeja() {
        return nomeja;
    }

    public void setNomeja(String nomeja) {
        this.nomeja = nomeja;
    }

    public String getAtasnama() {
        return atasnama;
    }

    public void setAtasnama(String atasnama) {
        this.atasnama = atasnama;
    }
}
