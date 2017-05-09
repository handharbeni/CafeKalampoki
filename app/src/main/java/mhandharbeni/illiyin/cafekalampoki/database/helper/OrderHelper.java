package mhandharbeni.illiyin.cafekalampoki.database.helper;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.database.MasterOrder;
import mhandharbeni.illiyin.cafekalampoki.database.Order;

/**
 * Created by root on 07/05/17.
 */

public class OrderHelper {
    private static final String TAG = "BlogHelper";

    private Realm realm;
    private RealmResults<Order> realmResult;
    public Context context;

    public OrderHelper(Context context) {
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

    public void addOrder(Order order) {
        Order od = new Order();
        od.setId(order.getId());
        od.setKdMenu(order.getKdMenu());
        od.setNama(order.getNama());
        od.setJumlah(order.getJumlah());
        od.setHarga(order.getHarga());
        realm.beginTransaction();
        realm.copyToRealm(od);
        realm.commitTransaction();
    }

    public RealmResults<Order> getOrder() {
        realmResult = realm.where(Order.class).findAll();
        return realmResult;
    }

    public boolean checkDuplicate(int id) {
        realmResult = realm.where(Order.class)
                .equalTo("id", id)
                .findAll();
        if (realmResult.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteData() {
        RealmResults<Order> dataDesults = realm.where(Order.class).findAll();
        realm.beginTransaction();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();
    }
}