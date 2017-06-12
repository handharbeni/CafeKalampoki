package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import mhandharbeni.illiyin.cafekalampoki.R;

/**
 * Created by root on 30/04/17.
 */

public class FragmentHome  extends Fragment {

    ImageView imgHome;
    View v;

    int status = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        imgHome = (ImageView) v.findViewById(R.id.imgHome);
        return v;
    }
    public void switchImage(){
        if (status == 0){
            /* bg home to nyuntek */
            Glide.with(getActivity().getApplicationContext()).load("").placeholder(R.drawable.bg_home_nyuntek).into(imgHome);
            status = 1;
        }else{
            /* nyuntek to bg home */
            Glide.with(getActivity().getApplicationContext()).load("").placeholder(R.drawable.bg_home).into(imgHome);
            status = 0;
        }
    }
}
