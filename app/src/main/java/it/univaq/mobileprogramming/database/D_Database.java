package it.univaq.mobileprogramming.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import it.univaq.mobileprogramming.entity.E_Farmacia;

@Database(entities = {E_Farmacia.class}, version = 2)
public abstract class D_Database extends RoomDatabase
{
    //This object is meant to be used via D_Database object.
    //Acting in this way we'll create a DB instance and access all his tables + methods
    public abstract D_Farmacia D_Farmacia_Access();
    
    //Instantiate the database with a Singleton Pattern in order to keep only 1 instance active at time
    private static D_Database instance = null;
    
    public D_Database(){}
    
    /**
     * Singleton instantiation of RoomDB persistent connection
     *
     * @param context Application context
     * @return DB connection instance
     */
    public static D_Database getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder( //Open a persistent connection to the DB
                    context,
                    D_Database.class,
                    "RoomDB"
            )
            // Drops and Alter the DB to apply new changes
            // Note: ID column had to be changed from the original
            // Source: https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
            .fallbackToDestructiveMigration()
            .build();
        }
        return instance;
    }
    
    
    /**
     * Close the persistent connection to DB.
     * As many users and Google Developers suggest, this should be used on application onDestroy()
     * in order to keep alive the DB throughout the whole working session
     */
    public static void closeConnection()
    {
        instance = null;
    }
}