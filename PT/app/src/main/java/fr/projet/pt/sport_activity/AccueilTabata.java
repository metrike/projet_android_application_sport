package fr.projet.pt.sport_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Locale;

import fr.projet.pt.R;

public class AccueilTabata extends AppCompatActivity {

    public ArrayList<Integer> item = new ArrayList<Integer>();
    public Button tempsOn,tempsOff;
    public int secondOn=0,secondOff=0, minuteOn=0,minuteOff=0;
    public int round=1;
    public TextView temps;
    public int tmp_second;
    private Intent i;

    public Button lancer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_tabata);
        tempsOn = findViewById(R.id.tempsOn);
        tempsOff = findViewById(R.id.tempsOff);
        temps=findViewById(R.id.temps_tabata);
        lancer=findViewById(R.id.lancer_tabata);

        for(int i=2;i<100;i++){
            item.add(i);
        }

        Spinner spi=findViewById(R.id.spinner_round);
        ArrayAdapter<Integer> a=new ArrayAdapter<Integer>(AccueilTabata.this, android.R.layout.simple_list_item_1
                ,(item));
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi.setAdapter(a);


        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                round=Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                tmp_second = ((secondOff + secondOn + (minuteOn * 60) + (minuteOff * 60)) * round)-(secondOff+minuteOff*60);

                if (tmp_second != 0) {
                    temps.setText(calculateTime(tmp_second));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }


    public void TempsOn(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int m, int s)
            {
                minuteOn = m;
                secondOn = s;
                int tmp_second = ((secondOff + secondOn + (minuteOn * 60) + (minuteOff * 60)) * round)-(secondOff+minuteOff*60);
                if (tmp_second != 0){
                    temps.setText(calculateTime(tmp_second));
                    tempsOn.setText(String.format(Locale.getDefault(), "%02d:%02d",minuteOn, secondOn));

                }
            }

        };

        int style = AlertDialog.THEME_HOLO_DARK;



        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, minuteOn, secondOn, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void TempsOff(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int m, int s) {
                minuteOff = m;
                secondOff= s;
                int tmp_second = ((secondOff + secondOn + (minuteOn * 60) + (minuteOff * 60)) * round)-(secondOff+minuteOff*60);
                if (tmp_second != 0){
                    temps.setText(calculateTime(tmp_second));
                    tempsOff.setText(String.format(Locale.getDefault(), "%02d:%02d",minuteOff, secondOff));
                }

            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;







        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, minuteOff, secondOff, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public String calculateTime(int seconds) {

        int sec = seconds % 60;
        int minutes = seconds % 3600 / 60;
        int hours = seconds % 86400 / 3600;
        int days = seconds / 86400;


        return String.format(Locale.getDefault(), "%02d:%02d",minutes, sec);
    }

    public void lance(View view){
        i=new Intent(AccueilTabata.this,Tabata.class);
        int tmp=((secondOff + secondOn + (minuteOn * 60) + (minuteOff * 60)) * round)-(secondOff+minuteOff*60);
        i.putExtra("temps",tmp);
        i.putExtra("tempsOn",minuteOn*60+secondOn);
        i.putExtra("tempsOff",minuteOff*60+secondOff);
        i.putExtra("round",round);
        i.putExtra("temps_total",String.valueOf(tmp));
        startActivity(i);
        finish();
    }
}