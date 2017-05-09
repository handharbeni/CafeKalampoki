package mhandharbeni.illiyin.cafekalampoki.service.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.Nullable;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.database.Blog;
import mhandharbeni.illiyin.cafekalampoki.database.helper.BlogHelper;
import sexy.code.Callback;
import sexy.code.HttPizza;
import sexy.code.Request;
import sexy.code.Response;

/**
 * Created by root on 04/05/17.
 */

public class BlogService extends IntentService implements ConnectivityChangeListener {
    public String STAT = "stat", KEY = "key", NAMA="nama", EMAIL= "email", PICTURE = "gambar";
    HttPizza client;
    EncryptedPreferences encryptedPreferences;

    String endUriHappening, endUriInside;
    BlogHelper bHelper;

    public BlogService() {
        super("Blog Service");
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
        bHelper = new BlogHelper(getApplicationContext());
        endUriHappening = getString(R.string.server)+"/"+getString(R.string.vServer)+"/getBlog.php?kategori=1";
        endUriInside = getString(R.string.server)+"/"+getString(R.string.vServer)+"/getBlog.php?kategori=2";
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (encryptedPreferences.getString("NETWORK","0").equalsIgnoreCase("1")){
            syncHappening();
            syncInside();
        }
    }
    public void syncHappening(){
        checkPermission();
        if (encryptedPreferences.getString("ALLOWED", "0").equalsIgnoreCase("0")){
            if (encryptedPreferences.getString("NETWORK", "0").equalsIgnoreCase("1")){
                /*NETWORK AVAILABLE*/
                Request request = client.newRequest()
                        .url(endUriHappening)
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
                                    if (bHelper.checkDuplicate(object.getInt("id"))){
                                        Blog blog = new Blog();
                                        blog.setKategori(1);
                                        blog.setId(Integer.valueOf(object.getString("id")));
                                        blog.setJudul(object.getString("judul"));
                                        blog.setIsi(object.getString("isi"));
                                        blog.setFoto(object.getString("foto"));
                                        blog.setNama_kategori(object.getString("kategori"));
                                        blog.setTanggal(object.getString("insert_date"));
                                        bHelper.AddBlog(blog);
                                    }
                                }
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
        }
    }
    public void syncInside(){
        checkPermission();
        if (encryptedPreferences.getString("ALLOWED", "0").equalsIgnoreCase("0")){
            if (encryptedPreferences.getString("NETWORK", "0").equalsIgnoreCase("1")){
                /*NETWORK AVAILABLE*/
                Request request = client.newRequest()
                        .url(endUriInside)
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
                                    if (bHelper.checkDuplicate(object.getInt("id"))){
                                        Blog blog = new Blog();
                                        blog.setKategori(2);
                                        blog.setId(Integer.valueOf(object.getString("id")));
                                        blog.setJudul(object.getString("judul"));
                                        blog.setIsi(object.getString("isi"));
                                        blog.setFoto(object.getString("foto"));
                                        blog.setNama_kategori(object.getString("kategori"));
                                        blog.setTanggal(object.getString("insert_date"));
                                        bHelper.AddBlog(blog);
                                    }
                                }
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
