package fr.projet.pt.sport_activity.localisation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    SQLiteDatabase db;


    public DB(Context context) {
        super(context, "localDB", null, 17);
    }

    // called when getWritableDatabase is called and the database file doesn't exist
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE location (" +
                " locationID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " journeyID INTEGER NOT NULL," +
                " altitude REAL NOT NULL," +
                " longitude REAL NOT NULL," +
                " latitude REAL NOT NULL," +
                " CONSTRAINT fk1 FOREIGN KEY (journeyID) REFERENCES journey (journeyID) ON DELETE CASCADE);");
    }

    // called when the database file exists but the version number stored in the db
    // is lower than that passed in the constructor
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




}