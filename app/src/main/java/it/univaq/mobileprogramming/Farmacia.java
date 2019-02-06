package it.univaq.mobileprogramming;


public class Farmacia
{
    private int codiceIdentificativo;
    private int codiceASL;
    private String Descrizione;
    private int partitaIVA;
    private int dataInizioValidita;
    private int dataFineValidita;
    private String descrizioneTipologia;
    private int codiceTipologia;
    private Location location;
    
    //Basic constructor
    public Farmacia()
    {
    }
    
    /**
     *
     * @param id
     * @param asl
     * @param desc
     * @param iva
     * @param dataInizio
     * @param dataFine
     * @param descTipo
     * @param codTipo
     * @param l
     */
    public Farmacia(int id, int asl, String desc, int iva, int dataInizio, int dataFine,
                    String descTipo, int codTipo, Location l)
    {
        this.codiceIdentificativo = id;
        this.codiceASL = asl;
        this.Descrizione = desc;
        this.partitaIVA = iva;
        this.dataInizioValidita = dataInizio;
        this.dataFineValidita = dataFine;
        this.descrizioneTipologia = descTipo;
        this.codiceTipologia = codTipo;
        this.location = l;
    }
    
    public int getCodiceIdentificativo()
    {
        return codiceIdentificativo;
    }
    
    public void setCodiceIdentificativo(int id)
    {
        this.codiceIdentificativo = id;
    }
    
    public int getCodiceASL()
    {
        return codiceASL;
    }
    
    public void setCodiceASL(int codiceASL)
    {
        this.codiceASL = codiceASL;
    }
    
    public String getDescrizione()
    {
        return Descrizione;
    }
    
    public void setDescrizione(String descrizione)
    {
        Descrizione = descrizione;
    }
    
    public int getPartitaIVA()
    {
        return partitaIVA;
    }
    
    public void setPartitaIVA(int partitaIVA)
    {
        this.partitaIVA = partitaIVA;
    }
    
    public int getDataInizioValidita()
    {
        return dataInizioValidita;
    }
    
    public void setDataInizioValidita(int dataInizioValidita)
    {
        this.dataInizioValidita = dataInizioValidita;
    }
    
    public int getDataFineValidita()
    {
        return dataFineValidita;
    }
    
    public void setDataFineValidita(int dataFineValidita)
    {
        this.dataFineValidita = dataFineValidita;
    }
    
    public String getDescrizioneTipologia()
    {
        return descrizioneTipologia;
    }
    
    public void setDescrizioneTipologia(String descrizioneTipologia)
    {
        this.descrizioneTipologia = descrizioneTipologia;
    }
    
    public int getCodiceTipologia()
    {
        return codiceTipologia;
    }
    
    public void setCodiceTipologia(int codiceTipologia)
    {
        this.codiceTipologia = codiceTipologia;
    }
    
    public Location getLocation()
    {
        return location;
    }
    
    public void setLocation(Location location)
    {
        this.location = location;
    }
    
    
}

