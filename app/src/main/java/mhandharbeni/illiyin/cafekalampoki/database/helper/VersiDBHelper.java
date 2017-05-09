package mhandharbeni.illiyin.cafekalampoki.database.helper;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.database.VersiDB;

/**
 * Created by root on 01/05/17.
 */

public class VersiDBHelper {
    private static final String TAG = "VDBHelper";

    private Realm realm;
    private RealmResults<VersiDB> realmResult;
    public Context context;

    public VersiDBHelper(Context context) {
        this.context = context;
        Realm.init(context);
        try {
            realm = Realm.getDefaultInstance();

        } catch (Exception e) {

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
    }

    public void AddVersi(VersiDB v) {
        VersiDB vDB = new VersiDB();
        vDB.setId(v.getId());
        vDB.setVersi(v.getVersi());
        realm.beginTransaction();
        realm.copyToRealm(vDB);
        realm.commitTransaction();
    }

    public RealmResults<VersiDB> getVersi() {
        realmResult = realm.where(VersiDB.class).findAll();
        return realmResult;
    }

    public void UpdateVersi(int id, String update) {
        realm.beginTransaction();
        VersiDB vDb = realm.where(VersiDB.class).equalTo("id", id).findFirst();
        vDb.setVersi(update);
        realm.commitTransaction();
    }

    public boolean checkDuplicate(String tanggalSekarang, String versi) {
        realmResult = realm.where(VersiDB.class)
                .equalTo("versi", versi)
                .findAll();
        if (realmResult.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteData() {
        RealmResults<VersiDB> dataDesults = realm.where(VersiDB.class).findAll();
        realm.beginTransaction();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();
    }
}
