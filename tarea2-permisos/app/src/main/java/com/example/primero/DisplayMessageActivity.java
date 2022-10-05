package com.example.primero;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class DisplayMessageActivity extends AppCompatActivity {
    private TextView storageText, locationText, cameraText, phoneText, contactText;
    private String cameraPackageName = "com.android.camera";
    private String contactsPackageName = "com.android.contacts";
    private String filesPackageName = "com.android.files";
    private String TELEFONO = "809-581-1111";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        storageText = findViewById(R.id.storageText);
        storageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Context ctx = DisplayMessageActivity.this;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    Intent intent=new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                    try {
                        startActivity(intent);
                    }catch (ActivityNotFoundException e){
                        Toast.makeText(ctx,"No hay aplicacion disponible para esta tarea",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast t = Toast.makeText(DisplayMessageActivity.this, "Please request permission", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.FILL_HORIZONTAL,0,0);
                    t.show();
                }
            }
        });
        locationText = findViewById(R.id.locationText);
        locationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    Context ctx = DisplayMessageActivity.this;
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("geo:0,0?q=santiago+republica+dominicana"));
                    try {
                        ctx.startActivity(intent);
                    }catch (ActivityNotFoundException e){
                        Toast.makeText(ctx,"No hay aplicacion disponible para esta tarea",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast t = Toast.makeText(DisplayMessageActivity.this, "Please request permission", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.FILL_HORIZONTAL,0,0);
                    t.show();
                }
            }
        });

        cameraText = findViewById(R.id.cameraText);
        cameraText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Context ctx = DisplayMessageActivity.this;
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        ctx.startActivity(takePictureIntent);
                    }catch (ActivityNotFoundException e){
                        Toast.makeText(ctx,"No hay aplicacion disponible para esta tarea",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast t = Toast.makeText(DisplayMessageActivity.this, "Please request permission", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.FILL_HORIZONTAL,0,0);
                    t.show();
                }
            }
        });

        phoneText = findViewById(R.id.phoneText);
        phoneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    Context ctx = DisplayMessageActivity.this;
                    String uri = "tel:" + TELEFONO.trim();
                    Intent i = new Intent(Intent.ACTION_CALL);
                    //Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse(uri));
                    try {
                        startActivity(i);
                    }catch (ActivityNotFoundException e){
                        Toast.makeText(ctx,"No hay aplicacion disponible para esta tarea",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast t = Toast.makeText(DisplayMessageActivity.this, "Please request permission", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.FILL_HORIZONTAL,0,0);
                    t.show();
                }
            }
        });

        contactText = findViewById(R.id.contactText);
        contactText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                    Context ctx = DisplayMessageActivity.this;
                    Intent i = new Intent();
                    i.setComponent(new ComponentName(contactsPackageName, "com.android.contacts.DialtactsContactsEntryActivity"));
                    i.setAction("android.intent.action.MAIN");
                    i.addCategory("android.intent.category.LAUNCHER");
                    i.addCategory("android.intent.category.DEFAULT");
                    try {
                        startActivity(i);
                    }catch (ActivityNotFoundException e){
                        Toast.makeText(ctx,"No hay aplicacion disponible para esta tarea",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast t = Toast.makeText(DisplayMessageActivity.this, "Please request permission", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.FILL_HORIZONTAL,0,0);
                    t.show();
                }
            }
        });
    }


}