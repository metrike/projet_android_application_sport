package fr.projet.pt.sport_activity;

import static fr.projet.pt.sport_activity.Manuellement.date;
import static fr.projet.pt.sport_activity.Manuellement.locationService;
import static fr.projet.pt.sport_activity.Manuellement.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.projet.pt.Accueil;
import fr.projet.pt.Connexion;
import fr.projet.pt.Page2;
import fr.projet.pt.R;
import fr.projet.pt.sport_activity.localisation.DB;
import fr.projet.pt.sport_activity.localisation.LocationService;
import fr.projet.pt.sport_activity.localisation.RecordJourney;

public class FinActivite extends AppCompatActivity {

    private TextView vitesse, temps, sport, distance;
    private EditText commentaire, note;
    private Button valider;
    private String s;
    private String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/activity.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_activite);
        Intent i = getIntent();
        s=i.getStringExtra("sport");


        vitesse = findViewById(R.id.vitesse_txt);
        distance = findViewById(R.id.distance_txt);
        sport = findViewById(R.id.sport_txt);
        temps = findViewById(R.id.temps_txt);
        commentaire = findViewById(R.id.commentaire);
        note = findViewById(R.id.note_txt);
        valider = findViewById(R.id.valider_activite);

        vitesse.setText(i.getStringExtra("vitesse"));
        distance.setText(i.getStringExtra("distance"));
        temps.setText(i.getStringExtra("temps"));
        sport.setText(i.getStringExtra("sport"));

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(note.getText().toString().equals("")){
                    Toast.makeText(FinActivite.this, "Veuillez remplir les champs obligatoire", Toast.LENGTH_SHORT).show();
                }else{
                    savePhp();
                    String tmp;

                    if(!sport.getText().toString().equals(""))
                        tmp=sport.getText().toString();
                    else
                        tmp=s;

                    Intent intent = new Intent(FinActivite.this, Page2.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


    public void savePhp(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());

        Toast.makeText(FinActivite.this, "Activité terminée et sauvegardée ! ", Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println( "response: "+ response);

                if (response.equals("success")) {
                    Toast.makeText(FinActivite.this, " ca marche 'normalement'", Toast.LENGTH_SHORT).show();

                } else if (response.equals("failure")) {
                    Toast.makeText(FinActivite.this, "erreur insertion", Toast.LENGTH_SHORT).show();
                }

           //     Toast.makeText(getApplicationContext(), "C'est bon".trim(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String d=distance.getText().toString().trim().replace(",",".");
                String v=vitesse.getText().toString().trim().replace(",",".");

                Map<String, String> data = new HashMap<>();
                data.put("id",String.valueOf(Accueil.ID));
                data.put("login", Connexion.user);
                data.put("distance", d);
                data.put("vitesse",v);
                data.put("date", formattedDate);
                data.put("temps", temps.getText().toString());
                data.put("note", note.getText().toString());
                System.out.println( "Note :! "+note.getText().toString());

                if(!sport.getText().toString().equals(""))
                    data.put("sport", sport.getText().toString());
                else
                    data.put("sport",s);

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}