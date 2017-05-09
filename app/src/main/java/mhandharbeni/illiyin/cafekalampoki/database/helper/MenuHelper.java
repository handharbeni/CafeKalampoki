package mhandharbeni.illiyin.cafekalampoki.database.helper;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.database.Menu;

/**
 * Created by root on 01/05/17.
 */

public class MenuHelper {
    private static final String TAG = "MenuHelper";

    private Realm realm;
    private RealmResults<Menu> realmResult;
    public Context context;

    public MenuHelper(Context context) {
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

    public void AddMenu(Menu mn) {
        Menu menus = new Menu();
        menus.setId(mn.getId());
        menus.setNama(mn.getNama());
        menus.setAction(mn.getAction());
        realm.beginTransaction();
        realm.copyToRealm(menus);
        realm.commitTransaction();
    }

    public RealmResults<Menu> getMenu() {
        realmResult = realm.where(Menu.class).findAll();
        return realmResult;
    }

    public RealmResults<Menu> getNoneUploaded() {
        realmResult = realm.where(Menu.class).equalTo("status", 0).findAll();
        return realmResult;
    }

    public void UpdatePresensi(String id, String field, String update) {
        realm.beginTransaction();
        Menu menus = realm.where(Menu.class).equalTo("id", id).findFirst();
        if (field == "nama") {
            menus.setNama(update);
        } else if (field == "action") {
            menus.setAction(update);
        }
        realm.commitTransaction();
    }

    public boolean checkDuplicate(String nama) {
        realmResult = realm.where(Menu.class)
                .equalTo("nama", nama)
                .findAll();
        if (realmResult.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteData() {
        RealmResults<Menu> dataDesults = realm.where(Menu.class).findAll();
        realm.beginTransaction();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();
    }
}