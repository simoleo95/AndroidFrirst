package it.univaq.mobileprogramming.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import it.univaq.mobileprogramming.database.D_Database;


/**
 * Sets all the necessary parameters for a new Pharmacy
 */
@Entity(tableName = "farmacie") //Represents a table within the database.
public class E_Farmacia
{
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;
    
    //POJO - Plain Old Java Object
    @ColumnInfo(name = "indirizzo")
    private String indirizzo;
    
    @ColumnInfo(name = "farmacia")
    private String farmacia;
    
    @ColumnInfo(name = "iva")
    private String iva;
    
    @ColumnInfo(name = "comune")
    private String comune;
    
    @ColumnInfo(name = "frazione")
    private String frazione;
    
    @ColumnInfo(name = "provincia")
    private String provincia;
    
    @ColumnInfo(name = "regione")
    private String regione;
    
    @ColumnInfo(name = "data_inizio")
    private String data_inizio;
    
    @ColumnInfo(name = "latitudine")
    private String latitudine;
    
    @ColumnInfo(name = "longitudine")
    private String longitudine;
    
    @Ignore
    public E_Farmacia()
    {
    }
    
    
    /**
     * Create a new E_Farmacia without specifying whether it's Favourite or not
     *
     * @param id          Excel's row
     * @param indirizzo   Farmacia's address
     * @param farmacia    Farmacia's description
     * @param iva         Partita IVA
     * @param comune      Farmacia's userCurrentCity location
     * @param frazione    City's fraction
     * @param provincia   City's provence
     * @param regione     City's region
     * @param data_inizio Farmacia's activity starting date
     * @param latitudine  Farmacia's geographical lat
     * @param longitudine Farmacia's geographical lon
     */
    public E_Farmacia(long id, String indirizzo, String farmacia, String iva, String comune,
                      String frazione, String provincia, String regione, String data_inizio,
                      String latitudine, String longitudine)
    {
        this.id = id;
        this.indirizzo = indirizzo;
        this.farmacia = farmacia;
        this.iva = iva;
        this.comune = comune;
        this.frazione = frazione;
        this.provincia = provincia;
        this.regione = regione;
        this.data_inizio = data_inizio;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }
    
    public void setAsFavourite(D_Database room)
    {
        E_Preferita pref = new E_Preferita(this.id);
        room.D_Preferita_Access().setAsFavourite(pref);
    }
    
    public void removeFromFavourites(D_Database room)
    {
        E_Preferita pref = new E_Preferita(this.id);
        room.D_Preferita_Access().removeFromFavourite(pref);
    }
    
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public String getIndirizzo()
    {
        return indirizzo;
    }
    
    public void setIndirizzo(String indirizzo)
    {
        this.indirizzo = indirizzo;
    }
    
    public String getFarmacia()
    {
        return farmacia;
    }
    
    public void setFarmacia(String farmacia)
    {
        this.farmacia = farmacia;
    }
    
    public String getIva()
    {
        return iva;
    }
    
    public void setIva(String iva)
    {
        this.iva = iva;
    }
    
    public String getComune()
    {
        return comune;
    }
    
    public void setComune(String comune)
    {
        this.comune = comune;
    }
    
    public String getFrazione()
    {
        return frazione;
    }
    
    public void setFrazione(String frazione)
    {
        this.frazione = frazione;
    }
    
    public String getProvincia()
    {
        return provincia;
    }
    
    public void setProvincia(String provincia)
    {
        this.provincia = provincia;
    }
    
    public String getRegione()
    {
        return regione;
    }
    
    public void setRegione(String regione)
    {
        this.regione = regione;
    }
    
    public String getData_inizio()
    {
        return data_inizio;
    }
    
    public void setData_inizio(String data_inizio)
    {
        this.data_inizio = data_inizio;
    }
    
    public String getLatitudine()
    {
        return latitudine;
    }
    
    public double getLat_Double()
    {
        return Double.valueOf(latitudine.replace(',', '.'));
    }
    
    public void setLatitudine(String latitudine)
    {
        this.latitudine = latitudine;
    }
    
    public String getLongitudine()
    {
        return longitudine;
    }
    
    
    public double getLon_Double()
    {
        return Double.valueOf(longitudine.replace(",", "."));
    }
    
    public void setLongitudine(String longitudine)
    {
        this.longitudine = longitudine;
    }
}