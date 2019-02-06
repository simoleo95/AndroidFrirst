package it.univaq.mobileprogramming.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import it.univaq.mobileprogramming.model.City;

/**
 * This class create the database and manage the access to it.
 *
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int VERSION = 2;

    //... Singleton Pattern
    private static Database instance = null;

    public static Database getInstance(Context context){
        return instance == null ? instance = new Database(context) : instance;
    }

    private Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    //... End Singleton Pattern


    @Override
    public void onCreate(SQLiteDatabase db) {
        CityTable.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        CityTable.upgrade(db);
    }


    public void save(City city){
        CityTable.insert(getWritableDatabase(), city);
    }

    public List<City> getAllCities(){
        return CityTable.select(getReadableDatabase());
    }

    public void delete(){
        CityTable.delete(getWritableDatabase());
    }

}
