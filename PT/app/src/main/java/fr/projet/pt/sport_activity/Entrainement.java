package fr.projet.pt.sport_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import fr.projet.pt.Connexion;
import fr.projet.pt.R;
import fr.projet.pt.sport_activity.localisation.RecordJourney;


public class Entrainement extends AppCompatActivity {
    private LinearLayout linear;
    private CheckBox depart;
    private Button lancer;
    private Button manuellement;
    private EditText d;
    private TextView texte;
    private Button lance;
    public static String Name;
    private ImageView image;
    private int val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        Name= intent.getStringExtra("Name");

        setContentView(R.layout.activity_entrainement);
        image=findViewById(R.id.image);
        lance=findViewById(R.id.lancer);
        d=findViewById(R.id.d);
        texte=findViewById(R.id.texte);
        texte.setText(Name);

        linear=findViewById(R.id.linear);
        depart=findViewById(R.id.depart);
        lancer=findViewById(R.id.lancer);
        manuellement=findViewById(R.id.ajout);

        if(Name.equals("Course")){
            image.setImageResource(R.drawable.runner);
            val=R.drawable.runner;
        }else if(Name.equals("Vélo")){
            image.setImageResource(R.drawable.ic_baseline_directions_bike_24);
            val=R.drawable.ic_baseline_directions_bike_24;
        }else if(Name.equals("Basket")){
            image.setImageResource(R.drawable.ic_baseline_sports_basketball_24);
            val=R.drawable.ic_baseline_sports_basketball_24;
        }else if(Name.equals("Football")){
            image.setImageResource(R.drawable.ic_baseline_sports_soccer_24);
            val=R.drawable.ic_baseline_sports_soccer_24;
        }else if(Name.equals("Natation")){
            image.setImageResource(R.drawable.swim);
            val=R.drawable.swim;
        }else if(Name.equals("Musculation")){
            image.setImageResource(R.drawable.building);
            val=R.drawable.building;
        }else if(Name.equals("Aviron")){
            image.setImageResource(R.drawable.ic_baseline_rowing_24);
            val=R.drawable.ic_baseline_rowing_24;
        }else if(Name.equals("Ski")){
            image.setImageResource(R.drawable.ic_baseline_downhill_skiing_24);
            val=R.drawable.ic_baseline_downhill_skiing_24;
        }else if(Name.equals("Handball")){
            image.setImageResource(R.drawable.ic_baseline_sports_handball_24);
            val=R.drawable.ic_baseline_sports_handball_24;
        }else if(Name.equals("Golf")){
            image.setImageResource(R.drawable.ic_baseline_golf_course_24);
            val=R.drawable.ic_baseline_golf_course_24;
        }

        Spinner spi=findViewById(R.id.spinner);
        ArrayAdapter<String> a=new ArrayAdapter<String>(Entrainement.this, android.R.layout.simple_list_item_1
                ,getResources().getStringArray(R.array.choix_parcours));
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi.setPrompt("Séléctionnez le mode d'entrainement");
        spi.setAdapter(a);
        spi.setSelection(0);

        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    linear.setVisibility(View.GONE);
                    depart.setVisibility(View.GONE);
                    lancer.setVisibility(View.VISIBLE);
                }else if(position==1){
                    linear.setVisibility(View.VISIBLE);
                    depart.setVisibility(View.VISIBLE);
                    lancer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                linear.setVisibility(View.GONE);
                depart.setVisibility(View.GONE);
                lancer.setVisibility(View.GONE);
            }
        });

        lance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Name.equals("Musculation")){
                    Intent i = new Intent(Entrainement.this, AccueilTabata.class);

                    i.putExtra("nom",Name);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i=new Intent(Entrainement.this, RecordJourney.class);
                    if(d.getText().toString().trim().length() > 0){
                        i.putExtra("distance",Double.parseDouble(d.getText().toString().trim()));
                    }else{
                        i.putExtra("distance",0.0);


                    }
                    i.putExtra("nom",Name);
                    i.putExtra("image",val);
                    startActivity(i);
                    finish();
                }

            }
        });

        manuellement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i2 = new Intent(Entrainement.this, Manuellement.class);;
                i2.putExtra("Name", Name);
                startActivity(i2);
            }
        });
    }
}