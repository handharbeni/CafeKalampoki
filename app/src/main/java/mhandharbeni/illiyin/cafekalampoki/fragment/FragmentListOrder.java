package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.database.Order;
import mhandharbeni.illiyin.cafekalampoki.database.helper.OrderHelper;

/**
 * Created by root on 07/05/17.
 */

public class FragmentListOrder extends Fragment {
    View v;
    TableLayout tableLayout;
    OrderHelper oh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oh = new OrderHelper(getActivity().getApplicationContext());

        v = inflater.inflate(R.layout.orderlist_layout, container, false);
        tableLayout = (TableLayout) v.findViewById(R.id.tblListOrder);
        insertDummyData();
        initData();
        return v;
    }
    public void insertDummyData(){
        for (int i=0;i<10;i++){
            if(!oh.checkDuplicate(i)){
                Order order = new Order();
                order.setId(i);
                order.setKdMenu(i+1);
                order.setNama("MENU "+i);
                order.setJumlah(i*2);
                order.setHarga(20000);
                oh.addOrder(order);
            }
        }
    }
    public void initData(){
        RealmResults<Order> results = oh.getOrder();
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

    }
}
