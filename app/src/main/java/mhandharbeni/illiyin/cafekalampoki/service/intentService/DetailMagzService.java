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
import mhandharbeni.illiyin.cafekalampoki.database.DetailMagz;
import mhandharbeni.illiyin.cafekalampoki.database.Magz;
import mhandharbeni.illiyin.cafekalampoki.database.helper.DetailMagzHelper;
import mhandharbeni.illiyin.cafekalampoki.database.helper.MagzHelper;
import sexy.code.Callback;
import sexy.code.FormBody;
import sexy.code.HttPizza;
import sexy.code.Request;
import sexy.code.RequestBody;
import sexy.code.Response;

/**
 * Created by root on 11/05/17.
 */

public class DetailMagzService extends IntentService implements ConnectivityChangeListener {
    public String STAT = "stat", KEY = "key", NAMA="nama", EMAIL= "email", PICTURE = "gambar";
    HttPizza client;
    EncryptedPreferences encryptedPreferences;

    String endUri;
    DetailMagzHelper dmgHelper;
    MagzHelper mgHelper;

    public DetailMagzService() {
        super("Detail Magz Service");
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
        dmgHelper = new DetailMagzHelper(getApplicationContext());
        mgHelper = new MagzHelper(getApplicationContext());

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (encryptedPreferences.getString("NETWORK","0").equalsIgnoreCase(getResources().getString(R.string.state_connection))){
            sycnDetMagz();
            stopSelf();
        }
    }
    public void sycnDetMagz(){
        checkPermission();
        if (encryptedPreferences.getString("ALLOWED", "0").equalsIgnoreCase("0")){
            if (encryptedPreferences.getString("NETWORK", "0").equalsIgnoreCase(getResources().getString(R.string.state_connection))){
                /*NETWORK AVAILABLE*/
                final Realm realms = Realm.getDefaultInstance();
                RealmResults<Magz> mainMagz =
                        realms.where(Magz.class).findAll();
                Log.d("DETAIL MAGZ", "sycnDetMagz: size "+mainMagz.size());
                if (mainMagz.size() > 0){
                    /*AMBIL DATA*/
                    for (int i=0;i<mainMagz.size();i++){
                        endUri = getString(R.string.server)+"/"+getString(R.string.vServer)+"//detMagz.php?id=";
                        Log.d("DETAIL MAGZ", "sycnDetMagz: id "+mainMagz.get(i).getId());
                        int id = mainMagz.get(i).getId();
                        endUri = endUri+String.valueOf(id);
                        Log.d("DETAIL MAGZ", "sycnDetMagz: endUri"+endUri);
                        Request request = client.newRequest()
                                .url(endUri)
                                .get()
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onResponse(Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    int status = jsonObject.getInt("status");
                                    if (status == 1){
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        if (jsonArray.length() > 0){
                                            for (int j=0;j<jsonArray.length();j++){
                                                JSONObject jsonSecond = jsonArray.getJSONObject(j);
                                                int idDetail = jsonSecond.getInt("id");
                                                if(!dmgHelper.checkDuplicate(idDetail)){
                                                    Log.d("RESPONSE DETAIL", "RESPONSE DETAIL onResponse: "+jsonSecond.getString("kd_magz"));
                                                    int idMagz = jsonSecond.getInt("kd_magz");;
                                                    String gambar = jsonSecond.getString("foto");
                                                    DetailMagz detailMagz = new DetailMagz();
                                                    detailMagz.setId(idDetail);
                                                    detailMagz.setId_magz(jsonSecond.getString("kd_magz"));
                                                    detailMagz.setFoto(gambar);
                                                    dmgHelper.addDetail(detailMagz);
                                                }
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
                    /*AMBIL DATA*/
                }
//                realms.close();
            }
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
