package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import io.realm.RealmResults;
import mhandharbeni.illiyin.cafekalampoki.R;
import mhandharbeni.illiyin.cafekalampoki.adapter.model.BlogModel;
import mhandharbeni.illiyin.cafekalampoki.database.Blog;
import mhandharbeni.illiyin.cafekalampoki.database.helper.BlogHelper;

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
    BlogHelper bHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bHelper = new BlogHelper(getActivity().getApplicationContext());
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
        String sKategori = "HAPPENING";
        if (kategori.equalsIgnoreCase("1")){
            /*HAPPENING*/
            sKategori = "HAPPENING";
        }else if(kategori.equalsIgnoreCase("2")){
            /*INSIDE*/
            sKategori = "INSIDE";
        }
        RealmResults<Blog> resultBlog = bHelper.getBlog(Integer.valueOf(kategori));
        Log.d("FRAGMENT BLOG", "initData: "+resultBlog.size());
        dataModels= new ArrayList<>();
        if (resultBlog.size() > 0){
            for (int i=0;i<resultBlog.size();i++){
                Log.d("FRAGMENT BLOG", "initData: "+resultBlog.get(i).getFoto());
                dataModels.add(new BlogModel(
                        Integer.valueOf(kategori),
                        resultBlog.get(i).getId(),
                        resultBlog.get(i).getJudul(),
                        resultBlog.get(i).getIsi(),
                        resultBlog.get(i).getFoto(),
                        sKategori,
                        resultBlog.get(i).getTanggal()));
            }
        }
    }
}
