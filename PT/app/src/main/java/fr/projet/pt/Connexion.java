package fr.projet.pt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import fr.projet.pt.sport_activity.localisation.LocationService;

public class Connexion extends AppCompatActivity {


    private EditText etUser, etPassword;
    private String password;
    public static String user;
    public String saveP, saveU="";
    private String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/login.php";

    private SharedPreferences mSharedPreferences;
    private String log,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        log =  mSharedPreferences.getString("login","");
        pass =  mSharedPreferences.getString("password","");

        View a =new View(getApplicationContext());
        if(!log.equals("") && !pass.equals("")){
            login_(a);
        }

        etUser = findViewById(R.id.login_connexion);
        etPassword = findViewById(R.id.passwd_connexion);
    }

    @Override
    public void onBackPressed(){
        Intent i=new Intent(Connexion.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void login_(View view) {
        if(!log.equals("") && !pass.equals("")){
            user=log;
            password=pass;
        }else {
            user = etUser.getText().toString().trim();
            password = etPassword.getText().toString().trim();
        }

        if(user.equals("") || password.equals("")){
            Toast.makeText(Connexion.this, "Identifiant ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
        }
        else if (!user.equals("") && !password.equals("")) {


            if (isConnected()) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("res", response);
                        if (response.equals("success")) {
                            register(view);
                        } else if (response.equals("failure")) {
                            Toast.makeText(Connexion.this, "Identifiant ou mot de passe incorrect ", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("connect")) {
                            Toast.makeText(Connexion.this, "Identifiant deja connecter ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("login", user);
                        data.put("password", password);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(getApplicationContext(), "il faut une connection internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, Page2.class);
        //addNotification();
        intent.putExtra("login",user);

        Context context = getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor=this.mSharedPreferences.edit();
            editor.putString("login", user);
            editor.putString("password",password);
            editor.commit();

        Connexion.this.onBackPressed();
        startActivity(intent);
        finish();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        saveP=password;
        saveU=user;

    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    /*public void addNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Tracking Journey";
            String description = "Continuer de courir !";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                    importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,
                CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Tracking Journey")
                .setContentText("Continuer de courir!")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }*/
}
