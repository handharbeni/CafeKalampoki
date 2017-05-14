package mhandharbeni.illiyin.cafekalampoki.database.helper;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.database.Magz;

/**
 * Created by root on 04/05/17.
 */

public class MagzHelper {
    private static final String TAG = "MagzHelper";

    private Realm realm;
    private RealmResults<Magz> realmResult;
    public Context context;

    public MagzHelper(Context context) {
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

    public void AddMagz(Magz magz) {
        Magz mg = new Magz();
        mg.setId(magz.getId());
        mg.setJudul(magz.getJudul());
        mg.setFoto_cover(magz.getFoto_cover());
        mg.setDeskripsi(magz.getDeskripsi());
        mg.setTanggal(magz.getTanggal());
        realm.beginTransaction();
        realm.copyToRealm(mg);
        realm.commitTransaction();
    }

    public RealmResults<Magz> getMagz() {
        realmResult = realm.where(Magz.class).findAll();
        return realmResult;
    }

    public boolean checkDuplicate(int id) {
        realmResult = realm.where(Magz.class)
                .equalTo("id", id)
                .findAll();
        if (realmResult.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteData() {
        RealmResults<Magz> dataDesults = realm.where(Magz.class).findAll();
        realm.beginTransaction();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();
    }
}