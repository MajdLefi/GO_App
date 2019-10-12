package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback {
    protected Button button_fiche ;
    GoogleMap map ;
    Button button_views , listview ;
    DatabaseHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dbHelper=new DatabaseHelper(this);



        button_fiche = (Button) findViewById(R.id.button2) ;
        button_fiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFiche() ;
            }
        });


        button_views = (Button) findViewById(R.id.btnView) ;
        button_views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewListContents() ;
            }
        });









        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById(R.id.map) ;
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }



    private void openFiche() {
        Intent intent = new Intent(this , Fiche.class) ;
        startActivity(intent);
    }


    private void openViewListContents () {
        Intent intent = new Intent(this , ViewListContents.class) ;
        startActivity(intent);
    }













    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap ;
        LatLng Yebni = new LatLng (36.856582, 10.206155) ;
        map.addMarker(new MarkerOptions().position(Yebni).title("Yebni"))  ;
        map.moveCamera(CameraUpdateFactory.newLatLng(Yebni));

    }

    public void Viewss (View view ) {

        if (map.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            map.setMapType (GoogleMap.MAP_TYPE_HYBRID) ;


        }

        else if (map.getMapType() == GoogleMap.MAP_TYPE_HYBRID) {

            map.setMapType (GoogleMap.MAP_TYPE_SATELLITE) ;

        }

        else if (map.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {

            map.setMapType (GoogleMap.MAP_TYPE_TERRAIN) ;



        }


    }








}

