package mhandharbeni.illiyin.cafekalampoki;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.golovin.fluentstackbar.FluentSnackbar;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.database.Menu;
import mhandharbeni.illiyin.cafekalampoki.database.VersiDB;
import mhandharbeni.illiyin.cafekalampoki.database.helper.MenuHelper;
import mhandharbeni.illiyin.cafekalampoki.database.helper.VersiDBHelper;
import mhandharbeni.illiyin.cafekalampoki.fragment.FragmentAbout;
import mhandharbeni.illiyin.cafekalampoki.fragment.FragmentBlog;
import mhandharbeni.illiyin.cafekalampoki.fragment.FragmentHome;
import mhandharbeni.illiyin.cafekalampoki.fragment.FragmentListOrder;
import mhandharbeni.illiyin.cafekalampoki.fragment.FragmentMagz;
import mhandharbeni.illiyin.cafekalampoki.fragment.FragmentMenu;
import mhandharbeni.illiyin.cafekalampoki.service.MainServices;
import sexy.code.Callback;
import sexy.code.HttPizza;
import sexy.code.Request;
import sexy.code.Response;

public class MainActivity extends AppCompatActivity implements ConnectivityChangeListener,Drawer.OnDrawerItemClickListener {
    Toolbar toolbar;
    HttPizza client;
    EncryptedPreferences encryptedPreferences;
    private FluentSnackbar mFluentSnackbar;
    VersiDBHelper vDB;
    MenuHelper mh;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!MainServices.serviceRunning){
            Intent i = new Intent(this, MainServices.class);
            startService(i);
        }

        if(savedInstanceState != null){
            ConnectionBuddy.getInstance().clearNetworkCache(this, savedInstanceState);
        }
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        client = new HttPizza();

        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword(String.valueOf(R.string.key)).build();

        vDB = new VersiDBHelper(getApplicationContext());
        mh = new MenuHelper(getApplicationContext());

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbarDefault);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        String[] menu =  getResources().getStringArray(R.array.menu);
        initDB();
        initSideBar();
    }
    public void showSnackBar(String message){
        mFluentSnackbar = FluentSnackbar.create(this);
        mFluentSnackbar.create(message)
                .maxLines(2)
                .backgroundColorRes(R.color.colorPrimary)
                .textColorRes(R.color.md_white_1000)
                .duration(Snackbar.LENGTH_SHORT)
                .actionText("DISMISS")
                .actionTextColorRes(R.color.colorAccent)
                .important()
                .action(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();
    }
    public void initDB(){
        checkPermission();
        if(encryptedPreferences.getString("ALLOWED", "0").equalsIgnoreCase("0")) {
            final RealmResults<VersiDB> versiDB = vDB.getVersi();
            if(versiDB.size() > 0){
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
                                String versi = versiDB.get(0).getVersi();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject jsonObjectArray = jsonArray.getJSONObject(0);
                                String newVersi = jsonObjectArray.getString("versi");
                                if(!versi.equalsIgnoreCase(newVersi)){
                                    vDB.UpdateVersi(1, newVersi);
                                    updateMenu();
                                }
                            }
                            // init menu
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
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
        }else{
            showSnackBar("UNKNOWN ERROR");
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
                                Log.d("menuInit", "new onResponse: "+menu);
                                Menu mnNew = new Menu();
                                mnNew.setId(i);
                                mnNew.setNama(menu);
                                mnNew.setAction("");
                                mh.AddMenu(mnNew);
                            }
                            //init side bar
                            initSideBar();
                        }else{
                            showSnackBar("NO DATA AVAILABLE");
                        }
                    }else{
                        showSnackBar("NO DATA AVAILABLE");
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
                            initSideBar();
                        }else{
                            showSnackBar("NO DATA AVAILABLE");
                        }
                    }else{
                      showSnackBar("NO DATA AVAILABLE");
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
                        Log.d("CHECK PERMISSION", "checkPermission: "+allowed);
                        encryptedPreferences.edit()
                                .putString("ALLOWED", "0")
                                .apply();
                    }else{
                        Log.d("CHECK PERMISSION", "checkPermission: "+allowed);
                        encryptedPreferences.edit()
                                .putString("ALLOWED", "1")
                                .apply();
                    }
                } catch (JSONException e) {
                    Log.d("CHECK PERMISSION", "checkPermission: "+e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d("CHECK PERMISSION", "checkPermission: "+e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    public void initSideBar(){
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withRootView(R.id.drawer_layout)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withSelectedItem(-1)
                .withFireOnInitialOnClick(false)
                .withOnDrawerItemClickListener(this)
                .withFooter(R.layout.footer)
                .build();
        RealmResults<Menu> mHelpers = mh.getMenu();
        if(mHelpers.size() > 0){
            PrimaryDrawerItem items = new PrimaryDrawerItem()
                    .withIdentifier(100)
                    .withName("")
                    .withTag(100);
            result.addItem(items);
            for (int i=0;i<mHelpers.size();i++){
                PrimaryDrawerItem item = new PrimaryDrawerItem()
                        .withIdentifier(i)
                        .withName(mHelpers.get(i).getNama())
                        .withTag(i);
                result.addItem(item);
            }
        }
    }
    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        Log.d("CONNECTION", "onConnectionChange: "+event.getState().getValue());
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
    public void changeFragment(Fragment fragment){
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.FrameContainer, fragment);
        fm.commit();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (!MainServices.serviceRunning){
            Intent i = new Intent(this, MainServices.class);
            startService(i);
        }
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, this);
    }

    @Override
    public void onStop() {
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
        super.onStop();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        Fragment fr = new FragmentHome();
        if(drawerItem.getIdentifier() == 0){
            /*HOME*/
            setTitle("Home");
            fr = new FragmentHome();
        }else if(drawerItem.getIdentifier() == 1){
            /*INSIDE*/
            setTitle("What's Inside");
            fr = new FragmentBlog();
            Bundle bundle = new Bundle();
            bundle.putString("kategori", "2");
            fr.setArguments(bundle);
        }else if(drawerItem.getIdentifier() == 2){
            /*HAPPENING*/
            setTitle("What's Happening");
            fr = new FragmentBlog();
            Bundle bundle = new Bundle();
            bundle.putString("kategori", "1");
            fr.setArguments(bundle);
        }else if(drawerItem.getIdentifier() == 3){
            /*MAGZ*/
            setTitle("Kala Magazine");
            fr = new FragmentMagz();
        }else if(drawerItem.getIdentifier() == 4){
            /*MENU*/
            setTitle("Menu");
            fr = new FragmentMenu();
        }else if(drawerItem.getIdentifier() == 5){
            /*CONTACT*/
            fr = new FragmentAbout();
        }
        changeFragment(fr);
        return false;
    }
    public void setTitle(String title){
        txtTitle.setText(title);
    }
}
