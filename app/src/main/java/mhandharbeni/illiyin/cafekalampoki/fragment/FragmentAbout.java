package mhandharbeni.illiyin.cafekalampoki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class FragmentAbout  extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraChangeListener {
    View v;
    private GoogleMap map;
    private MapScaleView scaleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_contact, container, false);
        scaleView = (MapScaleView) v.findViewById(R.id.mapView2);
//        CameraPosition cameraPosition = map.getCameraPosition();
//        scaleView.update(cameraPosition.zoom, cameraPosition.target.latitude);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        googleMap.setOnCameraMoveListener(this);
        googleMap.setOnCameraIdleListener(this);
        googleMap.setOnCameraChangeListener(this);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.07770360532252, -94.76820822805165), 12));
    }

    @Override
    public void onCameraMove() {
        update(map.getCameraPosition());
    }

    @Override
    public void onCameraIdle() {
        update(map.getCameraPosition());
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        update(cameraPosition);
    }

    private void update(CameraPosition cameraPosition) {
        scaleView.update(cameraPosition.zoom, cameraPosition.target.latitude);
    }

}
