package it.univaq.mobileprogramming.entity;


public class Location //TODO: QUESTA CLASSE DEVE ESSER RIMOSSA PERCHè SOSTITUITA DA E_Farmacia
{
    private String indirizzo;
    private String cap;
    private String codComuneISTAT;
    private String comune;
    private String frazione;
    private String codProvinciaISTAT;
    private String siglaProvincia;
    private String descProvincia;
    private String codRegione;
    private String descRegione;
    
    private String latitudine;
    private String longitudine;
    private String localize;
    
    
    //Basic constructor
    public Location()
    {
    }
    
    
    /**
     * Short constructor enabling to set fundamental details only
     *
     * @param indirizzo
     * @param comune
     * @param frazione
     * @param descProvincia
     * @param descRegione
     * @param latitudine
     * @param longitudine
     */
    public Location(String indirizzo, String comune, String frazione, String descProvincia,
                    String descRegione, String latitudine, String longitudine)
    {
        this.indirizzo = indirizzo;
        this.comune = comune;
        this.frazione = frazione;
        this.descProvincia = descProvincia;
        this.descRegione = descRegione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }
    
    
    public String getIndirizzo()
    {
        return indirizzo;
    }
    
    public void setIndirizzo(String indirizzo)
    {
        this.indirizzo = indirizzo;
    }
    
    public String getCap()
    {
        return cap;
    }
    
    public void setCap(String cap)
    {
        this.cap = cap;
    }
    
    public String getCodComuneISTAT()
    {
        return codComuneISTAT;
    }
    
    public void setCodComuneISTAT(String codComuneISTAT)
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
    
    public String getCodProvinciaISTAT()
    {
        return codProvinciaISTAT;
    }
    
    public void setCodProvinciaISTAT(String codProvinciaISTAT)
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
    
    public String getCodRegione()
    {
        return codRegione;
    }
    
    public void setCodRegione(String codRegione)
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
    
    public String getLatitudine()
    {
        return latitudine;
    }
    
    public void setLatitudine(String latitudine)
    {
        this.latitudine = latitudine;
    }
    
    public String getLongitudine()
    {
        return longitudine;
    }
    
    public void setLongitudine(String longitudine)
    {
        this.longitudine = longitudine;
    }
    
    public String getLocalize()
    {
        return localize;
    }
    
    public void setLocalize(String localize)
    {
        this.localize = localize;
    }
}