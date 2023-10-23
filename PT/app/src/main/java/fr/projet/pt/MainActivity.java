package fr.projet.pt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button connexion,inscription;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        connexion=findViewById(R.id.connexion);
        inscription=findViewById(R.id.inscription);

        String log =  mSharedPreferences.getString("login","");
        String pass =  mSharedPreferences.getString("password","");


        if(!log.equals("") && !pass.equals("")){
            Intent i=new Intent(MainActivity.this,Connexion.class);

            MainActivity.this.onBackPressed();
            startActivity(i);
        }

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Connexion.class);
                startActivity(i);
                finish();
            }
        });

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Inscription.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first

    }
}
