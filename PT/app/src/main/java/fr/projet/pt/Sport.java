package fr.projet.pt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

import fr.projet.pt.sport_activity.Entrainement;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sport extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sport() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sport.
     */
    // TODO: Rename and change types and number of parameters
    public static Sport newInstance(String param1, String param2) {
        Sport fragment = new Sport();
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


        return inflater.inflate(R.layout.fragment_sport, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ImageView course = (ImageView) getView().findViewById(R.id.course);
        ImageView velo = (ImageView) getView().findViewById(R.id.velo);
        ImageView basket = (ImageView) getView().findViewById(R.id.basket);
        ImageView foot = (ImageView) getView().findViewById(R.id.foot);
        ImageView natation = (ImageView) getView().findViewById(R.id.natation);
        ImageView muscu = (ImageView) getView().findViewById(R.id.muscu);
        ImageView aviron = (ImageView) getView().findViewById(R.id.aviron);
        ImageView ski = (ImageView) getView().findViewById(R.id.ski);
        ImageView hand = (ImageView) getView().findViewById(R.id.hand);
        ImageView golf = (ImageView) getView().findViewById(R.id.golf);

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplication(), Entrainement.class);
                i.putExtra("Name","Course");
                startActivity(i);
            }
        });

        velo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplication(), Entrainement.class);
                i.putExtra("Name","Vélo");
                startActivity(i);
            }
        });

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplication(), Entrainement.class);
                i.putExtra("Name","Basket");
                startActivity(i);
            }
        });

        foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplication(), Entrainement.class);
                i.putExtra("Name","Football");
                startActivity(i);
            }
        });

        natation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplication(), Entrainement.class);
                i.putExtra("Name","Natation");
                startActivity(i);
            }
        });

        muscu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplication(), Entrainement.class);
                i.putExtra("Name","Musculation");
                startActivity(i);
            }
        });

        aviron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplication(), Entrainement.class);
                i.putExtra("Name","Aviron");
                startActivity(i);
            }
        });

        ski.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplication(), Entrainement.class);
                i.putExtra("Name","Ski");
                startActivity(i);
            }
        });

        hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplication(), Entrainement.class);
                i.putExtra("Name","Handball");
                startActivity(i);
            }
        });

        golf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplication(), Entrainement.class);
                i.putExtra("Name","Golf");
                startActivity(i);
            }
        });
        super.onViewCreated(view, savedInstanceState);
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
}