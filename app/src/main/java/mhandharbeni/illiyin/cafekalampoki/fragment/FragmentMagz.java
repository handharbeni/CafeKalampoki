package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dant.centersnapreyclerview.SnappingRecyclerView;
import com.master.simplerrecyclerviewadapter.SimplerRecyclerViewAdapter;

import java.util.ArrayList;

import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.adapter.model.MagzModel;

/**
 * Created by root on 30/04/17.
 */

public class FragmentMagz  extends Fragment {
    View v;
    SnappingRecyclerView snappingRecyclerView;
    ArrayList<MagzModel> dataModels;
    SimplerRecyclerViewAdapter.SimplerRowHolder simplerViewHolder;
    SimplerRecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_magz, container, false);
        snappingRecyclerView = (SnappingRecyclerView) v.findViewById(R.id.snapRC);
        initData();
        createHolder();
        registerLayout();
        return v;
    }
    public void createHolder(){
       simplerViewHolder = new SimplerRecyclerViewAdapter.SimplerRowHolder<MagzModel>() {

            @Override
            public SimplerViewHolder getAdapter(View view) {
                return new SimplerViewHolder(view) {

                    TextView txtJudul, txtIsi;
                    ImageView cover;

                    @Override
                    public void create() {
                        txtIsi = links(R.id.txtIsi);
                        txtJudul = links(R.id.txtJudul);
                        cover = links(R.id.cover);
                    }

                    @Override
                    public void bind(MagzModel model) {
                        isMyViewType(model);
                        txtJudul.setText(model.getJudul());
                        txtIsi.setText(model.getDeskripsi());
                        Glide.with(getActivity().getApplicationContext()).load(model.getFoto_cover()).into(cover);
                    }
                };
            }
        };
    }
    public void registerLayout(){
        adapter = new SimplerRecyclerViewAdapter();
        adapter.addViewHolder(R.layout.item_magz, simplerViewHolder);
        adapter.setList(dataModels);
        snappingRecyclerView.setAdapter(adapter);
    }
    public void initData(){
        dataModels= new ArrayList<>();
        dataModels.add(new MagzModel(1,
                "TEST",
                "http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png",
                "TEST DESKRIPSI",
                "2017-05-01"));
        dataModels.add(new MagzModel(1,
                "TEST2",
                "http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png",
                "TEST DESKRIPSI",
                "2017-05-01"));
        dataModels.add(new MagzModel(1,
                "TEST3",
                "http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png",
                "TEST DESKRIPSI",
                "2017-05-01"));
        dataModels.add(new MagzModel(1,
                "TEST4",
                "http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png",
                "TEST DESKRIPSI",
                "2017-05-01"));
    }
}
