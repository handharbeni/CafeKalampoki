package mhandharbeni.illiyin.cafekalampoki.database.helper;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.database.Blog;
import mhandharbeni.illiyin.cafekalampoki.database.MenuCafe;
import mhandharbeni.illiyin.cafekalampoki.database.VersiDB;

/**
 * Created by root on 04/05/17.
 */

public class MenuCafeHelper {
    private static final String TAG = "BlogHelper";

    private Realm realm;
    private RealmResults<MenuCafe> realmResult;
    public Context context;

    public MenuCafeHelper(Context context) {
        this.context = context;
        Realm.init(context);
        try {
            realm = Realm.getDefaultInstance();

        } catch (Exception e) {
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
    }

    public void AddMenu(MenuCafe menu) {
        MenuCafe mc = new MenuCafe();
        mc.setKategori(menu.getKategori());
        mc.setId(menu.getId());
        mc.setNama(menu.getNama());
        mc.setNama_kategori(menu.getNama_kategori());
        mc.setDeskripsi(menu.getDeskripsi());
        mc.setFoto(menu.getFoto());
        mc.setHarga(menu.getHarga());
        realm.beginTransaction();
        realm.copyToRealm(mc);
        realm.commitTransaction();
    }

    public RealmResults<MenuCafe> getMenu(int kategori) {
        realmResult = realm.where(MenuCafe.class).equalTo("kategori", kategori).findAll();
        return realmResult;
    }
    public RealmResults<MenuCafe> getMenuById(int id) {
        realmResult = realm.where(MenuCafe.class).equalTo("id", id).findAll();
        return realmResult;
    }
    public void UpdateVersi(int id, String update) {
        realm.beginTransaction();
        VersiDB vDb = realm.where(VersiDB.class).equalTo("id", id).findFirst();
        vDb.setVersi(update);
        realm.commitTransaction();
    }

    public boolean checkDuplicate(int id) {
        realmResult = realm.where(MenuCafe.class)
                .equalTo("id", id)
                .findAll();
        if (realmResult.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteData() {
        RealmResults<Blog> dataDesults = realm.where(Blog.class).findAll();
        realm.beginTransaction();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();
    }
}