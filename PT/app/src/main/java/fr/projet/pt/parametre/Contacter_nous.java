package fr.projet.pt.parametre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fr.projet.pt.Connexion;
import fr.projet.pt.Inscription;
import fr.projet.pt.MainActivity;
import fr.projet.pt.Page2;
import fr.projet.pt.R;

public class Contacter_nous extends AppCompatActivity {

    private Button confirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacter_nous);

        confirmer=findViewById(R.id.confirmation);
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Contacter_nous.this, "Message envoyé", Toast.LENGTH_LONG).show();
                Intent i=new Intent(Contacter_nous.this, Page2.class);
                startActivity(i);
                finish();
            }
        });
    }
}