package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dant.centersnapreyclerview.SnappingRecyclerView;
import com.master.simplerrecyclerviewadapter.SimplerRecyclerViewAdapter;

import java.util.ArrayList;

import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.adapter.model.BlogModel;

/**
 * Created by root on 30/04/17.
 */

public class FragmentBlog  extends Fragment {
    SnappingRecyclerView snappingRecyclerView;
    ArrayList<BlogModel> dataModels;
    SimplerRecyclerViewAdapter.SimplerRowHolder simplerViewHolder;
    SimplerRecyclerViewAdapter adapter;
    View v;
    String kategori;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragmet_blog, container, false);
        snappingRecyclerView = (SnappingRecyclerView) v.findViewById(R.id.snapRC);
        Bundle args = getArguments();
        kategori = args.getString("kategori");
        initData(kategori);
        createHolder(kategori);
        registerLayout();
        return v;
    }

    public void createHolder(String kategori){
        if (kategori.equalsIgnoreCase("1")){
            /*HAPPENING*/
            simplerViewHolder = new SimplerRecyclerViewAdapter.SimplerRowHolder<BlogModel>() {
                @Override
                public SimplerViewHolder getAdapter(View view) {
                    return new SimplerViewHolder(view) {
                        RelativeLayout relativeLayoutHappening, RelativeLayoutInside;
                        TextView txtTitle, txtTanggal, txtDeskripsi;
                        ImageView cover;

                        @Override
                        public void create() {
                            relativeLayoutHappening = links(R.id.itemHappening);
                            RelativeLayoutInside = links(R.id.itemInside);
                            txtTitle = links(R.id.txtTitle);
                            txtTanggal = links(R.id.txtTanggal);
                            txtDeskripsi = links(R.id.txtDescription);
                            cover = links(R.id.imageCover);
                        }

                        @Override
                        public void bind(BlogModel model) {
                            isMyViewType(model);
                            relativeLayoutHappening.setVisibility(View.VISIBLE);
                            RelativeLayoutInside.setVisibility(View.GONE);
                            txtTitle.setText(model.getJudul());
                            txtTanggal.setText(model.getTanggal());
                            txtDeskripsi.setText(model.getIsi());
                            Glide.with(getActivity().getApplicationContext()).load(model.getFoto()).into(cover);
                        }
                    };
                }
            };

        }else if(kategori.equalsIgnoreCase("2")){
            /*INSIDE*/
            simplerViewHolder = new SimplerRecyclerViewAdapter.SimplerRowHolder<BlogModel>() {
                @Override
                public SimplerViewHolder getAdapter(View view) {
                    return new SimplerViewHolder(view) {
                        RelativeLayout relativeLayoutHappening, RelativeLayoutInside;
                        ImageView cover;

                        @Override
                        public void create() {
                            relativeLayoutHappening = links(R.id.itemHappening);
                            RelativeLayoutInside = links(R.id.itemInside);
                            cover = links(R.id.imageInside);
                        }

                        @Override
                        public void bind(BlogModel model) {
                            isMyViewType(model);
                            relativeLayoutHappening.setVisibility(View.GONE);
                            RelativeLayoutInside.setVisibility(View.VISIBLE);
                            Glide.with(getActivity().getApplicationContext()).load(model.getFoto()).into(cover);
                        }
                    };
                }
            };
        }
    }
    public void registerLayout(){
        adapter = new SimplerRecyclerViewAdapter();
        adapter.addViewHolder(R.layout.item_blog, simplerViewHolder);
        adapter.setList(dataModels);
        snappingRecyclerView.setAdapter(adapter);
    }
    public void initData(String kategori){
        if (kategori.equalsIgnoreCase("1")){
            /*HAPPENING*/
            dataModels= new ArrayList<>();
            dataModels.add(new BlogModel(
                    1,
                    1,
                    "TEST1",
                    "TEST ISI",
                    "http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png",
                    "HAPPENING",
                    "2017-05-01"));
            dataModels.add(new BlogModel(
                    1,
                    2,
                    "TEST2",
                    "TEST ISI",
                    "http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png",
                    "HAPPENING",
                    "2017-05-01"));
            dataModels.add(new BlogModel(
                    1,
                    3,
                    "TEST3",
                    "TEST ISI",
                    "http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png",
                    "HAPPENING",
                    "2017-05-01"));
        }else if(kategori.equalsIgnoreCase("2")){
            dataModels= new ArrayList<>();
            dataModels.add(new BlogModel(
                    2,
                    1,
                    "TEST1",
                    "TEST ISI",
                    "http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png",
                    "INSIDE",
                    "2017-05-01"));
            dataModels.add(new BlogModel(
                    2,
                    2,
                    "TEST2",
                    "TEST ISI",
                    "http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png",
                    "INSIDE",
                    "2017-05-01"));
            dataModels.add(new BlogModel(
                    2,
                    3,
                    "TEST3",
                    "TEST ISI",
                    "http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png",
                    "INSIDE",
                    "2017-05-01"));
        }
    }
}
