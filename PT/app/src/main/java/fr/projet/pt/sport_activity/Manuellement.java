package fr.projet.pt.sport_activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import fr.projet.pt.Connexion;
import fr.projet.pt.Page2;
import fr.projet.pt.R;
import fr.projet.pt.sport_activity.localisation.LocationService;

public class Manuellement extends AppCompatActivity {

    public static int tmp=0;

    public static EditText km, vitesse, tps, date, note;
    private Button valider;
    private String val_tps, val_date, val_km, val_vitesse, val_note;
    private int km_f, vitesse_f, note_f;
    public static String Name;
    private ImageView img;
    private long journeyID;
    public static LocationService.LocationServiceBinder locationService;
    private TextView texte;
    private String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/ajout_manuel.php";
    private int ordre=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manuellement);

        Intent intent = getIntent();
        Name = intent.getStringExtra("Name");

        km = findViewById(R.id.km);
        vitesse = findViewById(R.id.vitesse);
        tps = findViewById(R.id.temps);
        date = findViewById(R.id.date);
        note = findViewById(R.id.note);
        valider = findViewById(R.id.valid_ajout);
        texte = findViewById(R.id.sport_txt);

        img=findViewById(R.id.sport_img);
        if(Name.equals("Course")){
            img.setImageResource(R.drawable.runner);
        }else if(Name.equals("Vélo")){
            img.setImageResource(R.drawable.ic_baseline_directions_bike_24);
        }else if(Name.equals("Basket")){
            img.setImageResource(R.drawable.ic_baseline_sports_basketball_24);
        }else if(Name.equals("Football")){
            img.setImageResource(R.drawable.ic_baseline_sports_soccer_24);
        }else if(Name.equals("Natation")){
            img.setImageResource(R.drawable.swim);
        }else if(Name.equals("Musculation")){
            img.setImageResource(R.drawable.building);
        }else if(Name.equals("Aviron")){
            img.setImageResource(R.drawable.ic_baseline_rowing_24);
        }else if(Name.equals("Ski")){
            img.setImageResource(R.drawable.ic_baseline_downhill_skiing_24);
        }else if(Name.equals("Handball")){
            img.setImageResource(R.drawable.ic_baseline_sports_handball_24);
        }else if(Name.equals("Golf")){
            img.setImageResource(R.drawable.ic_baseline_golf_course_24);
        }

        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(count==1 && count!=s.length()){
                    date.setText("");
                    ordre=1;
                }
                System.out.println(count+" "+s.length()+" "+start+" "+after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()==4 && ordre==0){
                    Editable recup=date.getText();
                    date.setText(recup+"-");
                    date.setSelection(editable.length()+1);
                }
                if(editable.length()==7 && ordre==0){
                    Editable recup=date.getText();
                    date.setText(recup+"-");
                    date.setSelection(editable.length()+1);
                }
                ordre=0;
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val_km = km.getText().toString().trim();
                val_vitesse = vitesse.getText().toString().trim();
                val_note = note.getText().toString().trim();
                val_tps = tps.getText().toString().trim();
                val_date = date.getText().toString().trim();

                if(val_km.equals("") || val_vitesse.equals("")|| val_note.equals("")|| val_tps.equals("")|| val_date.equals("")) {
                    Toast.makeText(Manuellement.this, "Veuillez remplir tout les champs", Toast.LENGTH_SHORT).show();
                }else{

                    sauvegarder(view);
                    Intent i=new Intent(Manuellement.this, Page2.class);
                    startActivity(i);
                }
            }
        });
    }



    public void sauvegarder(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(Manuellement.this, "Sauvegardé dans la BD", Toast.LENGTH_SHORT).show();
                } else if (response.equals("failure")) {
                    Toast.makeText(Manuellement.this, "Login déjà existant", Toast.LENGTH_SHORT).show();
                    System.out.println("insertion dans la BD NOT OK");
                }
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
                data.put("login", Connexion.user);
                data.put("distance", val_km+" KM");

                if(val_vitesse.contains(",")){
                    val_vitesse = val_vitesse.replace(",",".");
                }


                if(val_vitesse.contains(".")){
                    data.put("vitesse",val_vitesse+ " KM/H");
                }
                else{
                    data.put("vitesse",val_vitesse + ".00 KM/H");
                }
                data.put("id",String.valueOf(journeyID));
                data.put("date", val_date);
                data.put("temps", calculateTime(Integer.parseInt(val_tps)));
                data.put("sport", Name);
                data.put("note",val_note);

                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public String calculateTime(int m) {
        int seconds=60*m;
        int sec = seconds % 60;
        int minutes = seconds % 3600 / 60;
        int hours = seconds % 86400 / 3600;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d",hours,minutes, sec);
    }

}