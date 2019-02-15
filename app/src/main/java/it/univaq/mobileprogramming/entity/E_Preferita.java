package it.univaq.mobileprogramming.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


/**
 * Collection of all Pharmacies IDs that are user's favourites
 */
@Entity(tableName = "preferite")
public class E_Preferita
{
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;
    
    public E_Preferita(long id)
    {
        this.id = id;
    }
    
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
}
