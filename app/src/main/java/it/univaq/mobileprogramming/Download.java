package it.univaq.mobileprogramming;


import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.FloatRange;
import android.util.Log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.csv.CSVFormat.EXCEL;


class parseCSVfromURL
{
    //Function based on https://stackoverflow.com/questions/4120942/programatically-downloading-csv-files-with-java
    public void try_1()
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
                                             CSVFormat.EXCEL.withHeader( //Permette di non caricare tutto il file ma solo le colonne indicate
                                                     "INDIRIZZO", "DESCRIZIONEFARMACIA", "PARTITAIVA", "DESCRIZIONECOMUNE",
                                                     "FRAZIONE", "DESCRIZIONEPROVINCIA", "DESCRIZIONEREGIONE",
                                                     "DATAINIZIOVALIDITA", "DATAFINEVALIDITA", "LATITUDINE", "LONGITUDINE"
                                             ));
            String indirizzo, descFarmacia, partitaIVA, descComune, frazione, descProvincia, descRegione, inizioAttivita;
            String latitudine, longitudine;
            
            ArrayList<String[]> farmacie = new ArrayList<String[]>();
            String farmacia[] = new String[10];
            for(CSVRecord record : parser)
            {
                if(record.get("DATAFINEVALIDITA").equals("-")) //indica una farmacia non chiusa
                {
//                    indirizzo       = record.get("INDIRIZZO");
//                    descFarmacia    = record.get("DESCRIZIONEFARMACIA");
//                    partitaIVA      = record.get("PARTITAIVA");
//                    descComune      = record.get("DESCRIZIONECOMUNE");
//                    frazione        = record.get("FRAZIONE");
//                    descProvincia   = record.get("DESCRIZIONEPROVINCIA");
//                    descRegione     = record.get("DESCRIZIONEREGIONE");
//                    inizioAttivita  = record.get("DATAINIZIOVALIDITA");
//                    latitudine      = record.get("LATITUDINE");
//                    longitudine     = record.get("LONGITUDINE");
    
                    farmacia[0] = record.get("INDIRIZZO");
                    farmacia[1] = record.get("DESCRIZIONEFARMACIA");
                    farmacia[2] = record.get("PARTITAIVA");
                    farmacia[3] = record.get("DESCRIZIONECOMUNE");
                    farmacia[4] = record.get("FRAZIONE");
                    farmacia[5] = record.get("DESCRIZIONEPROVINCIA");
                    farmacia[6] = record.get("DESCRIZIONEREGIONE");
                    farmacia[7] = record.get("DATAINIZIOVALIDITA");
                    farmacia[8] = record.get("LATITUDINE");
                    farmacia[9] = record.get("LONGITUDINE");
                    
                    farmacie.add(farmacia);
                }
            }
            parser.close();
            reader.close();
            
            //e qui usare l'arraylist per salvare tutto nel DB
    
    
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    /**
     * This method uses Java NIO library to optimize the download
     * It enables multi-threading operations and works in background
     */
    public void try_3()
    {
        //https://stackabuse.com/how-to-download-a-file-from-a-url-in-java/
        try
        {
            String csv_url = "http://www.dati.salute.gov.it/imgs/C_17_dataset_5_download_itemDownload0_upFile.CSV";
            URL url = new URL(csv_url);
            InputStream in = url.openStream();
            ReadableByteChannel read = Channels.newChannel(in);
            
            FileOutputStream out = new FileOutputStream("./excel.csv");
            FileChannel write = out.getChannel();
            write.transferFrom(read, 0, Long.MAX_VALUE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
    
    


class downloadExcel extends AsyncTask<URL, Integer, Long>
{
    //https://stackabuse.com/how-to-download-a-file-from-a-url-in-java/
    @Override
    protected Long doInBackground(URL... excelFile)
    {
        try
        {
            File saveHere = new File(Environment.getExternalStorageDirectory() + "/Excel Data");
            if(!saveHere.exists())
            {
                saveHere.mkdir();
            }
    
            String excel = "http://www.dati.salute.gov.it/imgs/C_17_dataset_5_download_itemDownload0_upFile.CSV";
            URL url = null;
            try
            {
                url = new URL(excel);
            }
            catch(MalformedURLException e)
            {
                Log.e("URL error: ", e.getMessage());
            }
    
            URLConnection connection = url.openConnection();
            connection.connect();
    
            //Save the file
            InputStream in = new BufferedInputStream(url.openStream(), 8192*2);
            
            //Strategy #1 - https://www.baeldung.com/java-download-file
            OutputStream out = new FileOutputStream(saveHere + "excel.csv");
            
            byte saveByte[] = new byte[1024];
            int c;
            while((c = in.read(saveByte)) != -1)
            {
                out.write(saveByte, 0, c);
            }
            out.flush();
            out.close();
            in.close();
    
        }
        catch(Exception e)
        {
            Log.e("Error occurred: ", e.getMessage());
        }
        
        return null;
    }
}
