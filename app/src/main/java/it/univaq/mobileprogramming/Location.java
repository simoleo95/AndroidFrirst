package it.univaq.mobileprogramming;


public class Location
{
    private String indirizzo;
    private int cap;
    private int codComuneISTAT;
    private String comune;
    private String frazione;
    private int codProvinciaISTAT;
    private String siglaProvincia;
    private String descProvincia;
    private int codRegione;
    private String descRegione;
    
    private float latitudine;
    private float longitudine;
    private int localize;
    
    
    //Basic constructor
    public Location()
    {
    }
    
    
    /**
     *
     * @param indirizzo
     * @param cap
     * @param codComuneISTAT
     * @param comune
     * @param frazione
     * @param codProvinciaISTAT
     * @param siglaProvincia
     * @param descProvincia
     * @param codRegione
     * @param descRegione
     * @param latitudine
     * @param longitudine
     * @param localize
     */
    public Location(String indirizzo, int cap, int codComuneISTAT, String comune,
                    String frazione, int codProvinciaISTAT, String siglaProvincia,
                    String descProvincia, int codRegione, String descRegione, float latitudine,
                    float longitudine, int localize)
    {
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.codComuneISTAT = codComuneISTAT;
        this.comune = comune;
        this.frazione = frazione;
        this.codProvinciaISTAT = codProvinciaISTAT;
        this.siglaProvincia = siglaProvincia;
        this.descProvincia = descProvincia;
        this.codRegione = codRegione;
        this.descRegione = descRegione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.localize = localize;
    }
    
    
    public String getIndirizzo()
    {
        return indirizzo;
    }
    
    public void setIndirizzo(String indirizzo)
    {
        this.indirizzo = indirizzo;
    }
    
    public int getCap()
    {
        return cap;
    }
    
    public void setCap(int cap)
    {
        this.cap = cap;
    }
    
    public int getCodComuneISTAT()
    {
        return codComuneISTAT;
    }
    
    public void setCodComuneISTAT(int codComuneISTAT)
    {
        this.codComuneISTAT = codComuneISTAT;
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
    
    public int getCodProvinciaISTAT()
    {
        return codProvinciaISTAT;
    }
    
    public void setCodProvinciaISTAT(int codProvinciaISTAT)
    {
        this.codProvinciaISTAT = codProvinciaISTAT;
    }
    
    public String getSiglaProvincia()
    {
        return siglaProvincia;
    }
    
    public void setSiglaProvincia(String siglaProvincia)
    {
        this.siglaProvincia = siglaProvincia;
    }
    
    public String getDescProvincia()
    {
        return descProvincia;
    }
    
    public void setDescProvincia(String descProvincia)
    {
        this.descProvincia = descProvincia;
    }
    
    public int getCodRegione()
    {
        return codRegione;
    }
    
    public void setCodRegione(int codRegione)
    {
        this.codRegione = codRegione;
    }
    
    public String getDescRegione()
    {
        return descRegione;
    }
    
    public void setDescRegione(String descRegione)
    {
        this.descRegione = descRegione;
    }
    
    public float getLatitudine()
    {
        return latitudine;
    }
    
    public void setLatitudine(float latitudine)
    {
        this.latitudine = latitudine;
    }
    
    public float getLongitudine()
    {
        return longitudine;
    }
    
    public void setLongitudine(float longitudine)
    {
        this.longitudine = longitudine;
    }
    
    public int getLocalize()
    {
        return localize;
    }
    
    public void setLocalize(int localize)
    {
        this.localize = localize;
    }
}