package it.univaq.mobileprogramming.entity;

import java.util.ArrayList;

public class Farmacia //TODO: QUESTA CLASSE DEVE ESSER RIMOSSA PERCHè SOSTITUITA DA E_Farmacia
{
    private String codiceIdentificativo;
    private String codiceASL;
    private String indirizzo;
    private String descrizione;
    private String partitaIVA;
    private String dataInizioValidita;
    private String dataFineValidita;
    private String descrizioneTipologia;
    private String codiceTipologia;
    private Location location;
    
    
    //Basic constructor
    public Farmacia()
    {
    }
    
    
    /**
     * Short constructor enabling to set fundamental details only
     *
     * @param indirizzo
     * @param descrizione
     * @param partitaIVA
     * @param dataInizioValidita
     * @param location
     */
    public Farmacia(String indirizzo, String descrizione, String partitaIVA,
                    String dataInizioValidita,
                    Location location)
    {
        this.indirizzo = indirizzo;
        this.descrizione = descrizione;
        this.partitaIVA = partitaIVA;
        this.dataInizioValidita = dataInizioValidita;
        this.location = location;
    }
    
    public ArrayList<String[]> toArrayList()
    {
        String[] support = new String[10];
        support[0] = this.indirizzo;
        support[1] = this.descrizione;
        support[2] = this.partitaIVA;
        support[3] = this.location.getComune();
        support[4] = this.location.getFrazione();
        support[5] = this.location.getDescProvincia();
        support[6] = this.location.getDescRegione();
        support[7] = this.dataInizioValidita;
        support[8] = this.location.getLatitudine();
        support[9] = this.location.getLongitudine();
        
        ArrayList<String[]> ret = new ArrayList<String[]>();
        ret.add(support);
        return ret;
    }
    
    
    public String getCodiceIdentificativo()
    {
        return codiceIdentificativo;
    }
    
    public void setCodiceIdentificativo(String codiceIdentificativo)
    {
        this.codiceIdentificativo = codiceIdentificativo;
    }
    
    public String getCodiceASL()
    {
        return codiceASL;
    }
    
    public void setCodiceASL(String codiceASL)
    {
        this.codiceASL = codiceASL;
    }
    
    public String getIndirizzo()
    {
        return indirizzo;
    }
    
    public void setIndirizzo(String indirizzo)
    {
        this.indirizzo = indirizzo;
    }
    
    public String getDescrizione()
    {
        return descrizione;
    }
    
    public void setDescrizione(String descrizione)
    {
        this.descrizione = descrizione;
    }
    
    public String getPartitaIVA()
    {
        return partitaIVA;
    }
    
    public void setPartitaIVA(String partitaIVA)
    {
        this.partitaIVA = partitaIVA;
    }
    
    public String getDataInizioValidita()
    {
        return dataInizioValidita;
    }
    
    public void setDataInizioValidita(String dataInizioValidita)
    {
        this.dataInizioValidita = dataInizioValidita;
    }
    
    public String getDataFineValidita()
    {
        return dataFineValidita;
    }
    
    public void setDataFineValidita(String dataFineValidita)
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
    
    public String getCodiceTipologia()
    {
        return codiceTipologia;
    }
    
    public void setCodiceTipologia(String codiceTipologia)
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
    

