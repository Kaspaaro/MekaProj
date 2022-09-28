package com.example.mekaproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/* @Kaspar Tullus*/
public class PaivaKirjaData_Displayer extends AppCompatActivity {

    ///Refrenssit
    private ListView lv_Paivakirjadata;
    ArrayAdapter PaivakirjaArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiva_kirja_data_displayer);

        //Tämä Näyttää Tallennetut rivit databasesta view listalle
        lv_Paivakirjadata = findViewById(R.id.pkDATADISPLAY);

        MekaDataBase mekaDataBase = new MekaDataBase(PaivaKirjaData_Displayer.this);
        //palauttaa kaiken datan SQLitesta
        List<PaivaKirjaData> everything = mekaDataBase.getEverything();

        // Kirjoitaa arvot viewlistalle databasesta.
        PaivakirjaArrayAdapter = new ArrayAdapter<>(PaivaKirjaData_Displayer.this, android.R.layout.simple_list_item_1, everything);

        lv_Paivakirjadata.setAdapter(PaivakirjaArrayAdapter);

        lv_Paivakirjadata.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            //Lähettää tiedot luettavaksi "Paivakirja_view_delete"
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                /*Object obj = PaivakirjaArrayAdapter.getItem(position);
                String value= obj.toString();*/

                Intent intent = new Intent(PaivaKirjaData_Displayer.this, Paivakirja_view_delete.class);
                intent.putExtra("POSITION",String.valueOf(position));

                startActivity(intent);

            }
        });
    }



}