package fr.projet.pt.sport_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.projet.pt.R;

public class Tabata extends AppCompatActivity {

    int pStatus = 0;
    private Handler handler = new Handler();
    private int secondeOn,secondeOff,round;
    ProgressBar mProgress;
    Vibrator vibreur;

    private boolean tmp=true;
    private TextView TextRound,OnOff;
    private String temps;
    public int compteur=1;
    public Thread t;
    TextView tv;
    public static int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata);
        Intent i = getIntent();
        secondeOn = i.getIntExtra("tempsOn", 10);
        secondeOff = i.getIntExtra("tempsOff", 3);
        round = i.getIntExtra("round", 2);
        TextRound=findViewById(R.id.round);
        OnOff=findViewById(R.id.OnOff);
        temps=i.getStringExtra("temps_total");
        vibreur = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        System.out.println("secondOff au début : " + secondeOff);
        //mProgress.setSecondaryProgress(secondeOn); // Secondary Progress
        tempsOn();
        tv = (TextView) findViewById(R.id.tv);

    }

    public void tempsOn(){

        t=new Thread(){


            @Override
            public void run() {


                while (tmp) {
                    OnOff.setText("Foooooooonnnnce mon gars");


                    Resources res = getResources();
                    Drawable drawable = res.getDrawable(R.drawable.circular);
                    mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
                    mProgress.setProgress(0);   // Main Progress
                    mProgress.setMax(secondeOn); // Maximum Progress
                    mProgress.setProgressDrawable(drawable);




                    //  for ( a = 0; a < (round * 2) - 1; a++) {
                    System.out.println("a au début " + a);

                    if (a / 2 == 0) {
                        System.out.println("dans le if " + secondeOn);
                        pStatus = secondeOn + 1;

                    } else {
                        System.out.println("dans le ellse " + secondeOff);

                        // pStatus = secondeOff+1;
                    }
                    // TODO Auto-generated method stub
                    while (pStatus > 0 && tmp==true) {
                        pStatus -= 1;




                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                mProgress.setProgress(pStatus);
                                TextRound.setText("Round : "+compteur);
                                tv.setText(pStatus + "sec");

                                System.out.println("compteur : "+compteur);

                                if(a == (round * 2) - 1){

                                    Intent i=new Intent(Tabata.this,FinTabata.class);
                                    i.putExtra("temps_on",String.valueOf(secondeOn));
                                    i.putExtra("temps_off",String.valueOf(secondeOff));
                                    i.putExtra("rounds",String.valueOf(round));
                                    i.putExtra("temps_total",String.valueOf(temps));
                                    t.interrupt();
                                    tmp=false;
                                    startActivity(i);
                                    finish();
                                }

                                if (pStatus == 0) {
                                    tempsOff();

                                }

                                if(pStatus<3){
                                    vibreur.vibrate(500 );
                                }




                            }
                        });
                        try {
                            // Sleep for 200 milliseconds.
                            // Just to display the progress slowly
                            Thread.sleep(1000); //thread will take approx 1.5 seconds to finish
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }



            }

            //}
        };

        t.start();

    }

    public void tempsOff(){
        // t.interrupt();

        if (a % 2 == 0) {
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.circular2);
            mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
            mProgress.setProgress(0);   // Main Progress
            mProgress.setMax(secondeOff); // Maximum Progress
            mProgress.setProgressDrawable(drawable);
            pStatus = secondeOff+1;
            System.out.println("second off dans if  "+secondeOff);
            System.out.println("second on dans if  "+secondeOn);
            OnOff.setText("Reposez-vous ! ");
            a++;

        }else{
            System.out.println("a dans %  2 : "+a);

            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.circular);
            mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
            mProgress.setVisibility(View.VISIBLE);
            mProgress.setProgress(0);   // Main Progress
            mProgress.setMax(secondeOn); // Maximum Progress
            mProgress.setProgressDrawable(drawable);
            pStatus = secondeOn+1;
            OnOff.setText("Courez ! ");
            a++;
            compteur++;


        }

        System.out.println("calcul : "+((round * 2) - 1));



    }
}