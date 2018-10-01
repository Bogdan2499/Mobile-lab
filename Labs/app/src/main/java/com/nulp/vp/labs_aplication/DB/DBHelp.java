package com.nulp.vp.labs_aplication.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vova0199 on 30.09.2018.
 */
public class DBHelp extends SQLiteOpenHelper {

    public  DBHelp(Context c){
        super(c, "Favourites", null, 1);
    }
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Favourites";

    private static final String GROUPS_TABLE_NAME = "films";


    public static final String FILM_ID = "id";
    public static final String FILM_TITLE = "title";
    public static final String FILM_DESCRIPTION = "description";
    public static final String FILM_POSTER = "image_path";
    public static final String FILM_RATE = "rate_average";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table films( id INTEGER PRIMARY KEY AUTOINCREMENT, title text, " +
                "description text, image_path text, rate_average text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists about");
        db.execSQL("drop table if exists markers");
    }


// ---------------------ABOUT-------------------------------------------

    public boolean insert(String title,  String description, String rate_average, String image_path) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("rate_average", rate_average);
        contentValues.put("image_path", image_path);
        long res = db.insert("films", null, contentValues);
        System.out.print(res);
        return true;
    }

    public boolean delete(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("delete from films where title = '%s'", title);
        db.execSQL(sql);
        return true;
    }

    public Cursor queueAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select  title,  description,rate_average ,\n" +
                "                         image_path from films", null);
        return cursor;
    }


}
