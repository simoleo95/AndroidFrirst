package it.univaq.mobileprogramming.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import it.univaq.mobileprogramming.entity.E_Farmacia;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao //Data Access Object - Contains the methods used for accessing the database.
public interface D_Farmacia
{
    //Why Room Database?
    //"[...] we highly recommend using Room instead of SQLite." - https://developer.android.com/training/data-storage/room/
    
    //https://developer.android.com/training/data-storage/room/accessing-data
    //Reactive Queries
    
    @Insert(onConflict = REPLACE)
    public void insertThis(E_Farmacia farmacia);
    
    
    @Delete
    public void deleteThis(E_Farmacia farmacia);
    
    
    @Update
    public void updateThis(E_Farmacia farmacia);
    
    
    //Retrieve all the pharmacies in the same Comune as the user
    @Query("SELECT * FROM farmacie WHERE comune = :comune")
    //public E_Farmacia getAllPharmaciesIn(String comune);
    public List<E_Farmacia> getAllPharmaciesIn(String comune);
    
    
    //Get all the user favourite pharmacies
    @Query("SELECT * FROM farmacie WHERE preferito = :preferito")
    //public E_Farmacia getPreferiti(byte preferito);
    public List<E_Farmacia> getUserFavourites(byte preferito);
    
    
    
    @Query("SELECT * FROM farmacie WHERE id = :id")
    public E_Farmacia getPharmacyWith_ID(long id);
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}