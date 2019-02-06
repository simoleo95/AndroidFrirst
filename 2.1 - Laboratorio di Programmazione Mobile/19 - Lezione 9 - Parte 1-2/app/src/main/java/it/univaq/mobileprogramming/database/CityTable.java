package it.univaq.mobileprogramming.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import it.univaq.mobileprogramming.model.City;

/**
 * It handles the table of the cities.
 */
class CityTable {

    // Name of the table
    static final String TABLE_NAME = "cities";

    // Columns name
    static final String ID = "id";
    static final String NAME = "name";
    static final String REGION = "region";
    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";


    /**
     * The method handles the creation of the table
     * @param db the database instance
     */
    static void create(SQLiteDatabase db){
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                REGION + " TEXT, " +
                LATITUDE + " REAL, " +
                LONGITUDE + " REAL " +
                ");";
        db.execSQL(sql);
    }

    /**
     * The method drops the table and loses all data
     * @param db the database instance
     */
    static void drop(SQLiteDatabase db){
        String sql = "DROP TABLE " + TABLE_NAME;
        db.execSQL(sql);
    }

    /**
     * The method do the upgrade of the table.
     * This implementation is a wrong way to do an upgrade because it loses all data
     */
    static void upgrade(SQLiteDatabase db){
        drop(db);
        create(db);
    }

    /**
     * The method save the City into the right table.
     *
     * @param db the database instance in writable mode
     * @param city the saved object containing the right id
     */
    static void insert(SQLiteDatabase db, City city){

        ContentValues values = new ContentValues();
        values.put(NAME, city.getName());
        values.put(REGION, city.getRegion());
        values.put(LATITUDE, city.getLatitude());
        values.put(LONGITUDE, city.getLongitude());
        long id = db.insert(TABLE_NAME, null, values);
        city.setId(id);
    }

    /**
     * The method update all values of the City.
     *
     * @param db the database instance in writable mode
     * @param city the city to update with the new data
     * @return true if the update is successful, false otherwise
     */
    static boolean update(SQLiteDatabase db, City city){

        ContentValues values = new ContentValues();
        values.put(NAME, city.getName());
        values.put(REGION, city.getRegion());
        values.put(LATITUDE, city.getLatitude());
        values.put(LONGITUDE, city.getLongitude());
        return db.update(TABLE_NAME, values, ID + " = ?",
                new String[]{ String.valueOf(city.getId()) }) == 1;
    }

    /**
     * The method delete the City from the database
     *
     * @param db the database instance in writable mode
     * @param city the city to delete
     * @return true if the delete is successful, false otherwise
     */
    static boolean delete(SQLiteDatabase db, City city){
        return db.delete(TABLE_NAME, ID + " = " + city.getId(), null) == 1;

    }

    /**
     * The method delete all data inside the table of the cities.
     * Warning: the primary key is not reset.
     *
     * @param db the database instance in writable mode
     * @return true if the data are deleted, false otherwise
     */
    static boolean delete(SQLiteDatabase db){
        return db.delete(TABLE_NAME, null, null) > 0;

    }

    /**
     * The method get all cities from the database.
     *
     * @param db the database instance in readable mode
     * @return a list containing all cities, or an empty list
     */
    static List<City> select(SQLiteDatabase db){

        List<City> cities = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + REGION + " ASC;";

        Cursor cursor = null;
        try {
            // Perform the query
            cursor = db.rawQuery(sql, null);

            // Iterate on cursor to read all tuples
            while(cursor.moveToNext()){
                City city = new City();

                // Warning: get dynamically the column index
                city.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                city.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                city.setRegion(cursor.getString(cursor.getColumnIndex(REGION)));
                city.setLatitude(cursor.getDouble(cursor.getColumnIndex(LATITUDE)));
                city.setLongitude(cursor.getDouble(cursor.getColumnIndex(LONGITUDE)));

                cities.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Warning: always close the cursor
            if(cursor != null) cursor.close();
        }

        return cities;
    }
}
