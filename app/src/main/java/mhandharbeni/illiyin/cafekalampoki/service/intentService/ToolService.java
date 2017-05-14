package mhandharbeni.illiyin.cafekalampoki.service.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.database.Magz;
import mhandharbeni.illiyin.cafekalampoki.database.Menu;
import mhandharbeni.illiyin.cafekalampoki.database.VersiDB;
import mhandharbeni.illiyin.cafekalampoki.database.helper.MenuHelper;
import mhandharbeni.illiyin.cafekalampoki.database.helper.VersiDBHelper;
import sexy.code.Callback;
import sexy.code.HttPizza;
import sexy.code.Request;
import sexy.code.Response;

/**
 * Created by root on 03/05/17.
 */

public class ToolService extends IntentService implements ConnectivityChangeListener {
    public String STAT = "stat", KEY = "key", NAMA="nama", EMAIL= "email", PICTURE = "gambar";
    MenuHelper mHelper;
    HttPizza client;
    EncryptedPreferences encryptedPreferences;

    String endUri;
    VersiDBHelper vDB;
    MenuHelper mh;

    public ToolService() {
        super("TOOL BUAT SIDEBAR MENU");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //init encrypting
        encryptedPreferences = new EncryptedPreferences.Builder(getBaseContext()).withEncryptionPassword(getString(R.string.key)).build();
        //init encrypting
        //httppizza
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        client = new HttPizza();
        //httppizza
        vDB = new VersiDBHelper(getApplicationContext());
        mh = new MenuHelper(getApplicationContext());
        endUri = getString(R.string.server)+"/"+getString(R.string.vServer)+"/getBlog.php";
        mHelper = new MenuHelper(getBaseContext());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (encryptedPreferences.getString("NETWORK","0").equalsIgnoreCase(getResources().getString(R.string.state_connection))){
            initDB();
            stopSelf();
        }
    }
    public void initDB(){
        checkPermission();
        if(encryptedPreferences.getString("ALLOWED", "0").equalsIgnoreCase("0")) {
            final Realm realmss = Realm.getDefaultInstance();
            RealmResults<VersiDB> versiDBs =
                    realmss.where(VersiDB.class).equalTo("id",1).findAll();
            Log.d("INIT DB", "initDB: "+versiDBs.size());
            if(versiDBs.size() > 0){
                final String versiDbx = versiDBs.get(0).getVersi();
                // check update
                Request request = client.newRequest()
                        .url(getResources().getString(R.string.server)+"/getVersiDB.php")
                        .get()
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("1")){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject jsonObjectArray = jsonArray.getJSONObject(0);
                                String newVersi = jsonObjectArray.getString("versi");
                                if(!versiDbx.equalsIgnoreCase(newVersi)){
                                    vDB.UpdateVersi(1, newVersi);
                                    updateMenu();
                                }
                            }
                            // init menu
                            initMenu();
                        } catch (JSONException | IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d("INITDB", "initDB: "+t.getMessage());
                    }
                });
            }else{
                // insert
                Request request = client.newRequest()
                        .url(getResources().getString(R.string.server)+"/getVersiDB.php")
                        .get()
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String status = jsonObject.getString("status");
                            if(status.equalsIgnoreCase("1")){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObjectArray = jsonArray.getJSONObject(i);
                                    String versi = jsonObjectArray.getString("versi");
                                    VersiDB vD = new VersiDB();
                                    vD.setId(1);
                                    vD.setVersi(versi);
                                    vDB.AddVersi(vD);
                                }
                                initMenu();
                            }
                        } catch (JSONException e) {
                            Log.d("INITDB", "initDB: "+e);
                            e.printStackTrace();
                        } catch (IOException e) {
                            Log.d("INITDB", "initDB: "+e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d("INITDB", "initDB: "+t.getMessage());
                    }
                });
            }
            realmss.close();
        }else{
        }
    }
    public void initMenu(){
        // first time init
        Request reques = client.newRequest()
                .url(getResources().getString(R.string.server)+"/getSideBarMenu.php")
                .get()
                .build();
        client.newCall(reques).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    if(status.equalsIgnoreCase("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if(jsonArray.length() > 0){
                            for (int i =0; i<jsonArray.length();i++){
                                JSONObject objectMenu = jsonArray.getJSONObject(i);
                                String menu = objectMenu.getString("menu");
                                Menu mnNew = new Menu();
                                mnNew.setId(i);
                                mnNew.setNama(menu);
                                mnNew.setAction("");
                                mh.AddMenu(mnNew);
                            }
                            //init side bar
                        }else{
                        }
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    public void updateMenu(){
        // update
        mh.deleteData();
        Request reques = client.newRequest()
                .url(getResources().getString(R.string.server)+"/getSideBarMenu.php")
                .get()
                .build();
        client.newCall(reques).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    if(status.equalsIgnoreCase("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if(jsonArray.length() > 0){
                            for (int i =0; i<jsonArray.length();i++){
                                JSONObject objectMenu = jsonArray.getJSONObject(i);
                                String menu = objectMenu.getString("menu");
                                Log.d("menuInit", "update onResponse: "+menu);
                                Menu mnNew = new Menu();
                                mnNew.setId(i);
                                mnNew.setNama(menu);
                                mnNew.setAction("");
                                mh.AddMenu(mnNew);
                            }
                            //init side bar
                        }else{
                        }
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    public void checkPermission(){
        Request request = client.newRequest()
                .url(getString(R.string.permission))
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) {
                try {
                    JSONObject resObject = new JSONObject(response.body().string());
                    String allowed = resObject.getString("kalampoki_allowed");
                    if (allowed.equalsIgnoreCase("true")){
                        encryptedPreferences.edit()
                                .putString("ALLOWED", "0")
                                .apply();
                    }else{
                        encryptedPreferences.edit()
                                .putString("ALLOWED", "1")
                                .apply();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        if(event.getState().getValue() == ConnectivityState.CONNECTED){
            // device has active internet connection
            encryptedPreferences.edit()
                    .putString("NETWORK", "1")
                    .apply();
        }
        else{
            // there is no active internet connection on this device
            encryptedPreferences.edit()
                    .putString("NETWORK", "0")
                    .apply();
        }
    }
}
