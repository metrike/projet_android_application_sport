package fr.projet.pt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.projet.pt.sport_activity.Manuellement;

public class Page2 extends AppCompatActivity {

    BottomNavigationView menu;
    private String URL="https://dwarves.iut-fbleau.fr/~metri/PT/activtyID.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        menu=findViewById(R.id.barre_nav);




      //  menu.setOnItemSelectedListener(R.id);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new Accueil()).commit();

        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frament=null;
                switch (item.getItemId()) {
                        case R.id.accueil:
                            frament=new Accueil();
                        break;

                    case R.id.menu:
                             frament=new Parametre();
                        break;

                    case R.id.historique:
                        frament=new Historique();
                        break;

                    case R.id.sport:
                        frament=new Sport();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,frament).commit();

                return true;
            }
        });


    }



}