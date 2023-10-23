package fr.projet.pt;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

import fr.projet.pt.sport_activity.RecapActivite;
import fr.projet.pt.sport_activity.RecapTabata;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Historique#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Historique extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button stat;
    static String[] valeur;
    static String[] valeur_distance;
    static String[] valeur_note;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner spi;
    private long journeyID;
    float moyenne;

    public Historique() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Historique.
     */
    // TODO: Rename and change types and number of parameters
    public static Historique newInstance(String param1, String param2) {
        Historique fragment = new Historique();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historique, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spi = getView().findViewById(R.id.spinner);
        stat=getView().findViewById(R.id.stat);

        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //------------------------------------------------- Vitesse ------------------------------------------
                String URLdeco = "https://dwarves.iut-fbleau.fr/~metri/PT/recup_par_semaine.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLdeco, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String activite="";

                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i =0;i< obj.length();i++){
                                activite = activite +" "+ obj.getString(i);
                            }

                            String[] date_valeur=activite.split(" ");
                            String[] date=new String[date_valeur.length];
                            valeur=new String[date_valeur.length];
                            valeur[0]=date_valeur[1];

                            int compteur_date=0;
                            int compteur_valeur=1;

                            for(int i=0;i<date_valeur.length;i++){

                                if(i==2|| i%3==2){
                                    date[compteur_date]=date_valeur[i]+" / "+date_valeur[i+1];
                                    if(i+3<date_valeur.length||i==2) {

                                        System.out.println("val :" + date_valeur[i + 2]);
                                        if(date_valeur[i+2]!=null)
                                            valeur[compteur_valeur]=date_valeur[i+2];
                                        compteur_valeur++;
                                    }
                                    compteur_date++;
                                }
                            }

                            Intent i=new Intent(getContext(),Graphique.class);
                            i.putExtra("date",date);
                            startActivity(i);

                            for(int a=0;a<valeur.length;a++){
                                System.out.println("vitesse : "+valeur[a]);
                            }

                            for(int a=0;valeur_distance[a]!=null;a++){
                                System.out.println("distance : "+valeur_distance[a]);
                            }
                        } catch (JSONException e) {
                            System.out.println(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("login",Connexion.user);
                        data.put("donne","vitesse");

                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

                //------------------------------------------------- Distance ------------------------------------------
                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URLdeco, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String activite="";

                        System.out.println("resp : "+response);

                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i =0;i< obj.length();i++){
                                activite = activite +" "+ obj.getString(i);
                            }

                            System.out.println("histo : "+activite);

                            String[] date_valeur=activite.split(" ");
                            String[] date=new String[date_valeur.length];
                            valeur_distance=new String[date_valeur.length];
                            valeur_distance[0]=date_valeur[1];

                            int compteur_date=0;
                            int compteur_valeur=1;

                            for(int i=0;i<date_valeur.length;i++){
                                if(i==2|| i%3==2){
                                    date[compteur_date]=date_valeur[i]+" / "+date_valeur[i+1];
                                    if(i+3<date_valeur.length||i==2) {

                                if(date_valeur[i+2]!=null)
                                    valeur_distance[compteur_valeur]=date_valeur[i+2];
                                        compteur_valeur++;
                                    }
                                    compteur_date++;
                                }
                            }
                        } catch (JSONException e) {
                            System.out.println(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("login",Connexion.user);
                        data.put("donne","distance");

                        return data;
                    }
                };
                RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
                requestQueue2.add(stringRequest2);

                //------------------------------------------------- Note ------------------------------------------
                StringRequest stringRequest3 = new StringRequest(Request.Method.POST, URLdeco, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String activite="";
                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i =0;i< obj.length();i++){
                                activite = activite +" "+ obj.getString(i);
                            }
                            String[] date_valeur=activite.split(" ");
                            String[] date=new String[date_valeur.length];
                            valeur_note=new String[date_valeur.length];
                            valeur_note[0]=date_valeur[1];

                            int compteur_valeur=1;

                            for(int i=0;i<date_valeur.length;i++){

                                if(i==2|| i%3==2){
                                    if(i+3<date_valeur.length||i==2) {
                                        if(!date_valeur[i+2].equals("null"))
                                        valeur_note[compteur_valeur]=date_valeur[i+2];
                                        compteur_valeur++;
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            System.out.println(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("login",Connexion.user);
                        data.put("donne","note");

                        return data;
                    }
                };
                RequestQueue requestQueue3 = Volley.newRequestQueue(getContext());
                requestQueue3.add(stringRequest3);
            }
        });

        ArrayAdapter<String> a = new ArrayAdapter<String>(getActivity().getApplication(), android.R.layout.simple_list_item_1
                , getResources().getStringArray(R.array.historique));
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi.setAdapter(a);
    }

    public void onResume(){
        super.onResume();

        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);

                if(item.equals("Du plus ancien au plus rÃ©cent")){
                    recupinformation(new VolleyCallback(){
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onSuccess(String result){
                            String[] tabentier = result.split(",");
                            TableLayout table = getView().findViewById(R.id.tableau);
                            TableRow row;
                            TextView tv1;
                            TextView tv2;
                            Button bouton;
                            String datetmp = "";
                            table.removeAllViews();
                            for (int a = 0;a<tabentier.length;a++) {
                                row = new TableRow(getActivity().getApplication());
                                if(!datetmp.equals(tabentier[a]) || a ==0) {
                                    tv1 = new TextView(getActivity().getApplication());
                                    tv2 = new TextView(getActivity().getApplication());
                                    bouton = new Button(getActivity().getApplication());
                                    if(!tabentier[a].contains("-")){
                                        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                                        rowParams.setMargins(0,0,0,-250);
                                        String txt = tabentier[a];
                                        tv1.setText(txt);
                                        tv1.setGravity(Gravity.CENTER);
                                        tv1.setTextSize(18);
                                        tv1.setTextColor(R.color.gray);
                                        tv1.setLayoutParams(rowParams);
                                        row.addView(tv1);

                                        TableRow.LayoutParams rowParams2 = new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                                        rowParams2.setMargins(-100,0,-25,-250);
                                        txt = tabentier[a + 1];
                                        tv2.setText(txt);
                                        tv2.setGravity(Gravity.CENTER);
                                        tv2.setTextSize(18);
                                        tv2.setTextColor(R.color.gray);
                                        tv2.setLayoutParams(rowParams2);
                                        row.addView(tv2);

                                        bouton.setText("voir");
                                        bouton.setGravity(Gravity.CENTER);

                                        int finalA = a;
                                        bouton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(tabentier[finalA].equals("Musculation")) {
                                                    Intent i = new Intent(getContext(), RecapTabata.class);
                                                    Bundle b = new Bundle();
                                                    b.putLong("journeyID", journeyID);
                                                    i.putExtra("date",tabentier[finalA-1]);
                                                    i.putExtra("sport",tabentier[finalA]);
                                                    i.putExtra("temps",tabentier[finalA+1]);
                                                    i.putExtras(b);
                                                    startActivity(i);

                                                }else{
                                                    Intent i = new Intent(getContext(), RecapActivite.class);
                                                    Bundle b = new Bundle();
                                                    System.out.println("SPORT : "+tabentier[finalA]);
                                                    b.putLong("journeyID", journeyID);
                                                    i.putExtra("date",tabentier[finalA-1]);
                                                    i.putExtra("sport",tabentier[finalA]);
                                                    i.putExtra("temps",tabentier[finalA+1]);
                                                    i.putExtras(b);
                                                    startActivity(i);
                                                }
                                            }
                                        });
                                        journeyID++;
                                        row.addView(bouton);
                                        table.addView(row);
                                        a++;
                                    }
                                    else{
                                        tv1.setText("\t"+tabentier[a]);
                                        datetmp = tabentier[a];
                                        tv1.setTextSize(20);
                                        tv1.setTextColor(R.color.gray);
                                        row.addView(tv1);
                                        table.addView(row);
                                    }
                                }
                            }
                        }
                    },"ASC");
                }
                else{
                    recupinformation(new VolleyCallback(){
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onSuccess(String result){
                            String[] tabentier = result.split(",");
                            TableLayout table = getView().findViewById(R.id.tableau);
                            TableRow row;
                            TextView tv1;
                            TextView tv2;
                            Button bouton;
                            String datetmp = "";
                            journeyID=0;
                            table.removeAllViews();

                            if(!tabentier[0].equals("")){
                                for (int a = 0; a < tabentier.length; a++) {
                                    row = new TableRow(getActivity().getApplication());
                                    if (!datetmp.equals(tabentier[a]) || a == 0) {
                                        tv1 = new TextView(getActivity().getApplication());
                                        tv2 = new TextView(getActivity().getApplication());
                                        bouton = new Button(getActivity().getApplication());
                                        if (!tabentier[a].contains("-")) {
                                            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                                            rowParams.setMargins(0,0,0,-250);
                                            String txt = tabentier[a];
                                            tv1.setText(txt);
                                            tv1.setGravity(Gravity.CENTER);
                                            tv1.setTextSize(18);
                                            tv1.setTextColor(R.color.gray);
                                            tv1.setLayoutParams(rowParams);
                                            row.addView(tv1);

                                            TableRow.LayoutParams rowParams2 = new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                                            rowParams2.setMargins(-100,0,-25,-250);
                                            txt = tabentier[a + 1];
                                            tv2.setText(txt);
                                            tv2.setGravity(Gravity.CENTER);
                                            tv2.setTextSize(18);
                                            tv2.setTextColor(R.color.gray);
                                            tv2.setLayoutParams(rowParams2);
                                            row.addView(tv2);

                                            bouton.setText("voir");
                                            bouton.setGravity(Gravity.CENTER);

                                            int finalA = a;
                                            bouton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(tabentier[finalA].equals("Musculation")) {
                                                        Intent i = new Intent(getContext(), RecapTabata.class);
                                                        Bundle b = new Bundle();
                                                        b.putLong("journeyID", journeyID);
                                                        i.putExtra("date",tabentier[finalA-1]);
                                                        i.putExtra("sport",tabentier[finalA]);
                                                        i.putExtra("temps",tabentier[finalA+1]);
                                                        i.putExtras(b);
                                                        startActivity(i);

                                                    }else{
                                                        Intent i = new Intent(getContext(), RecapActivite.class);
                                                        Bundle b = new Bundle();
                                                        System.out.println("SPORT : "+tabentier[finalA]);
                                                        b.putLong("journeyID", journeyID);
                                                        i.putExtra("date",tabentier[finalA-1]);
                                                        i.putExtra("sport",tabentier[finalA]);
                                                        i.putExtra("temps",tabentier[finalA+1]);
                                                        i.putExtras(b);
                                                        startActivity(i);
                                                    }
                                                }
                                            });
                                            journeyID++;
                                            row.addView(bouton);
                                            table.addView(row);
                                            a++;
                                        } else {
                                            tv1.setText("\t"+tabentier[a]);
                                            datetmp = tabentier[a];
                                            tv1.setTextSize(20);
                                            tv1.setTextColor(R.color.gray);
                                            row.addView(tv1);
                                            table.addView(row);
                                        }

                                        TextView texteactivite = tv1;
                                        row.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                System.out.println(texteactivite.getText());
                                            }
                                        });
                                    }
                                }
                            }
                            else{
                                table = getView().findViewById(R.id.tableau);
                                row = new TableRow(getActivity().getApplication());
                                tv1 = new TextView(getActivity().getApplication());
                                String txt = "Pas d'activitÃ©";
                                tv1.setText(txt);
                                tv1.setGravity(Gravity.CENTER);

                                tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                                TextView texteactivite = tv1;
                                row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        System.out.println(texteactivite.getText());
                                    }
                                });

                                row.addView(tv1);
                                table.addView(row);
                            }
                        }
                    },"DESC");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onStop() {
        super.onStop();
        deconect(Connexion.user);

    }

    public void recupinformation(final VolleyCallback callback,String ordre){
        String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/recuptouthistorique.php";
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
                data.put("ordre", ordre);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
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

}