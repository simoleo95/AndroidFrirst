package it.univaq.mobileprogramming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import it.univaq.mobileprogramming.entity.Farmacia;
import it.univaq.mobileprogramming.entity.Location;

public class MainActivity extends AppCompatActivity // <- to ensure backward compability
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Link this class(context) to a specific XML (activity_main)

//        Location[] data = new Location[4];
//        Farmacia[] farmacia = new Farmacia[2];
//
//        Location c1 = new Location("Roma", "Lazio");
//        data[0] = c1;
//
//        Location c2 = new Location("L'Aquila", "Abruzzo");
//        data[1] = c2;
//
//        Location c3 = new Location("Firenze", "Toscana");
//        data[2] = c3;
//
//        Location c4 = new Location("Ascoli Piceno", "Marche");
//        data[3] = c4;
//
//        Farmacia farm1 = new Farmacia();
//        farm1.setCity(c1);
//        farm1.setName("Popoli");
//
//        Farmacia farm2 = new Farmacia();
//        farm2.setName("Pippo");
//        farm2.setCity(c2);

//
//
//
//    //THREAD
//        //Request Volley -> HTTP
//        //Request Queue in SINGLETON
//        RequestQueue dataQueue = Volley.newRequestQueue(this); //TOO GENERIC! Need to had the Cache
//
//        //Cache handling
//        //Network for transporting reqs
//
//        int CACHE_SIZE = 16 * 1024 * 1024; //16MB
//        DiskBasedCache cache = new DiskBasedCache(this.getCacheDir(), CACHE_SIZE);
//        //Network network = new BasicNetwork(new HurlStack());
//        dataQueue = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
//
//        //dataQueue.start();
//
//
//
//
//
//        String url = "your_address";
//
//        //Approach #1 - Simple Request
//        StringRequest stringGET = new StringRequest(
//                StringRequest.Method.GET,
//                url,
//                new Response.Listener<String>() //Listener for Server Response
//                {
//                    @Override
//                    public void onResponse(String response) {}
//                },
//                new Response.ErrorListener() //Listener for Errors
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {}
//                }
//        );
//        dataQueue.add(stringGET);
//
//
//        //Approach #2 - JSON Object
//        JsonObjectRequest requestOBJ = new JsonObjectRequest(
//                JsonObjectRequest.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {}
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {}
//                }
//        );
//        dataQueue.add(requestOBJ);
//
//
//        //Approach #3 - JSON Array
//        JsonArrayRequest requestArray = new JsonArrayRequest(
//                JsonArrayRequest.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONArray>()
//                {
//                    @Override
//                    public void onResponse(JSONArray response) {}
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {}
//                }
//        );
//        dataQueue.add(requestArray);
//
//
//        //PARSE JSON CODE!!!!
    
    
    
        System.out.println("Qui dovresti esserci");
        ParseCSVfromURL f = new ParseCSVfromURL();
        f.csvParser();
        System.out.println("Ho finito tutto...");
        
        
        
        
        
        Location[] loc = new Location[2];
        Farmacia[] farmacia = new Farmacia[2];
    
        Farmacia f1 = new Farmacia();
        f1.setDescrizione("FARMACIA CASABIANCA S.N.C. DEI DOTT.RI PALINI ROBERTA, RESTIVO PIETRO E CANTONI ANDREA");
        
        Location l1 = new Location();
        l1.setIndirizzo("Via Buggianese, 108");
        f1.setLocation(l1);
        
        
        
        Farmacia f2 = new Farmacia();
        f2.setDescrizione("SAN FRANCESCO DA PAOLA");
        Location l2 = new Location();
        l2.setIndirizzo("Via San Francesco Da Paola, 10");
        f2.setLocation(l2);
        
        
        farmacia[0]=f1;
        farmacia[1]=f2;
        System.out.println("Ho impostato i nomi per le farmacie");

        // Adapter adapter = new Adapter(data);
        AdapterRecycler adapter = new AdapterRecycler(farmacia);


        //Here link the main_list to the context (MainActivity)
        RecyclerView list = findViewById(R.id.main_list); //Search for R.id.main_list in the activity_main.xml because it's the xml file linked in the onCreate() function
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }
    
}
