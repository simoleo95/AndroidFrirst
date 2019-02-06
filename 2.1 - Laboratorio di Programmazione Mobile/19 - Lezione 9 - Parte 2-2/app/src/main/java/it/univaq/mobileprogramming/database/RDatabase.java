package it.univaq.mobileprogramming.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import it.univaq.mobileprogramming.model.City;

/**
 * MobileProgramming2018
 * Created by leonardo on 30/11/2018.
 */

@Database(entities ={ City.class }, version = 1, exportSchema = false)
public abstract class RDatabase extends RoomDatabase {

    public abstract CityDao getCityDao();

    private static RDatabase instance = null;

    public RDatabase(){}

    public static RDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context,
                    RDatabase.class,
                    "myRoomDatabase").build();
        }
        return instance;
    }
}
