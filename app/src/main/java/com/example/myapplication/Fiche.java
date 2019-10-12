package com.example.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fiche extends AppCompatActivity {


    EditText Name , Description;

    private static  final int REQUEST_LOCATION=1;

    Button getlocationBtn , insertData;
    TextView showLocationTxt;
    TextView showLatTxt ;
    LocationManager locationManager;
    String latitude = "0" , longitude = "0" ;

    DatabaseHelper dbHelper;
    private static final String TAG = "CapturePicture";
    static final int REQUEST_PICTURE_CAPTURE = 1;
    private ImageView image;
    private String pictureFilePath="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.INTERNET,
                                Manifest.permission.LOCATION_HARDWARE,
                                Manifest.permission.INSTALL_LOCATION_PROVIDER,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        },
                REQUEST_LOCATION);
        setContentView(R.layout.activity_fiche);
        Name=findViewById(R.id.edit_name) ;
        Description=findViewById(R.id.edit_description) ;
        showLocationTxt=findViewById(R.id.edit_long);
        showLatTxt = findViewById(R.id.edit_lat) ;
        insertData = findViewById(R.id.btn_insert_data) ;
        dbHelper=new DatabaseHelper(this);
        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result=dbHelper.insertDataToDatabase(Name.getText().toString(),Description.getText().toString(), latitude.toString(), longitude.toString(), pictureFilePath);//Pass the values of our edittext,Please note it

                if (result)
                {
                    Toast.makeText(Fiche.this, "Data Inserted Successfully...", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Fiche.this, "Data Not Inserted Successfully...", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button captureButton = findViewById(R.id.btnAdd);
        captureButton.setOnClickListener(capture);
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            captureButton.setEnabled(false);
        }
        getlocationBtn=findViewById(R.id.button_location);

        getlocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {

                    OnGPS();
                }
                else
                {


                    getLocation();
                }
            }
        });

    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }



    private View.OnClickListener capture = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                sendTakePictureIntent();
            }
        }
    };

    private void sendTakePictureIntent() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);

            File pictureFile = null;
            try {
                pictureFile = getPictureFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
            }
        }
    }

    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "GO_" + timeStamp + "_.jpg";
        File image = new File("/storage/emulated/0/" + pictureFile);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new  File(pictureFilePath);
            if(imgFile != null)            {
                addToGallery();
            }
        }
    }

    private void addToGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(pictureFilePath);
        Uri picUri = Uri.fromFile(f);
        galleryIntent.setData(picUri);
        this.sendBroadcast(galleryIntent);
    }


    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(Fiche.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Fiche.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText(longitude);
                showLatTxt.setText(latitude) ;
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText(longitude);
                showLatTxt.setText(latitude) ;
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText(longitude);
                showLatTxt.setText(latitude) ;
            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }


        }

    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}

