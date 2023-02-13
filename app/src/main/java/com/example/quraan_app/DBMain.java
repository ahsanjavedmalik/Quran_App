package com.example.quraan_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBMain extends SQLiteOpenHelper {
    private static final String DB_PATH= "data/data/com.example.quraan_app/databases/";
    private static final String DB_NAME = "QuranDb.db";
    private SQLiteDatabase dbObj;
    private final Context context;
    public ArrayList<String> surahNameEng = new ArrayList<>();
    public ArrayList<String> surahNameUrdu = new ArrayList<>();
    public ArrayList<String> AyahsArabic = new ArrayList<>();
    public ArrayList<String> AyahsUrdu = new ArrayList<>();
    public ArrayList<String> FirstSearch = new ArrayList<>();
    public ArrayList<String> SecondSearch = new ArrayList<>();
    public Cursor a;

    public DBMain(Context context) {
        super(context,  DB_NAME , null, 3);
        this.context  = context;
    }


    public void createDB() throws IOException {

        this.getReadableDatabase();

        Log.i("Readable ends....","end");

        try {
            copyDB();
            Log.i("copy db ends....","end");

        } catch (IOException e) {

            throw new Error("Error copying database");
        }
    }

    public void DBSurahNames(){

        SQLiteDatabase checkDB = null;
        try{

            String path = DB_PATH + DB_NAME;
            Log.i("myPath ......",path);
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

            Log.i("myPath ......",path);
            if (checkDB!=null)
            {
               Cursor c = checkDB.rawQuery("SELECT * FROM tsurah", null);
                if (c.moveToFirst()) {
                   do {
                       if(c.getString(2)!=null)
                       {
                           Log.i("DB values.........", c.getString(2));
                           surahNameEng.add(c.getString(2));
                       }
                       if(c.getString(1)!=null)
                       {
                           Log.i("DB values.........", c.getString(4));
                           surahNameUrdu.add(c.getString(4));
                       }
                   } while (c.moveToNext());
               }
            }

        }catch(SQLiteException e){
            e.printStackTrace();
        }

        if(checkDB != null) {
            checkDB.close();
        }
    }

    public void DBAyahs(int index1){

        SQLiteDatabase checkDB = null;
        try{

            String path = DB_PATH + DB_NAME;
            Log.i("myPath ......",path);
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

            Log.i("myPath ......",path);
            if (checkDB!=null)
            {
                Cursor c = checkDB.rawQuery("SELECT * FROM tayah", null);
                if (c.moveToFirst()) {
                    do {
                        if(c.getString(0)!=null) {
                            if (Integer.parseInt(c.getString(0)) == 1) {
                                Log.i("DB values.........", c.getString(3));
                                AyahsArabic.add(c.getString(3));
                                AyahsUrdu.add(c.getString(4));
                                c.moveToNext();
                            }
                        }
                        if(c.getString(1)!=null) {
                            if (Integer.parseInt(c.getString(1)) == index1 + 1) {
                                Log.i("DB values.........", c.getString(3));
                                AyahsArabic.add(c.getString(3));
                                AyahsUrdu.add(c.getString(4));
                            }
                        }
                    } while (c.moveToNext());
                }
            }

        }catch(SQLiteException e){
            e.printStackTrace();
        }

        if(checkDB != null) {
            checkDB.close();
        }

    }

    public Cursor DBSearch(String searchTxt1, boolean Ayah)
    {
        SQLiteDatabase checkDB = null;
        try{

            String path = DB_PATH + DB_NAME;
            Log.i("myPath ......",path);
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            Log.i("myPath ......",path);
            if(Ayah==false) {
                if (checkDB != null) {
                    Cursor c = checkDB.rawQuery("select * from tsurah where SurahNameE LIKE '%" + searchTxt1 + "%'  OR SurahNameU LIKE '%" + searchTxt1 + "%' ", null);
                    a = c;
                    if (c.moveToFirst()) {
                        do {
                            if (c.getString(0) != null) {
                                FirstSearch.add(c.getString(2));
                                SecondSearch.add(c.getString(4));
                            }
                        } while (c.moveToNext());
                    }

                }
            }
            else if(Ayah==true)
            {
                if (checkDB != null) {
                    Cursor c = checkDB.rawQuery("select * from tayah where FatehMuhammadJalandhrield LIKE '%" + searchTxt1 + "%'", null);
                    a = c;
                    if (c.moveToFirst()) {
                        do {
                            if (c.getString(0) != null) {
                                FirstSearch.add(c.getString(3));
                                SecondSearch.add(c.getString(4));
                            }
                        } while (c.moveToNext());
                    }

                }
            }

        }catch(SQLiteException e){
            e.printStackTrace();
        }
        if(checkDB != null) {
            checkDB.close();
        }
        return a;
    }

    public ArrayList<String> getFirst()
    {
        return FirstSearch;
    }
    public ArrayList<String> getSecond()
    {
        return SecondSearch;
    }

    public ArrayList<String> getSurahNameEng()
    {
        return surahNameEng;
    }
    public ArrayList<String> getSurahNameUrdu()
    {
        return surahNameUrdu;
    }
    public ArrayList<String> getAyahsArabic()
    {
        return AyahsArabic;
    }
    public ArrayList<String> getAyahsUrdu()
    {
        return AyahsUrdu;
    }

    String getItemNameEng(int i)
    {
        return FirstSearch.get(i);
    }

    public void copyDB() throws IOException{
        try {
            Log.i("inside copyDB....","start");

            InputStream ip =  context.getAssets().open(DB_NAME);
            Log.i("Input Stream....",ip+"");
            String op=  DB_PATH  +  DB_NAME ;
            OutputStream output = new FileOutputStream( op);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = ip.read(buffer))>0){
                output.write(buffer, 0, length);
//                Log.i("Content.... ",length+"");
            }
            output.flush();
            output.close();
            ip.close();
        }
        catch (IOException e) {
            Log.v("error", e.toString());
        }
    }

    public void openDB() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        dbObj = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        Log.i("open DB......",dbObj.toString());
    }

    @Override
    public synchronized void close() {

        if(dbObj != null)
            dbObj.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
