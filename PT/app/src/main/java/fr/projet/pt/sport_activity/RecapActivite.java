package fr.projet.pt.sport_activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import fr.projet.pt.Historique;
import fr.projet.pt.R;
import fr.projet.pt.sport_activity.localisation.MapsActivity;
import fr.projet.pt.sport_activity.localisation.RecordJourney;

public class RecapActivite extends AppCompatActivity {

    private TextView titre,distance,vitesse,temps,date,note;
    private String d,t,s;
    private Button map;
    private ImageView image;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap_activite);
        Intent i=getIntent();
        d=i.getStringExtra("date");
        t=i.getStringExtra("temps");
        s=i.getStringExtra("sport");
        map = findViewById(R.id.map_);
        image=findViewById(R.id.img_recap);


        recup_id(new VolleyCallback(){
            @Override
            public void onSuccess(String result) {
                id  = Integer.parseInt(result);

            }
        },d,s,t);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(RecapActivite.this, MapsActivity.class);
                Bundle b = new Bundle();
                b.putLong("journeyID", id);
                map.putExtras(b);
                startActivity(map);
            }
        });

        titre = findViewById(R.id.affi_titre);
        distance = findViewById(R.id.affi_distance);
        vitesse = findViewById(R.id.affi_vitesse);
        temps = findViewById(R.id.affi_temps);
        date = findViewById(R.id.affi_date);
        note = findViewById(R.id.affi_note);

        recup_donnee(new VolleyCallback(){
            @Override
            public void onSuccess(String result) {
                String[] tabentier = result.split(",");

                titre.setText(tabentier[0]);
                distance.setText(tabentier[1]);
                vitesse.setText(tabentier[2]);
                temps.setText(tabentier[3]);
                date.setText(tabentier[4]);
                note.setText(tabentier[5]);

                if(titre.getText().toString().equals("Course")){
                    image.setImageResource(R.drawable.runner);
                }else if(titre.getText().toString().equals("Vélo")){
                    image.setImageResource(R.drawable.ic_baseline_directions_bike_24);
                }else if(titre.getText().toString().equals("Basket")){
                    image.setImageResource(R.drawable.ic_baseline_sports_basketball_24);
                }else if(titre.getText().toString().equals("Football")){
                    image.setImageResource(R.drawable.ic_baseline_sports_soccer_24);
                }else if(titre.getText().toString().equals("Natation")){
                    image.setImageResource(R.drawable.swim);
                }else if(titre.getText().toString().equals("Aviron")){
                    image.setImageResource(R.drawable.ic_baseline_rowing_24);
                }else if(titre.getText().toString().equals("Ski")){
                    image.setImageResource(R.drawable.ic_baseline_downhill_skiing_24);
                }else if(titre.getText().toString().equals("Handball")){
                    image.setImageResource(R.drawable.ic_baseline_sports_handball_24);
                }else if(titre.getText().toString().equals("Golf")){
                    image.setImageResource(R.drawable.ic_baseline_golf_course_24);
                }
            }
        },d,s,t);
    }


    public void recup_donnee(final VolleyCallback callback,String date,String sport, String temps){
        String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/recup_historique.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                String activite="";
                System.out.println(response);

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

    public void recup_id(final VolleyCallback callback,String date,String sport, String temps){
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