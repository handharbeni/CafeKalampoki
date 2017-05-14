package mhandharbeni.illiyin.cafekalampoki.adapter.model;

/**
 * Created by root on 09/05/17.
 */

public class DetailMagzModel {
    int id;
    int id_magz;
    String foto;

    public DetailMagzModel(int id, int id_magz, String foto) {
        this.id = id;
        this.id_magz = id_magz;
        this.foto = foto;
    }

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
