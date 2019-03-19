package it.univaq.mobileprogramming.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import it.univaq.mobileprogramming.entity.E_Farmacia;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE; //https://developer.android.com/reference/android/arch/persistence/room/OnConflictStrategy

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
    
    
    /**
     * Retrieve all the pharmacies in the same userCurrentCity as the user's
     *
     * @param comune user's userCurrentCity
     * @return a List of all the open pharmacies in the selected userCurrentCity
     */
    @Query("SELECT * " +
           "FROM farmacie " +
           "WHERE comune = :comune")
    //public E_Farmacia getAllPharmaciesIn(String comune);
    public List<E_Farmacia> getAllPharmaciesIn(String comune);
    
    
    /**
     * Get a specific pharmacy by its ID
     *
     * @return E_Farmacia object
     */
    @Query("SELECT * " +
           "FROM farmacie " +
           "WHERE id = :id")
    public E_Farmacia getPharmacyWith_ID(long id);
    
    
    /**
     * Retrieve all the pharmacies
     *
     * @return a List of all the pharmacies saved in the DB
     */
    @Query("SELECT * " +
           "FROM farmacie")
    public List<E_Farmacia> getAll();
    
    
    /**
     * Selects all the user favourite pharmacies
     *
     * @return a List of all the user favourite pharmacies
     */
    @Query("SELECT * " +
           "FROM farmacie " +
           "INNER JOIN preferite " +
           "ON farmacie.id = preferite.id")
    public List<E_Farmacia> getAllFavourites();
}