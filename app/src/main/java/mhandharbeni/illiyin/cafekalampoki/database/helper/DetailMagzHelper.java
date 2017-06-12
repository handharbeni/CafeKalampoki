package mhandharbeni.illiyin.cafekalampoki.database.helper;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.database.DetailMagz;
import mhandharbeni.illiyin.cafekalampoki.database.Magz;

/**
 * Created by root on 09/05/17.
 */

public class DetailMagzHelper {
    private static final String TAG = "DetailMagzHelper";

    private Realm realm;
    private RealmResults<DetailMagz> realmResult;
    public Context context;

    public DetailMagzHelper(Context context) {
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

    public void addDetail(DetailMagz detailMagz) {
        DetailMagz mg = new DetailMagz();
        mg.setId(detailMagz.getId());
        mg.setId_magz(detailMagz.getId_magz());
        mg.setFoto(detailMagz.getFoto());
        realm.beginTransaction();
        realm.copyToRealm(mg);
        realm.commitTransaction();
    }

    public RealmResults<DetailMagz> getDetailMagz(int idMagz) {
        DetailMagz dm = new DetailMagz();
        realmResult = realm.where(DetailMagz.class).equalTo("id_magz", String.valueOf(idMagz)).findAll();
        return realmResult;
    }

    public boolean checkDuplicate(int id) {
        realmResult = realm.where(DetailMagz.class)
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