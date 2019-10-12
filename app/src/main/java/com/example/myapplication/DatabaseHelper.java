package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Fiche.db";
    private static final String TABLE_NAME="users";
    public DatabaseHelper(Context context ) {

        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_NAME+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, namE TEXT,descriptioN TEXT, lng TEXT, lat TEXT, filePath TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    public boolean insertDataToDatabase(String name,String description, String lat, String lng, String filePath)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("name",name);
        values.put("description",description);
        values.put("lat", lat);
        values.put("lng", lng);
        values.put("filePath", filePath);

        long result=db.insert(TABLE_NAME,null,values);
        if (result==-1)
        {
            return false;
        }
        else {
            return true;
        }

    }
    public List<ListItem> getAllUsers() {
        List<ListItem> users = new ArrayList<ListItem>();


        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_NAME);


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ListItem newUser = new ListItem();
                    newUser.setName(cursor.getString(cursor.getColumnIndex("namE")));
                    newUser.setDescription(cursor.getString(cursor.getColumnIndex("descriptioN")));
                    newUser.setLat(cursor.getString(cursor.getColumnIndex("lat")));
                    newUser.setLng(cursor.getString(cursor.getColumnIndex("lng")));
                    newUser.setFilePath(cursor.getString(cursor.getColumnIndex("filePath")));
                    if(newUser != null)
                        users.add(newUser);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get users from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return users;
    }


}









