package fr.projet.pt.parametre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.net.Uri;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import fr.projet.pt.Connexion;
import fr.projet.pt.Page2;
import fr.projet.pt.Parametre;
import fr.projet.pt.R;

public class Page_parametre extends AppCompatActivity {

    private Button BSelectImage,ValidateImage;
    private ImageView IVPreviewImage;
    private int SELECT_PICTURE = 200;
    private String login,saveU="";
    private String URL = "https://dwarves.iut-fbleau.fr/~metri/PT/addphoto.php";
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_parametre);
        BSelectImage = findViewById(R.id.BSelectImage);
        ValidateImage = findViewById(R.id.BtnValidate);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        login = Connexion.user;

        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

    }

    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_OPEN_DOCUMENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
    public void envoiebdd(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("marche")) {
                    startActivity(new Intent(Page_parametre.this, Page2.class));
                }
                else if (response.equals("failure")) {
                    Toast.makeText(Page_parametre.this, "erreur insertion", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("failure_update")) {
                    Toast.makeText(Page_parametre.this, "erreur insertion update", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("echec")) {
                    Toast.makeText(Page_parametre.this, "pas de login", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Page_parametre.this, "erreur mais jsp pourquoi ", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("login",login );
                data.put("image", selectedImageUri.toString());
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        saveU=Connexion.user;
    }






}