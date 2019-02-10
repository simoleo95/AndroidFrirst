package it.univaq.mobileprogramming;


import android.animation.FloatArrayEvaluator;
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


class ParseCSVfromURL
{
    //All the pharmacies found in the Excel file
    private ArrayList<String[]> farmacie = new ArrayList<String[]>();
    
    /**
     * Thread-safe function to handle the download
     * Putting a thread is way more effective than following this: https://stackoverflow.com/questions/25093546/android-os-networkonmainthreadexception-at-android-os-strictmodeandroidblockgua
     * Simply looking at https://developer.android.com/reference/android/os/StrictMode it's recommended Thread or AsyncTask over StrictMode
     */
    public void csvParser()
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
                //DEBUG ONLY
//                try
//                {
//                    if(Integer.parseInt(record.get(0))%1000 == 0)
//                    {
//                        System.out.println("FATTI 500 RECORDS!!! " + record.get(0));
//                    }
//                }
//                catch(NumberFormatException e) { }
    
                safeExcelReader(record);
            }
            parser.close();
            reader.close();
            saveToDB();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Parse each record and save it to a temporary array
     * This function will totally IGNORE the error on Excel line 26587, index 12045, and will just
     * proceed to the next record.
     * @param record Current Excel line to save
     * @return an array containing the necessary details to save
     */
    private void safeExcelReader(CSVRecord record)
    {
        String farmacia[] = new String[10];
        try
        {
            if(record.get(15).equals("-")) //DATAFINEVALIDITA == "-" indica una farmacia non chiusa
            {
                farmacia[0] = record.get(2); //INDIRIZZO
                farmacia[1] = record.get(3); //DESCRIZIONEFARMACIA
                farmacia[2] = record.get(4); //PARTITAIVA
                farmacia[3] = record.get(7); //DESCRIZIONECOMUNE
                farmacia[4] = record.get(8); //FRAZIONE
                farmacia[5] = record.get(11);//DESCRIZIONEPROVINCIA
                farmacia[6] = record.get(12);//CODICEREGIONE
                farmacia[7] = record.get(14);//DATAINIZIOVALIDITA
                farmacia[8] = record.get(18);//LATITUDINE
                farmacia[9] = record.get(19);//LONGITUDINE
                
                farmacie.add(farmacia);
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            //System.out.println("Ultimo indice fatto = " + record.get(0));
            
            //Line 26587 (record.get(0) = 12045) presents an error and throws a ArrayIndexOutOfBoundsException
            
            //Why not an IF() ELSE()? Because we have a LOT of data to analyze and adding a new
            //instruction to check for each record would slow down the whole process
            //In this way we just skip the record. I'm sorry for that.
        }
    }
    
    
    /**
     * Save the ArrayList into the DB
     */
    private void saveToDB()
    {
        System.out.println("Farmacie totali = " + farmacie.size());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * UNUSED ---
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


/**
 * UNUSED ---
 */
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
