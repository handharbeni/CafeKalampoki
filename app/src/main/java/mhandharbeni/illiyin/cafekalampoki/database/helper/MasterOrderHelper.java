package mhandharbeni.illiyin.cafekalampoki.database.helper;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.database.MasterOrder;

/**
 * Created by root on 07/05/17.
 */

public class MasterOrderHelper {
    private static final String TAG = "BlogHelper";

    private Realm realm;
    private RealmResults<MasterOrder> realmResult;
    public Context context;

    public MasterOrderHelper(Context context) {
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

    public void addMasterOrder(MasterOrder masterOrder) {
        MasterOrder mo = new MasterOrder();
        mo.setId(masterOrder.getId());
        mo.setAtasnama(masterOrder.getAtasnama());
        mo.setNomeja(masterOrder.getNomeja());
        realm.beginTransaction();
        realm.copyToRealm(mo);
        realm.commitTransaction();
    }

    public RealmResults<MasterOrder> getMasterOrder(int id) {
        realmResult = realm.where(MasterOrder.class).equalTo("id", id).findAll();
        return realmResult;
    }

    public boolean checkDuplicate(int id) {
        realmResult = realm.where(MasterOrder.class)
                .equalTo("id", id)
                .findAll();
        if (realmResult.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteData() {
        RealmResults<MasterOrder> dataDesults = realm.where(MasterOrder.class).findAll();
        realm.beginTransaction();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();
    }
}