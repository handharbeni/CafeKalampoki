package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mhandharbeni.illiyin.cafekalampoki.R;

/**
 * Created by root on 30/04/17.
 */

public class FragmentFood  extends Fragment {
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }
}
