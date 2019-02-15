package it.univaq.mobileprogramming.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import it.univaq.mobileprogramming.entity.E_Preferita;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE; //https://developer.android.com/reference/android/arch/persistence/room/OnConflictStrategy

/**
 * Enables to set and remove favourite pharmacies
 * Fetching operations (getAllFavourites and others) belong to D_Farmacia interface
 */
@Dao //Data Access Object
public interface D_Preferita
{
    @Insert(onConflict = IGNORE)
    public void setAsFavourite(E_Preferita e);
 
    @Delete
    public void removeFromFavourite(E_Preferita e);
}
