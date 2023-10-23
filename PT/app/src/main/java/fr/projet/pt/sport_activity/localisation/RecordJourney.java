package fr.projet.pt.sport_activity.localisation;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.projet.pt.Connexion;
import fr.projet.pt.MainActivity;
import fr.projet.pt.Page2;
import fr.projet.pt.R;
import fr.projet.pt.sport_activity.FinActivite;

public class RecordJourney extends AppCompatActivity {

    public static LocationService.LocationServiceBinder locationService;

    private TextView distanceText;

    private TextView avgSpeedText;
    private TextView durationText;
    public static String nom;
    private double distance1;

    private Button playButton;
    private Button stopButton;
    private static final int PERMISSION_GPS_CODE = 1;

    // will poll the location service for distance and duration
    private Handler postBack = new Handler();

    private TextView name;
    private ImageView image;
    private int val;

    private ServiceConnection lsc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            locationService = (LocationService.LocationServiceBinder) iBinder;

            // if currently tracking then enable stopButton and disable startButton
            initButtons();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    locationService.playJourney();
                    while (locationService != null) {
                        // get the distance and duration from the surface

                        float d = (float) locationService.getDuration();
                        long duration = (long) d;  // in seconds
                        float distance = locationService.getDistance();

                        long hours = duration / 3600;
                        long minutes = (duration % 3600) / 60;
                        long seconds = duration % 60;

                        float avgSpeed = 0;
                        if(d != 0) {
                            avgSpeed = distance / (d / 3600);
                        }

                        final String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                        final String dist = String.format("%.2f KM", distance);
                        final String avgs = String.format("%.2f KM/H", avgSpeed);

                        postBack.post(new Runnable() {
                            @Override
                            public void run() {
                                // post back changes to UI thread
                                durationText.setText(time);
                                avgSpeedText.setText(avgs);
                                distanceText.setText(dist);
                                String d_test=distanceText.getText().toString().substring(0,distanceText.getText().toString().length()-3);

                                if(distance1!=0){
                                    double d_=Double.parseDouble(d_test.replace(',', '.'));
                                    if(d_==distance1) {
                                        addNotification();
                                    }
                                }


                            }
                        });
                        try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            locationService = null;
        }
    };

    // whenever activity is reloaded while still tracking a journey (if back button is clicked for example)
    private void initButtons() {
        // no permissions means no buttons
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //stopButton.setEnabled(false);
            playButton.setEnabled(false);
            return;
        }

        // if currently tracking then enable stopButton and disable startButton
        if(locationService != null && locationService.currentlyTracking()) {
            stopButton.setEnabled(true);
            playButton.setEnabled(false);
        } else {
            //stopButton.setEnabled(false);
            playButton.setEnabled(true);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_journey);
        Intent i=getIntent();
        nom=i.getStringExtra("nom");
        val=i.getIntExtra("image",0);
        distance1=i.getDoubleExtra("distance",0);



        name=findViewById(R.id.name);
        name.setText(nom);
        image=findViewById(R.id.image);
        image.setImageResource(val);
        distanceText = findViewById(R.id.distanceText);
        durationText = findViewById(R.id.durationText);
        avgSpeedText = findViewById(R.id.avgSpeedText);

        playButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        // connect to service to see if currently tracking before enabling a button
        stopButton.setEnabled(true);
        playButton.setEnabled(false);


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gif.play();

                // start the timer and tracking GPS locations
                locationService.playJourney();
                playButton.setEnabled(false);
                stopButton.setEnabled(true);
            }
        });




        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder bdr = new AlertDialog.Builder(RecordJourney.this);
                bdr.setCancelable(false) //pour ne pas supprimer l'alert en

                        .setMessage("Confirmer l'arrêt de l'activité")
                        // On ajoute le bouton OUI
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float distance = locationService.getDistance();
                                locationService.saveJourney();
                                Intent i = new Intent(RecordJourney.this, FinActivite.class);
                                i.putExtra("distance", distanceText.getText().toString());
                                i.putExtra("sport",nom);


                                i.putExtra("vitesse", avgSpeedText.getText().toString());
                                i.putExtra("temps", durationText.getText().toString());
                                startActivity(i);
                                finish();
                            }
                        })
                        // On ajoute le bouton NON
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel(); // si NON on supprime l'alerte
                            }
                        });
                AlertDialog myAlert = bdr.create(); // on crée l'alert
                myAlert.show(); // on l'affiche
            }
        });


        try {
            MyReceiver receiver = new MyReceiver();
            registerReceiver(receiver, new IntentFilter(
                    Intent.ACTION_BATTERY_LOW));
        } catch(IllegalArgumentException  e) {
        }

        handlePermissions();
        startService(new Intent(this, LocationService.class));
        bindService(new Intent(this, LocationService.class), lsc, Context.BIND_AUTO_CREATE);
      //  locationService.playJourney();
    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            MyReceiver receiver = new MyReceiver();
            unregisterReceiver(receiver);
        } catch(IllegalArgumentException  e) {
        }

        if(lsc != null) {
            unbindService(lsc);
            lsc = null;
        }
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(RecordJourney.this, "Veuillez terminer l'activité !", Toast.LENGTH_SHORT).show();
    }

    // PERMISSION THINGS

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(reqCode, permissions, results);
        switch (reqCode) {
            case PERMISSION_GPS_CODE:
                if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    initButtons();
                    if (locationService != null) {
                        locationService.notifyGPSEnabled();
                    }
                } else {
                    // permission denied, disable GPS tracking buttons
                   // stopButton.setEnabled(false);
                    playButton.setEnabled(false);
                }
                return;
        }
    }



    private void handlePermissions() {
        // if don't have GPS permissions then request this permission from the user.
        // if not granted the permission disable the start button
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // the user has already declined request to allow GPS
                // give them a pop up explaining why its needed and re-ask

            } else {
                // request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GPS_CODE);
            }

        }
    }

    private void addNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Tracking Journey";
            String description = "Continuer de courir !";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("100", name,
                    importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,
                "0")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Tracking Journey")
                .setContentText("Continuer de courir!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(100, mBuilder.build());
    }
}
