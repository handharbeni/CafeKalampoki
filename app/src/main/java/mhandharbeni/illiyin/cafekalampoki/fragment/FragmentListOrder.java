package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.golovin.fluentstackbar.FluentSnackbar;
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
import java.lang.reflect.Array;

import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.database.Order;
import mhandharbeni.illiyin.cafekalampoki.database.helper.OrderHelper;
import mhandharbeni.illiyin.cafekalampoki.service.MainServices;
import sexy.code.Callback;
import sexy.code.FormBody;
import sexy.code.HttPizza;
import sexy.code.Request;
import sexy.code.RequestBody;
import sexy.code.Response;

/**
 * Created by root on 07/05/17.
 */

public class FragmentListOrder extends Fragment implements ConnectivityChangeListener {
    View v;
    TableLayout tableLayout;
    OrderHelper oh;
    Button btnOrder;

    private FluentSnackbar mFluentSnackbar;
    HttPizza client;
    EncryptedPreferences encryptedPreferences;

    SpinKitView spin_kit;
    RelativeLayout mainForm;
    EditText txtAtasNama, txtNoMeja;

    String endUriMasterOrder, endUriItemOrder;
    protected static final int REFRESH = 0;

    private Handler _hRedraw;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oh = new OrderHelper(getActivity().getApplicationContext());
        endUriMasterOrder = getResources().getString(R.string.server)+"/"+getResources().getString(R.string.vServer)+"/uploadMasterOrder.php";
        endUriItemOrder = getResources().getString(R.string.server)+"/"+getResources().getString(R.string.vServer)+"/uploadTransaksiOrder.php";
        if(savedInstanceState != null){
            ConnectionBuddy.getInstance().clearNetworkCache(this, savedInstanceState);
        }
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(getActivity().getApplicationContext()).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        client = new HttPizza();

        encryptedPreferences = new EncryptedPreferences.Builder(getActivity().getApplicationContext()).withEncryptionPassword(String.valueOf(R.string.key)).build();

        v = inflater.inflate(R.layout.orderlist_layout, container, false);
        spin_kit = (SpinKitView) v.findViewById(R.id.spin_kit);
        mainForm = (RelativeLayout) v.findViewById(R.id.mainForm);

        txtAtasNama = (EditText) v.findViewById(R.id.txtAtasNama);
        txtNoMeja = (EditText) v.findViewById(R.id.txtNoMeja);

        btnOrder = (Button) v.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOrder();
            }
        });

        tableLayout = (TableLayout) v.findViewById(R.id.tblListOrder);
        initData();
        _hRedraw=new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what) {
                    case REFRESH:
                        redrawEverything();
                        break;
                }
            }
        };
        return v;
    }
    private void redrawEverything()
    {
        tableLayout.invalidate();
        tableLayout.refreshDrawableState();
//        initData();
    }
    public void showSnackBar(String message){
        mFluentSnackbar = FluentSnackbar.create(getActivity());
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
    public void initData(){
//        tableLayout.removeAllViews();
        RealmResults<Order> results = oh.getOrder();
//        TableRow tableRowHeader = new TableRow(getActivity());
//        tableRowHeader.setPadding(0, 5, 0, 0);
//        tableRowHeader.setDividerPadding(2);
//        TextView txtNama= new TextView(getActivity());
//        txtNama.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        TextView txtJumlah= new TextView(getActivity());
//        txtJumlah.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        TextView txtHarga= new TextView(getActivity());
//        txtHarga.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        txtNama.setText("NAMA");
//        txtJumlah.setText(String.valueOf("JUMLAH"));
//        txtHarga.setText(String.valueOf("HARGA"));
//        tableRowHeader.addView(txtNama);
//        tableRowHeader.addView(txtJumlah);
//        tableRowHeader.addView(txtHarga);
//        tableLayout.addView(tableRowHeader);
        if(results.size() > 0){
            for (int i = 0;i<results.size();i++){
                TableRow tableRow = new TableRow(getActivity());
                TextView txtNamaMenu = new TextView(getActivity());
                TextView txtJumlahMenu = new TextView(getActivity());
                TextView txtHargaMenu = new TextView(getActivity());
                txtNamaMenu.setText(results.get(i).getNama());
                txtJumlahMenu.setText(String.valueOf(results.get(i).getJumlah()));
                txtHargaMenu.setText(String.valueOf(results.get(i).getHarga()));
                tableRow.addView(txtNamaMenu);
                tableRow.addView(txtJumlahMenu);
                tableRow.addView(txtHargaMenu);
                tableLayout.addView(tableRow);
            }
        }
    }
    public void doOrder(){
        if (!encryptedPreferences.getString("NETWORK", "0").equalsIgnoreCase(getResources().getString(R.string.state_connection))){
            mainForm.setVisibility(View.GONE);
            spin_kit.setVisibility(View.VISIBLE);
            sendMasterOrder();
        }else{
            showSnackBar("NO CONNECTION");
        }
    }
    public void sendMasterOrder(){
        String sAtasNama = txtAtasNama.getText().toString();
        String sNoMeja = txtNoMeja.getText().toString();

        if (!sAtasNama.equalsIgnoreCase("") || !sAtasNama.isEmpty() || !sNoMeja.equalsIgnoreCase("") || !sNoMeja.isEmpty()){
            /*send to server*/
            RequestBody requestBody = new FormBody.Builder()
                    .add("meja", sNoMeja)
                    .add("atasnama", sAtasNama)
                    .build();

            Request request = client.newRequest()
                    .url(endUriMasterOrder)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Response response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String status = jsonObject.getString("status");
                        if (status.equalsIgnoreCase("1")){
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() > 0){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id = jsonObject1.getString("id");
                                    sendItemOrder(id);
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
            mainForm.setVisibility(View.VISIBLE);
            spin_kit.setVisibility(View.GONE);
            _hRedraw.sendEmptyMessage(REFRESH);
        }else{
            /*show is empty*/
            mainForm.setVisibility(View.VISIBLE);
            spin_kit.setVisibility(View.GONE);
            showSnackBar("Atas Nama dan Nomor Meja tidak boleh Kosong!!");
        }
    }
    public void sendItemOrder(String id){
        RealmResults<Order> results = oh.getOrder();
        if(results.size() > 0){
            for (int i = 0;i<results.size();i++){
                final int idOrder = results.get(i).getId();
                RequestBody requestBody = new FormBody.Builder()
                        .add("kd_order", id)
                        .add("kd_menu", String.valueOf(results.get(i).getKdMenu()))
                        .add("qty", String.valueOf(results.get(i).getJumlah()))
                        .add("total_harga", String.valueOf(results.get(i).getHarga()))
                        .build();
                Request request = client.newRequest()
                        .url(endUriItemOrder)
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Response response) {
                        oh.deleteData(idOrder);
                    }
                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

            }
        }
    }
    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        if(event.getState().getValue() == ConnectivityState.CONNECTED){
            encryptedPreferences.edit()
                    .putString("NETWORK", "1")
                    .apply();
        }
        else{
            encryptedPreferences.edit()
                    .putString("NETWORK", "0")
                    .apply();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, this);
    }

    @Override
    public void onStop() {
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
        super.onStop();
    }
}
