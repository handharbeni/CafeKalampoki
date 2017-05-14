package mhandharbeni.illiyin.cafekalampoki.database;

import io.realm.RealmObject;

/**
 * Created by root on 09/05/17.
 */

public class DetailMagz extends RealmObject{
    int id;
    int id_magz;
    String foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_magz() {
        return id_magz;
    }

    public void setId_magz(int id_magz) {
        this.id_magz = id_magz;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
