package net.ivanvega.misclienteshttpp87b;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    StringRequest stringRequest;
    public static final String TAG = "MyTag";

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imageView);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
         stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.¿
                        //textView.setText("Response is: "+ response.substring(0,500));
                        Toast.makeText(MainActivity.this,
                                "Response is: "+ response.substring(0,500), Toast.LENGTH_SHORT).show();
                        Log.i("VOLLEYRESPUESTA", "Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                Toast.makeText(MainActivity.this,
                        "Response is: "+ "That didn't work!", Toast.LENGTH_SHORT).show();
                //Log.i("VOLLEYRESPUESTA", "Response is: "+ response.substring(0,500));
            }
        });


         stringRequest.setTag(TAG);

        queue .add(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (queue!= null) {
            queue.cancelAll(TAG);
        }

    }

    public void clic(View view) {

        switch (view.getId()){
            case R.id.btnImg:
                cargarImagen();
                break;


            case R.id.btnJSon:
                obtenerJson();
                break;


        }

    }

    private void obtenerJson() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://simplifiedcoding.net/demos/view-flipper/heroes.php",
                null,
                 new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity.this,
                                response.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("MIJSON", response.toString());

                    }
                },new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();

                        }
        }
        );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        RequestQueue requestQueue = MySingleton.getInstance(getApplicationContext()).getRequestQueue();
        requestQueue.add(jsonObjectRequest);

    }

    private void cargarImagen() {

        ImageRequest imageRequest = new ImageRequest(
                "https://keepcoding.io/es/wp-content/uploads/sites/4/2018/10/android-3d-820x400.jpg",

                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        img.setImageBitmap(response);
                    }
                },
                200, 200, ImageView.ScaleType.CENTER, Bitmap.Config.ALPHA_8,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }

        );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(imageRequest);

    }
}
