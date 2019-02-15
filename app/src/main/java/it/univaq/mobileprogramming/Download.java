package it.univaq.mobileprogramming;


import android.content.Context;
import android.media.effect.Effect;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import it.univaq.mobileprogramming.database.D_Database;
import it.univaq.mobileprogramming.entity.E_Farmacia;
import it.univaq.mobileprogramming.entity.E_Preferita;


class Download
{
    D_Database roomDB;
    
    public Download(Context context)
    {
        roomDB = D_Database.getInstance(context);
    }
    
    
    /**
     * Thread-safe function to handle the download
     * Putting a thread is way more effective than following this: https://stackoverflow.com/questions/25093546/android-os-networkonmainthreadexception-at-android-os-strictmodeandroidblockgua
     * Simply looking at https://developer.android.com/reference/android/os/StrictMode it's recommended Thread or AsyncTask over StrictMode
     */
    public void saveToDB()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                csvParser_Base();
            }
        }).start();
    }
    
    
    /**
     * Function based on https://stackoverflow.com/questions/4120942/programatically-downloading-csv-files-with-java
     * This function is NOT thread safe. In order to run it correctly it needs to be put in a thread
     */
    private void csvParser_Base()
    {
        String url_csv = "http://www.dati.salute.gov.it/imgs/C_17_dataset_5_download_itemDownload0_upFile.CSV";
        URL url = null;
        try
        {
            url = new URL(url_csv);
            InputStream in = url.openStream();
            Reader reader = new InputStreamReader(in, "UTF-8");
            
    
            //https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVParser.html
            //https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/
            CSVParser parser = new CSVParser(reader,
                                             CSVFormat.EXCEL
                                                     .withDelimiter(';') //Because italian government SUCKS
                                                     .withHeader()
                                                     .withFirstRecordAsHeader() //Returns a new CSVFormat using the first record as header
                                                     .withIgnoreEmptyLines() //Returns a new CSVFormat with the "empty line skipping" behavior of the format set to true
            );
            
            for(CSVRecord record : parser)
            {
                safeExcelReader(record);
            }
            parser.close(); //Parsing is DONE
            reader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void showRecords()
    {
        List<E_Farmacia> EF1 = roomDB.D_Farmacia_Access().getAllPharmaciesIn("TORINO");
        int max = 15;
        for(int i = 0; i < max; i++)
        {
            showF(EF1, i);
    
        }
        int sizeF = EF1.size();
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println("Total pharms now = " + sizeF);
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - ");
        for(int i = 0; i < max; i++)
        {
            showF(EF1, sizeF - 5 + i);
        
        }
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - ");
    }
    
    private void showF(List<E_Farmacia> EF1, int i)
    {
        System.out.println("i = " + i + "\n ID = " + EF1.get(i).getId() +
                                   ", Indirizzo: " + EF1.get(i).getIndirizzo() +
                                   ", Comune: " + EF1.get(i).getComune() +
                                   ", IVA: " + EF1.get(i).getIva() +
                                   ", Lat: " + EF1.get(i).getLatitudine());
    }
    
    
    /**
     * Parse each record and save it to a temporary array
     *
     * @param record Current Excel line to save
     */
    private void safeExcelReader(CSVRecord record)
    {
        try
        {
            if(record.get(15).equals("-") //DATAFINEVALIDITA == "-" indica una farmacia non chiusa
                    && (!record.get(0).equals("0") //Often it will wrongly read a "0" (Probably because we have a STREAM of data and records aren't fetched soon enough)
                        || !record.get(0).equals("null"))) //or "null" value so we want to discard these records
            {
                E_Farmacia f = new E_Farmacia(
                        Long.parseLong(record.get(0)),//ID
                        record.get(2), //INDIRIZZO
                        record.get(3), //DESCRIZIONEFARMACIA
                        record.get(4), //PARTITAIVA
                        record.get(7), //DESCRIZIONECOMUNE
                        record.get(8), //FRAZIONE
                        record.get(11),//DESCRIZIONEPROVINCIA
                        record.get(13),//DESCRIZIONEREGIONE
                        record.get(14),//DATAINIZIOVALIDITA
                        record.get(18),//LATITUDINE
                        record.get(19) //LONGITUDINE
                );
                roomDB.D_Farmacia_Access()
                        .insertThis(f);
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            //System.out.println("Ultimo indice fatto = " + record.get(0));
            
            //Line 26587 (record.get(0) = 12045) presents an error and throws a ArrayIndexOutOfBoundsException
            
            //Why not an IF() ELSE()? Because we have a LOT of data to analyze and adding a new
            //instruction to check for each record would slow down the whole process
            
            //In this way we just hardcode it here
            E_Farmacia f = new E_Farmacia(
                    Long.parseLong("12045"),//ID
                    "Via Passanti, 176/178", //INDIRIZZO
                    "Farmacia D'Ambrosio Fernanda S.n.c. Dei Dott.ri D'Ambrosio Fernanda E Cerciello Francesco Claudio", //DESCRIZIONEFARMACIA
                    "8978851213", //PARTITAIVA
                    "SAN GIUSEPPE VESUVIANO", //DESCRIZIONECOMUNE
                    "-", //FRAZIONE
                    "NAPOLI",//DESCRIZIONEPROVINCIA
                    "CAMPANIA",//DESCRIZIONEREGIONE
                    "01/11/2018",//DATAINIZIOVALIDITA
                    "40,8275996228434",//LATITUDINE
                    "14,5041034840709" //LONGITUDINE
            );
            roomDB.D_Farmacia_Access()
                .insertThis(f);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
