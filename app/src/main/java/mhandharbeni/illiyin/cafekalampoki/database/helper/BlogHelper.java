package mhandharbeni.illiyin.cafekalampoki.database.helper;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.database.Blog;
import mhandharbeni.illiyin.cafekalampoki.database.VersiDB;

/**
 * Created by root on 04/05/17.
 */

public class BlogHelper {
    private static final String TAG = "BlogHelper";

    private Realm realm;
    private RealmResults<Blog> realmResult;
    public Context context;

    public BlogHelper(Context context) {
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

    public void AddBlog(Blog blog) {
        Blog bl = new Blog();
        bl.setKategori(blog.getKategori());
        bl.setId(blog.getId());
        bl.setJudul(blog.getJudul());
        bl.setIsi(blog.getIsi());
        bl.setFoto(blog.getFoto());
        bl.setTanggal(blog.getTanggal());
        realm.beginTransaction();
        realm.copyToRealm(bl);
        realm.commitTransaction();
    }

    public RealmResults<Blog> getBlog(int kategori) {
        realmResult = realm.where(Blog.class).equalTo("kategori", kategori).findAll();
        return realmResult;
    }

    public void UpdateVersi(int id, String update) {
        realm.beginTransaction();
        VersiDB vDb = realm.where(VersiDB.class).equalTo("id", id).findFirst();
        vDb.setVersi(update);
        realm.commitTransaction();
    }

    public boolean checkDuplicate(int id) {
        realmResult = realm.where(Blog.class)
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