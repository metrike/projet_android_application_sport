package fr.projet.pt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.projet.pt.sport_activity.localisation.RecordJourney;

public class Inscription extends AppCompatActivity {

    private static Button valider_inscrip;
    private static EditText passwd,check_passwd,mail,user,nom,prenom;
    private String login, email, password, reenterPassword,noms,prenoms;
    private String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/registrer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        valider_inscrip = findViewById(R.id.valid_inscription);
        passwd = findViewById(R.id.passwd);
        check_passwd = findViewById(R.id.passwd2);
        mail = findViewById(R.id.mail);
        user = findViewById(R.id.user);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
    }

    @Override
    public void onBackPressed(){
        Intent i=new Intent(Inscription.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void save(View view) {

        login = user.getText().toString().trim();
        email = mail.getText().toString().trim();
        password = passwd.getText().toString().trim();
        reenterPassword = check_passwd.getText().toString().trim();
        noms = nom.getText().toString().trim();
        prenoms = prenom.getText().toString().trim();

        if(!password.equals(reenterPassword)){
            Toast.makeText(this, "Les mots de passe ne sont pas identiques ", Toast.LENGTH_SHORT).show();
        }
        else if(login.equals("")){
            Toast.makeText(Inscription.this, "Login nécessaire", Toast.LENGTH_SHORT).show();
        }
        else if(noms.equals("")){
            Toast.makeText(Inscription.this, "Nom nécessaire", Toast.LENGTH_SHORT).show();
        }
        else if(prenoms.equals("")){
            Toast.makeText(Inscription.this, "Prénom nécessaire", Toast.LENGTH_SHORT).show();
        }
        else if(email.equals("")){
            Toast.makeText(Inscription.this, "Email nécessaire", Toast.LENGTH_SHORT).show();
        }
        else if(!isEmailValid(email)){
            Toast.makeText(Inscription.this, "Email incorrect", Toast.LENGTH_SHORT).show();
        }
        else if(password.equals("")){
            Toast.makeText(Inscription.this, "Mot de passe nécessaire", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("marche")) {

                        login(view);
                        valider_inscrip.setClickable(false);

                    }
                    else if (response.equals("marche_po")) {
                        Toast.makeText(Inscription.this, "erreur insertion", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.equals("echec")) {
                        Toast.makeText(Inscription.this, "Login déjà existant", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Inscription.this, "erreur mais jsp pourquoi ", Toast.LENGTH_SHORT).show();
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
                    data.put("email", email);
                    data.put("password", password);
                    data.put("noms",noms);
                    data.put("prenoms",prenoms);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        finish();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}


