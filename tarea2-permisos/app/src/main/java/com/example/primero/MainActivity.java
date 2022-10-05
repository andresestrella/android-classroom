package com.example.primero;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private Button cancelButton;
    private Switch storageSwitch, locationSwitch, cameraSwitch, phoneSwitch, contactSwitch;

    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final int LOCATION_PERMISSION_CODE = 101;
    private static final int CAMERA_PERMISSION_CODE = 102;
    private static final int PHONE_PERMISSION_CODE = 103;
    private static final int CONTACT_PERMISSION_CODE = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkPermissions();
        setOnClickListeners();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == STORAGE_PERMISSION_CODE){
            if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                storageSwitch.setChecked(true);
            }else{
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == LOCATION_PERMISSION_CODE){
            if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                locationSwitch.setChecked(true);
            }else{
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == CAMERA_PERMISSION_CODE){
            if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                cameraSwitch.setChecked(true);
            }else{
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == PHONE_PERMISSION_CODE){
            if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                phoneSwitch.setChecked(true);
            }else{
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == CONTACT_PERMISSION_CODE){
            if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                contactSwitch.setChecked(true);
            }else{
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setOnClickListeners() {

        storageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storageSwitch.isChecked()){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                }else{
                    storageSwitch.setChecked(true);
                    Toast.makeText(MainActivity.this, "Permiso ya fue otorgado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        locationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(locationSwitch.isChecked()){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
                }else{
                    locationSwitch.setChecked(true);
                    Toast.makeText(MainActivity.this, "Permiso ya fue otorgado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cameraSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cameraSwitch.isChecked()){
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                }else{
                    cameraSwitch.setChecked(true);
                    Toast.makeText(MainActivity.this, "Permiso ya fue otorgado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        phoneSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneSwitch.isChecked()){
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_PERMISSION_CODE);
                }else{
                    phoneSwitch.setChecked(true);
                    Toast.makeText(MainActivity.this, "Permiso ya fue otorgado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        contactSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contactSwitch.isChecked()){
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, CONTACT_PERMISSION_CODE);
                }else{
                    contactSwitch.setChecked(true);
                    Toast.makeText(MainActivity.this, "Permiso ya fue otorgado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Called when the user taps the Send button
    public void clickNext(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //intent.setAction(Intent.ACTION_SEND);
        //intent.setType("text/plain");
        startActivity(intent);
    }

    private void  checkPermissions(){
        storageSwitch = findViewById(R.id.storageSwitch);
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            storageSwitch.setChecked(true);
        }else
            storageSwitch.setChecked(false);

        locationSwitch = findViewById(R.id.locationSwitch);
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationSwitch.setChecked(true);
        }else
            locationSwitch.setChecked(false);

        cameraSwitch = findViewById(R.id.cameraSwitch);
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            cameraSwitch.setChecked(true);
        }else
            cameraSwitch.setChecked(false);

        phoneSwitch = findViewById(R.id.phoneSwitch);
        if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            phoneSwitch.setChecked(true);
        }else
            phoneSwitch.setChecked(false);

        contactSwitch = findViewById(R.id.contactSwitch);
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            contactSwitch.setChecked(true);
        }else
            contactSwitch.setChecked(false);
    }

}