package com.example.mekaproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * @author Kaspar Tullus
 * @author Semen Morozov
 */
public class MainActivity extends AppCompatActivity {

    private int STORAGE_PREMISSION = 245436;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
     //PREMISSION WINDOW BUILDER.
    public void requestNotificationPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.ACCESS_NOTIFICATION_POLICY)){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Permission needed")
                .setMessage("This permission is needed to send notifications from the reminder inside the application.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

        }else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY},STORAGE_PREMISSION);
        }

    }

    //PREMISSION WINDOW RESULTS //CHECKS IF PREMISSION GRANTED OR NOT!
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PREMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this, "Permission Not Granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }



    public void paivakirjabtn(View view) {
        Intent intent = new Intent(this, paivaKirja_kirjaaminen.class);
        startActivity(intent);
    }
    public void btn_paivakirjadata(View view) {
        Intent intent = new Intent(this, PaivaKirjaData_Displayer.class);
        startActivity(intent);
    }

    public void muistutus_btn (View view){
        //CHECK IF PREMISSION IS GRANTED, IF NOT THEN ASK FOR PREMISSION!
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(MainActivity.this,"You Have Already Granted Premissions!"+"\uD83D\uDC8E",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, MekaMuistutus.class);
            startActivity(intent);
        }else{
            requestNotificationPermission();
        }


    }
    public void btn_poistaMuistutus (View view){
        Intent intent = new Intent(this, Calendar_memory_list.class);
        startActivity(intent);
    }


}