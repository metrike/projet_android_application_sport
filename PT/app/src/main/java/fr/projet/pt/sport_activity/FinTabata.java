package fr.projet.pt.sport_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
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
import java.util.Locale;
import java.util.Map;

import fr.projet.pt.Accueil;
import fr.projet.pt.Connexion;
import fr.projet.pt.Page2;
import fr.projet.pt.R;

public class FinTabata extends AppCompatActivity {

    private TextView temps_on,temps_off,sport,rounds,temps_t;
    private EditText note;
    private String temps_total;
    private Button valider;
    private String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/activity_tabata.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_tabata);
        Intent i=getIntent();
        temps_on=findViewById(R.id.temps_on_fin);
        temps_off=findViewById(R.id.temps_off_fin);
        rounds=findViewById(R.id.rounds);
        sport=findViewById(R.id.sport_tabata);
        note=findViewById(R.id.note_txt_tabata);
        valider=findViewById(R.id.valider_activite_tabata);
        temps_t=findViewById(R.id.temps_fin_tabata);
        temps_on.setText(calculateTime(Integer.parseInt(i.getStringExtra("temps_on"))));
        temps_off.setText(calculateTime(Integer.parseInt(i.getStringExtra("temps_off"))));
        temps_total=i.getStringExtra("temps_total");
        temps_t.setText(calculateTime(Integer.parseInt(temps_total)));

        rounds.setText(i.getStringExtra("rounds"));
        sport.setText("Musculation/Tabata");

        System.out.println("login dans fin tabata "+ Connexion.user);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePhp();
                Intent i=new Intent(FinTabata.this,Page2.class);
                startActivity(i);

            }
        });

    }

    public void savePhp(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());

        Toast.makeText(FinTabata.this, "Activité terminée et sauvegardée ! ", Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println( "response: "+ response);

                if (response.equals("success")) {

                } else if (response.equals("failure")) {
                    Toast.makeText(FinTabata.this, "erreur insertion", Toast.LENGTH_SHORT).show();
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

                Map<String, String> data = new HashMap<>();
                data.put("id",String.valueOf(Accueil.ID));
                data.put("login", Connexion.user);
                data.put("rounds",rounds.getText().toString());
                data.put("temps_on", temps_on.getText().toString());
                data.put("temps_off", temps_off.getText().toString());
                data.put("date", formattedDate);
                data.put("temps", temps_t.getText().toString());
                data.put("note", note.getText().toString());
                data.put("sport","Musculation");

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public String calculateTime(int seconds) {

        int sec = seconds % 60;
        int minutes = seconds % 3600 / 60;
        int hours = seconds % 86400 / 3600;




        return String.format(Locale.getDefault(), "%02d:%02d:%02d",hours,minutes,sec);
    }
}