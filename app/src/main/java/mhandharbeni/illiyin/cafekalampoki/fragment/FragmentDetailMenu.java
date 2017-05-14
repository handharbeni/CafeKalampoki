package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dant.centersnapreyclerview.SnappingRecyclerView;
import com.master.simplerrecyclerviewadapter.SimplerRecyclerViewAdapter;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;

import java.util.ArrayList;

import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.adapter.model.MenuCafeModel;
import mhandharbeni.illiyin.cafekalampoki.database.MenuCafe;
import mhandharbeni.illiyin.cafekalampoki.database.Order;
import mhandharbeni.illiyin.cafekalampoki.database.helper.MenuCafeHelper;
import mhandharbeni.illiyin.cafekalampoki.database.helper.OrderHelper;

/**
 * Created by root on 05/05/17.
 */

public class FragmentDetailMenu extends Fragment {
    SnappingRecyclerView snappingRecyclerView;
    ArrayList<MenuCafeModel> dataModels;
    SimplerRecyclerViewAdapter.SimplerRowHolder simplerViewHolder;
    SimplerRecyclerViewAdapter adapter;
    String kategori;
    View v;
    MenuCafeHelper mch;
    OrderHelper oh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.detail_menu, container, false);
        mch = new MenuCafeHelper(getActivity().getApplicationContext());
        oh = new OrderHelper(getActivity().getApplicationContext());
        snappingRecyclerView = (SnappingRecyclerView) v.findViewById(R.id.snapRC);
        Bundle args = getArguments();
        kategori = args.getString("kategori");
//        insertDummyData();
        initData(kategori);
        createHolder();
        registerLayout();

        return v;
    }
    public void createHolder(){
        simplerViewHolder = new SimplerRecyclerViewAdapter.SimplerRowHolder<MenuCafeModel>() {
            @Override
            public SimplerViewHolder getAdapter(View view) {
                return new SimplerViewHolder(view) {
                    TextView txtKategori, txtNamaMenu, txtDescriptionMenu, txtHarga;
                    ScrollableNumberPicker numberPicker;
                    ImageView imageMenu;
                    Button btnOrder, btnViewOrder;

                    @Override
                    public void create() {
                        txtKategori = links(R.id.txtKategori);
                        txtNamaMenu = links(R.id.txtNamaMenu);
                        txtDescriptionMenu = links(R.id.txtDescriptionMenu);
                        numberPicker = links(R.id.txtQty);
                        imageMenu = links(R.id.imageMenu);
                        btnOrder = links(R.id.btnOrder);
                        btnViewOrder = links(R.id.btnViewOrder);
                        txtHarga = links(R.id.txtHarga);
                    }

                    @Override
                    public void bind(MenuCafeModel model) {
                        isMyViewType(model);
                        txtKategori.setText(model.getNama_kategori());
                        txtNamaMenu.setText(model.getNama());
                        txtDescriptionMenu.setText(model.getDeskripsi());
                        txtHarga.setText(model.getHarga());
                        numberPicker.setValue(0);
                        numberPicker.setMinValue(0);
                        numberPicker.setListener(new ScrollableNumberPickerListener() {
                            @Override
                            public void onNumberPicked(int value) {
                                btnOrder.setTag(value);
                            }
                        });
                        btnOrder.setId(model.getId());
                        btnOrder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity().getApplicationContext(), String.valueOf(v.getId()+"-"+v.getTag()/*model.getId()*/), Toast.LENGTH_SHORT).show();
                                RealmResults<MenuCafe> menuCafes = mch.getMenuById(v.getId());
                                if (menuCafes.size() > 0){
                                    int qty = 0;
                                    if (v.getTag() != null){
                                        qty = Integer.valueOf(v.getTag().toString());
                                    }else{
                                        qty = 0;
                                    }
                                    Order order = new Order();
                                    order.setKdMenu(menuCafes.get(0).getId());
                                    order.setId(menuCafes.get(0).getId());
                                    order.setNama(menuCafes.get(0).getNama());
                                    order.setJumlah(qty);
                                    order.setHarga(Integer.valueOf(menuCafes.get(0).getHarga()));
                                    oh.addOrder(order);
                                }

                            }
                        });
                        btnViewOrder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.FrameContainer, new FragmentListOrder());
                                ft.commit();
                            }
                        });
                        Glide.with(getActivity().getApplicationContext()).load(model.getFoto()).into(imageMenu);
                    }
                };
            }
        };
    }
    public void insertDummyData(){
        /*FOOD*/
        for (int i=0;i<10;i++){
            MenuCafe menuCafe = new MenuCafe();
            menuCafe.setId(i);
            menuCafe.setNama("FOOD"+i);
            menuCafe.setFoto("http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png");
            menuCafe.setHarga(String.valueOf(i*20000));
            menuCafe.setDeskripsi(" DESKRIPSI FOOD "+i);
            menuCafe.setKategori(1);
            menuCafe.setNama_kategori("Food");
            if(!mch.checkDuplicate(i)){
                mch.AddMenu(menuCafe);
            }
        }
        /*BEVERAGE*/
        for (int i=100;i<110;i++){
            MenuCafe menuCafe = new MenuCafe();
            menuCafe.setId(i);
            menuCafe.setNama("BEVERAGE"+i);
            menuCafe.setFoto("http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png");
            menuCafe.setHarga(String.valueOf(i*20000));
            menuCafe.setDeskripsi(" DESKRIPSI BEVERAGE "+i);
            menuCafe.setKategori(2);
            menuCafe.setNama_kategori("Beverage");
            if(!mch.checkDuplicate(i)){
                mch.AddMenu(menuCafe);
            }
        }
    }
    public void registerLayout(){
        adapter = new SimplerRecyclerViewAdapter();
        adapter.addViewHolder(R.layout.item_menu, simplerViewHolder);
        adapter.setList(dataModels);
        snappingRecyclerView.setAdapter(adapter);
    }
    public void initData(String kategori){
        String sKategori;
        dataModels= new ArrayList<>();
        if (kategori.equalsIgnoreCase("1")){
            /*food*/
            sKategori = "Food";
        }else{
            /*beverage*/
            sKategori = "Beverage";
        }
        RealmResults<MenuCafe> mchMenu = mch.getMenu(Integer.valueOf(kategori));
        Log.d("Detail Menu", "initData: "+mchMenu.size());
        if(mchMenu.size() > 0){
            for (int i=0;i<mchMenu.size();i++){
                dataModels.add(new MenuCafeModel(
                        mchMenu.get(i).getKategori(),
                        mchMenu.get(i).getId(),
                        mchMenu.get(i).getNama(),
                        sKategori,
                        mchMenu.get(i).getDeskripsi(),
                        mchMenu.get(i).getFoto(),
                        mchMenu.get(i).getHarga()));
            }
        }

    }
}
