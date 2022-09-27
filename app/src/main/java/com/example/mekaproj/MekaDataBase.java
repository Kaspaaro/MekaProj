package com.example.mekaproj;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MekaDataBase extends SQLiteOpenHelper{
    static final String DB_NAME = "Paivakirja.db";
    public static final String PAIVAKIRJA_TABLE = "PAIVAKIRJA_TABLE";
    public static final String COLUMN_PAIVAKIRJA_OTSIKKO = "PAIVAKIRJA_OTSIKKO";
    public static final String COLUMN_PAIVAKIRJA_KIRJE = "PAIVAKIRJA_KIRJE";
    public static final String COLUMN_ID = "ID";

    public MekaDataBase(@Nullable Context context) {

        super(context, DB_NAME, null, 1);
    }

    //Eka kerta databasea käytetään. Täälä pitäisi olla koodia joka luo uuden databasen.
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Luo databaselle arvot SQL koodia käyttäen.
        String createTableStatement = "CREATE TABLE " + PAIVAKIRJA_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PAIVAKIRJA_OTSIKKO + " TEXT," + COLUMN_PAIVAKIRJA_KIRJE + " TEXT)";
        db.execSQL(createTableStatement);
    }

    //tämä kutsutaan jos databasen versio numero muutuu. se estää vanhojen käyttäjien sovelluksen crashaamisen jos databasea muutetaan/päivitetään!
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public boolean addOne (PaivaKirjaData paivaKirjaData){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PAIVAKIRJA_OTSIKKO,paivaKirjaData.getOtsikko()); // Tässä kirjoitetaan column_paivakirjaan otsikko joka saadaan getOtsikko methodilla paivakirjadatasta
        cv.put(COLUMN_PAIVAKIRJA_KIRJE,paivaKirjaData.getKirje()); // Tässä kirjoitetaan column_paivakirjaan kirje joka saadaan getKirje methodilla paivakirjadatasta

        long insert = db.insert(PAIVAKIRJA_TABLE, null, cv);

        // Jos inserti on -1 palauttaa false jos se on jotain muuta se palautta true
        if (insert == -1){

            return false;

        }else{

            return true;

        }

    }

    public boolean deleteOne(PaivaKirjaData paivaKirjaData){
        SQLiteDatabase db = getWritableDatabase();
        String queryString = "DELETE FROM " + PAIVAKIRJA_TABLE + " WHERE " + COLUMN_ID + " = " + paivaKirjaData.getID();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    //Geteverything methodi,palauttaa Kaiken databasesta.
    public List<PaivaKirjaData> getEverything() {

        List<PaivaKirjaData> returnList = new ArrayList<>();
        // Get data from database (ottaa dataa databasesta)

        String queryString = "SELECT * FROM " + PAIVAKIRJA_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {
                int RiviID = cursor.getInt(0);
                Integer paivakirjaid = cursor.getInt(0);
                String paivakirjaOtsikko = cursor.getString(1);

                String paivakirjaKirje = cursor.getString(2);

                PaivaKirjaData paivaKirjaData = new PaivaKirjaData(paivakirjaid,paivakirjaOtsikko,paivakirjaKirje);

                returnList.add(paivaKirjaData);

            }while (cursor.moveToNext());


        }else {
            //fail älä lisää mitään
        }

        // sulje db ja cursor kun on valmista
        cursor.close();

        db.close();

        return returnList;
    }
}
