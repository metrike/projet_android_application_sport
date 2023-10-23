package fr.projet.pt.sport_activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import fr.projet.pt.Connexion;
import fr.projet.pt.R;
import fr.projet.pt.sport_activity.localisation.MapsActivity;

public class RecapTabata extends AppCompatActivity {

    private TextView rounds,temps_off,temps_on,temps,date,note;
    private String d,t,s;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap_tabata);
        Intent i=getIntent();

        d=i.getStringExtra("date");
        t=i.getStringExtra("temps");
        s=i.getStringExtra("sport");


        recup_id(new RecapActivite.VolleyCallback(){
            @Override
            public void onSuccess(String result) {
                id  = Integer.parseInt(result);

            }
        },d,s,t);



        temps_on = findViewById(R.id.temps_on_recap);
        temps_off = findViewById(R.id.temps_off_recap);
        temps = findViewById(R.id.temps_recap);
        date = findViewById(R.id.date_tabata);
        note = findViewById(R.id.note_tabata);
        rounds=findViewById(R.id.rounds_recap);

        recup_donnee(new RecapActivite.VolleyCallback(){
            @Override
            public void onSuccess(String result) {
                String[] tabentier = result.split(",");

                rounds.setText(tabentier[0]);
                temps.setText(tabentier[1]);
                temps_on.setText(tabentier[2]);
                temps_off.setText(tabentier[3]);
                date.setText(tabentier[4]);
                note.setText(tabentier[5]);
            }
        },d,s,t);

        RecapTabata.this.onBackPressed();
    }
    /*@Override
    public void onBackPressed(){
        Intent i = new Intent(RecapActivite.this,Historique.class);
        startActivity(i);
        finish();
    }*/

    public void recup_donnee(final RecapActivite.VolleyCallback callback, String date, String sport, String temps){
        String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/recap_info_tabata.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                String activite="";


                try {
                    JSONArray obj = new JSONArray(response);
                    for (int i =0;i< obj.length();i++){
                        activite = activite + obj.getString(i);
                    }
                    activite = deleteChar(activite);

                    callback.onSuccess(activite);
                } catch (JSONException e) {
                    System.out.println(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString().trim());
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("login", Connexion.user);
                data.put("date",date);
                data.put("sport",sport);
                data.put("temps",temps);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void recup_id(final RecapActivite.VolleyCallback callback, String date, String sport, String temps){
        String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/recup_id.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString().trim());
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("login", Connexion.user);
                data.put("date",date);
                data.put("sport",sport);
                data.put("temps",temps);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public String deleteChar(String activite){
        activite = activite.replaceAll("[ +^\" ]","");
        activite = activite.replaceAll("\\\\", "");
        activite = activite.replaceAll("\\[","");
        activite = activite.replaceAll("]",",");

        return activite;
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }
}