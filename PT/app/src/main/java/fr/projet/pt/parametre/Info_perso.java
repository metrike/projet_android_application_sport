package fr.projet.pt.parametre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuWrapperICS;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import org.json.JSONObject;

import java.net.ConnectException;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.projet.pt.Accueil;
import fr.projet.pt.Connexion;
import fr.projet.pt.Page2;
import fr.projet.pt.Parametre;
import fr.projet.pt.R;
import fr.projet.pt.databinding.FragmentAccueilBinding;

public class Info_perso extends AppCompatActivity {

    private String login, age, poids, taille,sexe;
    private Button valider;
    private RadioGroup recupsexe;
    private EditText retage, retpoids, rettaille;
    private String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/updateuser.php";
    private  RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_perso);
        retage = findViewById(R.id.age);
        retpoids = findViewById(R.id.poids);
        rettaille = findViewById(R.id.taille);
        valider = findViewById(R.id.btnvalid);

        RecupBdd();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb1:
                if (checked)
                    sexe = "homme";
                    break;
            case R.id.rb2:
                if (checked)
                    sexe = "femme";
                    break;
        }
    }



    public void update(View view) {
        login = Connexion.user;
        age = retage.getText().toString().trim();
        poids = retpoids.getText().toString().trim();
        taille = rettaille.getText().toString().trim();

        if(age.equals("")){
            age = "null";
        }
        if(poids.equals("")){
            poids = "null";
        }
        if(taille.equals("")){
            poids = "null";
        }
        if(sexe == null){
            sexe = "null";
        }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("marche")) {
                        Toast.makeText(Info_perso.this, "Données sauvegardées", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Info_perso.this, Page2.class));
                    }
                    else if (response.equals("failure")) {
                        Toast.makeText(Info_perso.this, "Problème avec la base de données", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Info_perso.this, "Problème", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("login",login );
                    data.put("age", age);
                    data.put("poids", poids);
                    data.put("taille",taille);
                    data.put("sexe",sexe);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

    public void RecupBdd(){
        
        String URLrecup = "https://dwarves.iut-fbleau.fr/~metri/PT/recup_donnee.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLrecup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONObject client = obj.getJSONObject("contenue");

                    if(!client.getString("age").equals("-1")){
                        retage.setText(client.getString("age"));
                    }
                    if(!client.getString("poids").equals("-1")){
                        retpoids.setText(client.getString("poids"));
                    }
                    if(!client.getString("taille").equals("-1")){
                        rettaille.setText(client.getString("taille"));
                    }
                    if(!client.getString("sexe").equals("null")){
                        if(client.getString("sexe").equals("homme")){
                            RadioButton homme = findViewById(R.id.rb1);
                            homme.setChecked(true);
                        }
                        else if(client.getString("sexe").equals("homme")){
                            RadioButton femme = findViewById(R.id.rb2);
                            femme.setChecked(true);
                        }

                    }

                } catch (JSONException e) {
                    System.out.println(e);

                }

                if (response.equals("failure")) {
                    Toast.makeText(Info_perso.this, "erreur insertion", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("echec")) {
                    Toast.makeText(Info_perso.this, "pas de login", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString().trim());
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("login", Connexion.user);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}