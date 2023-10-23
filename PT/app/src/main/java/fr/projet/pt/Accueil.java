package fr.projet.pt;

//import static fr.projet.pt.sport_activity.localisation.JourneyProviderContract.J_ID;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.projet.pt.sport_activity.RecapTabata;
import fr.projet.pt.sport_activity.localisation.DB;
//import fr.projet.pt.sport_activity.localisation.JourneyProviderContract;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Accueil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Accueil extends Fragment {

    DB dbh;
    SQLiteDatabase db;
    String date_pass,date;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView name;
    public static long ID;

    private String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/accueil_donnee.php";
    private String URL2="https://dwarves.iut-fbleau.fr/~metri/PT/activtyID.php";


    public Accueil() {
    }


    // TODO: Rename and change types and number of parameters
    public static Accueil newInstance(String param1, String param2) {
        Accueil fragment = new Accueil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbh = new DB(this.getContext());
        db = dbh.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accueil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getJourneyID();




        name = getView().findViewById(R.id.nom);
        name.setText("Bienvenue "+Connexion.user);

        RecupActivity(new VolleyCallback(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSuccess(String result){
                String[] tabentier = result.split(",");
                TableLayout table = getView().findViewById(R.id.grille);
                TableRow row;
                TextView tv1;
                TextView tv2;
                TextView tv3;
                String datetmp = "";

                if(!tabentier[0].equals("")){
                    for (int a = 0; a < tabentier.length; a++) {
                        row = new TableRow(getActivity().getApplication());
                        if (!datetmp.equals(tabentier[a]) || a == 0) {
                            tv1 = new TextView(getActivity().getApplication());
                            tv2 = new TextView(getActivity().getApplication());
                            tv3 = new TextView(getActivity().getApplication());

                            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                            rowParams.setMargins(0,0,0,-300);
                            String txt = tabentier[a];
                            tv1.setText(txt);
                            tv1.setGravity(Gravity.CENTER);
                            tv1.setTextSize(18);
                            tv1.setTextColor(R.color.gray);
                            tv1.setLayoutParams(rowParams);
                            row.addView(tv1);

                            TableRow.LayoutParams rowParams2 = new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                            //rowParams2.setMargins(1,1,1,-300);
                            txt = tabentier[a + 1];
                            tv2.setText(txt);
                            tv2.setGravity(Gravity.CENTER);
                            tv2.setTextSize(18);
                            tv2.setTextColor(R.color.gray);
                            //tv2.setLayoutParams(rowParams2);
                            row.addView(tv2);

                            TableRow.LayoutParams rowParams3 = new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                            rowParams3.setMargins(0,0,0,-300);
                            txt = tabentier[a + 2];
                            tv3.setText(txt);
                            tv3.setGravity(Gravity.CENTER);
                            tv3.setTextSize(18);
                            tv3.setTextColor(R.color.gray);
                            tv3.setLayoutParams(rowParams3);
                            row.addView(tv3);

                            a=a+2;
                            TextView texteactivite = tv3;
                            row.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println(texteactivite.getText());
                                }
                            });
                            table.addView(row);
                        }
                    }
                }
                else{
                    table = getView().findViewById(R.id.grille);
                    row = new TableRow(getActivity().getApplication());
                    tv1 = new TextView(getActivity().getApplication());
                    String txt = "Pas d'activité";
                    tv1.setText(txt);
                    tv1.setGravity(Gravity.CENTER);

                    tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    row.addView(tv1);
                    table.addView(row);
                }
            }
        });

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH,-7);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date_pass = df.format(c.getTime());

        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        date = df2.format(c2.getTime());

    }

    public void onStop() {
        super.onStop();
        deconect(Connexion.user);
    }


    public void deconect(String login){
        String URLdeco = "https://dwarves.iut-fbleau.fr/~metri/PT/deconnection.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLdeco, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(getActivity().getApplication(), "marche", Toast.LENGTH_SHORT).show();
                }
                else if(response.equals("test")) {
                    Toast.makeText(getActivity().getApplication(), "connect mais pas delete", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("connect")) {
                    Toast.makeText(getActivity().getApplication(), "erreur delete", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplication(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("login",login);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplication());
        requestQueue.add(stringRequest);
    }

    public void RecupActivity(final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                String activite="";

                try {
                    JSONArray obj = new JSONArray(response);
                    for (int i =0;i< obj.length();i++){
                        activite = activite + obj.getString(i);
                    }
                    activite = deleteChar(activite);

                    callback.onSuccess(activite);
                } catch (JSONException e) {
                    System.out.println(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString().trim());
                Toast.makeText(getActivity().getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("login", Connexion.user);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public String deleteChar(String activite){
        activite = activite.replaceAll("[ +^\" ]","");
        activite = activite.replaceAll("\\\\", "");
        activite = activite.replaceAll("\\[","");
        activite = activite.replaceAll("]",",");

        return activite;
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }

    public void getJourneyID(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ID=Integer.parseInt(response)+1;
                System.out.println("id dans acc "+ID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
