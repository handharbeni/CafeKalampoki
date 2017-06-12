package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.pengrad.mapscaleview.MapScaleView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import mhandharbeni.illiyin.cafekalampoki.R;

/**
 * Created by root on 30/04/17.
 */

public class FragmentAbout  extends Fragment {
    View v;
    ImageView imageView7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_contact, container, false);
        imageView7 = (ImageView) v.findViewById(R.id.imageView7);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();
            }
        });
        return v;
    }


    public void openMaps(){
        String uri = "http://maps.google.com/maps?daddr=-7.971288,112.611998(" + "Kalampoki Quayhouse" + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        try
        {
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(getActivity().getApplicationContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }
}
