package mhandharbeni.illiyin.cafekalampoki.service.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.database.MenuCafe;
import mhandharbeni.illiyin.cafekalampoki.database.helper.MenuCafeHelper;
import sexy.code.Callback;
import sexy.code.HttPizza;
import sexy.code.Request;
import sexy.code.Response;

/**
 * Created by root on 04/05/17.
 */

public class MenuCafeService extends IntentService implements ConnectivityChangeListener {
    public String STAT = "stat", KEY = "key", NAMA="nama", EMAIL= "email", PICTURE = "gambar";
    HttPizza client;
    EncryptedPreferences encryptedPreferences;

    String endUriFood, endUriBaverage;
    MenuCafeHelper mcHelper;

    public MenuCafeService() {
        super("Menu Cafe Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Menu Cafe Service", "onStartCommand: ");
        //init encrypting
        encryptedPreferences = new EncryptedPreferences.Builder(getBaseContext()).withEncryptionPassword(getString(R.string.key)).build();
        //init encrypting
        //httppizza
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        client = new HttPizza();
        //httppizza
        mcHelper = new MenuCafeHelper(getApplicationContext());
        endUriFood = getString(R.string.server)+"/"+getString(R.string.vServer)+"/getMenu.php?kategori=1";
        endUriBaverage = getString(R.string.server)+"/"+getString(R.string.vServer)+"/getMenu.php?kategori=2";
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (encryptedPreferences.getString("NETWORK","0").equalsIgnoreCase(getResources().getString(R.string.state_connection))){
            syncBaverage();
            syncFood();
            stopSelf();
        }
    }
    public void syncBaverage(){
        checkPermission();
        if (encryptedPreferences.getString("ALLOWED", "0").equalsIgnoreCase("0")){
                /*NETWORK AVAILABLE*/
                Request request = client.newRequest()
                        .url(endUriBaverage)
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
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    if (!mcHelper.checkDuplicate(object.getInt("id"))){
                                        MenuCafe menuCafe = new MenuCafe();
                                        menuCafe.setKategori(2);
                                        menuCafe.setId(object.getInt("id"));
                                        menuCafe.setNama(object.getString("nama"));
                                        menuCafe.setDeskripsi(object.getString("deskripsi"));
                                        menuCafe.setFoto(object.getString("foto"));
                                        menuCafe.setHarga(object.getString("harga"));
                                        mcHelper.AddMenu(menuCafe);
                                    }
                                }
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

        }
    }
    public void syncFood(){
        checkPermission();
        if (encryptedPreferences.getString("ALLOWED", "0").equalsIgnoreCase("0")){
                /*NETWORK AVAILABLE*/
                Request request = client.newRequest()
                        .url(endUriFood)
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
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    if (!mcHelper.checkDuplicate(object.getInt("id"))){
                                        MenuCafe menuCafe = new MenuCafe();
                                        menuCafe.setKategori(1);
                                        menuCafe.setId(object.getInt("id"));
                                        menuCafe.setNama(object.getString("nama"));
                                        menuCafe.setDeskripsi(object.getString("deskripsi"));
                                        menuCafe.setFoto(object.getString("foto"));
                                        menuCafe.setHarga(object.getString("harga"));
                                        mcHelper.AddMenu(menuCafe);
                                    }
                                }
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

        }
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
                } catch (JSONException | IOException e) {
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
            encryptedPreferences.edit()
                    .putString("NETWORK", "1")
                    .apply();
        }else{
            encryptedPreferences.edit()
                    .putString("NETWORK", "0")
                    .apply();
        }
    }
}
