package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dant.centersnapreyclerview.SnappingRecyclerView;
import com.master.simplerrecyclerviewadapter.SimplerRecyclerViewAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.adapter.model.DetailMagzModel;
import mhandharbeni.illiyin.cafekalampoki.database.DetailMagz;
import mhandharbeni.illiyin.cafekalampoki.database.helper.DetailMagzHelper;

/**
 * Created by root on 09/05/17.
 */

public class FragmentDetailMagz extends Fragment {
    SnappingRecyclerView snappingRecyclerView;
    ArrayList<DetailMagzModel> dataModels;
    SimplerRecyclerViewAdapter.SimplerRowHolder simplerViewHolder;
    SimplerRecyclerViewAdapter adapter;
    DetailMagzHelper dmgHelper;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = this.getArguments();
        int idMagz = args.getInt("IDMAGZ");
        v = inflater.inflate(R.layout.fragment_detail_magz, container, false);
        dmgHelper = new DetailMagzHelper(getActivity().getApplicationContext());
        snappingRecyclerView = (SnappingRecyclerView) v.findViewById(R.id.snapRC);
        initData(idMagz);
        createHolder();
        registerLayout();
        return v;
    }
    public void createHolder(){
        simplerViewHolder = new SimplerRecyclerViewAdapter.SimplerRowHolder<DetailMagzModel>() {

            @Override
            public SimplerViewHolder getAdapter(View view) {
                return new SimplerViewHolder(view) {

                    ImageView imageMagz;

                    @Override
                    public void create() {
                        imageMagz = links(R.id.imageMagz);
                    }

                    @Override
                    public void bind(DetailMagzModel model) {
                        isMyViewType(model);
                        Glide.with(getActivity().getApplicationContext()).load(model.getFoto()).into(imageMagz);
                    }
                };
            }
        };
    }
    public void registerLayout(){
        adapter = new SimplerRecyclerViewAdapter();
        adapter.addViewHolder(R.layout.item_detail_magz, simplerViewHolder);
        adapter.setList(dataModels);
        snappingRecyclerView.setAdapter(adapter);
    }
    public void initData(int idMagz){
        dataModels= new ArrayList<>();
        RealmResults<DetailMagz> resultDetail = dmgHelper.getDetailMagz(idMagz);
        if(resultDetail.size() > 0){
            for (int i=0;i<resultDetail.size();i++){
                Log.d("DETAIL MAGZ", "initData: "+resultDetail.get(i).getFoto());
                dataModels.add(new DetailMagzModel(resultDetail.get(i).getId(),
                                                    resultDetail.get(i).getId_magz(),
                                                    resultDetail.get(i).getFoto()));
            }
        }
    }
}
