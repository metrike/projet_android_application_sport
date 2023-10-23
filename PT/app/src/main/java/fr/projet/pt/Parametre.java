package fr.projet.pt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.IAccountAccessor;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.FileSystemLoopException;
import java.util.HashMap;
import java.util.Map;

import fr.projet.pt.parametre.Contacter_nous;
import fr.projet.pt.parametre.Info_perso;
import fr.projet.pt.parametre.Page_parametre;
import fr.projet.pt.sport_activity.Manuellement;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Parametre#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Parametre extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences mSharedPreferences;

    private String URLdeco = "https://dwarves.iut-fbleau.fr/~metri/PT/deconnection.php";
    private String URLimage = "https://dwarves.iut-fbleau.fr/~metri/PT/recupphoto.php";
    private String URLdesinscription = "https://dwarves.iut-fbleau.fr/~metri/PT/desinscription.php";
    private String val_login = Connexion.user;
    private TextView affi_nom,affi_prenom;
    private ImageView pdp;
    private static final int RQS_OPEN_IMAGE = 1;

    public Parametre() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Parametre.
     */
    // TODO: Rename and change types and number of parameters
    public static Parametre newInstance(String param1, String param2) {
        Parametre fragment = new Parametre();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parametre, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        affi_nom = getView().findViewById(R.id.affi_nom);
        affi_prenom = getView().findViewById(R.id.affi_prenom);

        Button btn_info_perso = (Button) getView().findViewById(R.id.information_perso);
        Button btn_param = (Button) getView().findViewById(R.id.parametre);
        Button btn_contact = (Button) getView().findViewById(R.id.contact_nous);
        Button btn_deconect = (Button) getView().findViewById(R.id.deconnecte);
        Button btn_desinscription = (Button) getView().findViewById(R.id.desinscription);
        pdp = (ImageView) getView().findViewById(R.id.logo_personne);

        btn_deconect.setBackgroundColor(Color.RED);

        recupImage(new VolleyCallback() {


            @Override
            public void onSuccess(String result) {
                System.out.println(result);

                if(result.equals("failure")){
                    pdp.setImageResource(R.drawable.ic_baseline_person_24);
                }
                else{
                    //loadDocument( Uri.parse(result));
                    pdp.setImageURI(Uri.parse(result));
                }
            }
        });
        recupNom(Connexion.user);
        recupPrenom(Connexion.user);

        btn_info_perso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplication(), Info_perso.class));
            }
        });

        btn_param.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplication(), Page_parametre.class));
            }
        });

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplication(), Contacter_nous.class));
            }
        });

        btn_deconect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getActivity().getApplication(), "Deconnection", Toast.LENGTH_SHORT).show();
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplication());

                SharedPreferences.Editor editor=mSharedPreferences.edit();
                editor.putString("login", "");
                editor.putString("password","");
                editor.commit();

                deco();
                startActivity(new Intent(getActivity().getApplication(),MainActivity.class));
                getActivity().onBackPressed();

            }
        });

        btn_desinscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder bdr = new AlertDialog.Builder(getContext());
                bdr.setCancelable(false) //pour ne pas supprimer l'alert en
                        .setMessage("Confirmer la désincription")
                        // On ajoute le bouton OUI
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplication());

                                SharedPreferences.Editor editor=mSharedPreferences.edit();
                                editor.putString("login", "");
                                editor.putString("password","");
                                editor.commit();

                                desinscrire();
                                Intent i = new Intent(getActivity().getApplication(), MainActivity.class);
                                startActivity(i);
                                getActivity().onBackPressed();
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
    }

    public void deco(){
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
                data.put("login",val_login );
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplication());
        requestQueue.add(stringRequest);
    }

    public void recupImage(final VolleyCallback callback){

        int OPEN_DIRECTORY_REQUEST_CODE = 20;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLimage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                callback.onSuccess(response);
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
                data.put("login",val_login );
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplication());
        requestQueue.add(stringRequest);
    }

    public void recupNom(String login){
        String URLnom = "https://dwarves.iut-fbleau.fr/~metri/PT/recup_nom.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLnom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                affi_nom.setText(response);

                if(response.equals("failure")) {
                    Toast.makeText(getActivity().getApplication(), "pb select", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("echec")) {
                    Toast.makeText(getActivity().getApplication(), "login null", Toast.LENGTH_SHORT).show();
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

    public void recupPrenom(String login){
        String URLnom = "https://dwarves.iut-fbleau.fr/~metri/PT/recup_prenom.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLnom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                affi_prenom.setText(response);

                if(response.equals("failure")) {
                    Toast.makeText(getActivity().getApplication(), "pb select", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("echec")) {
                    Toast.makeText(getActivity().getApplication(), "login null", Toast.LENGTH_SHORT).show();
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

    public void desinscrire(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLdesinscription, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response "+response);
                if (response.equals("marche")) {
                    Toast.makeText(getActivity().getApplication(), "Sauvegardé dans la BD", Toast.LENGTH_SHORT).show();
                } else if (response.equals("echec")) {
                    Toast.makeText(getActivity().getApplication(), "Impossible de se désinscrire", Toast.LENGTH_SHORT).show();
                    System.out.println("insertion dans la BD NOT OK");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplication(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("login", Connexion.user);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplication());
        requestQueue.add(stringRequest);
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }

    public void loadDocument( Uri document) {
        Intent i = new Intent();

        i.setAction(Intent.ACTION_OPEN_DOCUMENT);

        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setData(document);
        pdp.setImageURI(document);

        // startActivity(i);
    }


    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RQS_OPEN_IMAGE){
                pdp.setImageBitmap(null);
                Uri mediaUri = data.getData();
                String mediaPath = mediaUri.getPath();
                //display the image
                try {
                    InputStream inputStream = getActivity().getBaseContext().getContentResolver().openInputStream(mediaUri);
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);
                    pdp.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

}