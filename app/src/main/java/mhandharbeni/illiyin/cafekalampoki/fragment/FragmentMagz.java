package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dant.centersnapreyclerview.SnappingRecyclerView;
import com.master.simplerrecyclerviewadapter.SimplerRecyclerViewAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.adapter.model.MagzModel;
import mhandharbeni.illiyin.cafekalampoki.database.Magz;
import mhandharbeni.illiyin.cafekalampoki.database.helper.MagzHelper;

/**
 * Created by root on 30/04/17.
 */

public class FragmentMagz  extends Fragment {
    View v;
    SnappingRecyclerView snappingRecyclerView;
    ArrayList<MagzModel> dataModels;
    SimplerRecyclerViewAdapter.SimplerRowHolder simplerViewHolder;
    SimplerRecyclerViewAdapter adapter;
    MagzHelper mgHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_magz, container, false);
        snappingRecyclerView = (SnappingRecyclerView) v.findViewById(R.id.snapRC);
        mgHelper = new MagzHelper(getActivity().getApplicationContext());
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
                    LinearLayout llMagz;

                    @Override
                    public void create() {
                        txtIsi = links(R.id.txtIsi);
                        txtJudul = links(R.id.txtJudul);
                        cover = links(R.id.cover);
                        llMagz = links(R.id.llMagz);
                    }

                    @Override
                    public void bind(MagzModel model) {
                        isMyViewType(model);
                        llMagz.setTag(model.getId());
                        llMagz.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("IDMAGZ",Integer.valueOf(String.valueOf(v.getTag())));
                                Fragment fragment = new FragmentDetailMagz();
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.FrameContainer, fragment);
                                ft.commit();
                            }
                        });
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
        snappingRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void initData(){
        dataModels= new ArrayList<>();
        RealmResults<Magz> results = mgHelper.getMagz();
        Log.d("MAGZ FRAGMENT", "Total " +
                "initData: "+results.size());
        if (results.size() > 0){
            for (int i=0;i<results.size();i++){
                dataModels.add(new MagzModel(results.get(i).getId(),
                        results.get(i).getJudul(),
                        results.get(i).getFoto_cover(),
                        results.get(i).getDeskripsi(),
                        results.get(i).getTanggal()));
            }
        }
    }
}
