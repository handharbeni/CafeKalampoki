package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import mhandharbeni.illiyin.cafekalampoki.R;

/**
 * Created by root on 30/04/17.
 */

public class FragmentMenu  extends Fragment {
    View v;
    LinearLayout layoutFood, layoutBeverage;
    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_menu, container, false);
        layoutFood = (LinearLayout) v.findViewById(R.id.menuFood);
        layoutBeverage = (LinearLayout) v.findViewById(R.id.menuBeverage);

        layoutBeverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("kategori", "2");
                fragment = new FragmentDetailMenu();
                fragment.setArguments(bundle);
                changeFragment(fragment);
            }
        });
        layoutFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("kategori", "1");
                fragment = new FragmentDetailMenu();
                fragment.setArguments(bundle);
                changeFragment(fragment);
            }
        });
        return v;
    }
    public void changeFragment(Fragment fr){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameContainer, fr);
        ft.commit();
    }
}
