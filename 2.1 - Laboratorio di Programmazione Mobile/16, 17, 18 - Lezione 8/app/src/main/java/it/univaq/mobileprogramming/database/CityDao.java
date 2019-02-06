package it.univaq.mobileprogramming.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import it.univaq.mobileprogramming.model.City;

/**
 * MobileProgramming2018
 * Created by leonardo on 30/11/2018.
 */

@Dao
public interface CityDao {

    @Insert
    public void save(City city);

    @Delete
    public void delete(City city);

    @Update
    public void update(City city);

    @Query("SELECT * FROM cities ORDER BY region ASC")
    public List<City> getAllCities();

    @Query("SELECT * FROM cities WHERE id=:id")
    public City getById(long id);
}
