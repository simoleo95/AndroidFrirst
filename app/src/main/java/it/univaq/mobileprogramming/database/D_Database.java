package it.univaq.mobileprogramming.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import it.univaq.mobileprogramming.entity.E_Farmacia;

@Database(entities = {E_Farmacia.class}, version = 1)
public abstract class D_Database extends RoomDatabase
{
    public abstract D_Farmacia D_Farmacia_Model();
    
    //Instantiate the database with a Singleton Pattern in order to keep only 1 instance active at time
    private static D_Database instance = null;
    
    private D_Database(){}
    
    public static D_Database getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(
                    context,
                    D_Database.class,
                    "RoomDB"
            ).build();
        }
        return instance;
    }
}
