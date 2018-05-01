package com.example.jia.mp7finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String word;

    EditText wordInput;

    Button search;

    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);

        wordInput = (EditText) findViewById(R.id.wordInput);

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String definition = wordInput.getText().toString();
                if (definition.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter a valid word", Toast.LENGTH_SHORT).show();
                    return;
                }

                startAPICall(definition);
            }

        });

    }
    void startAPICall(String word) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "http://api.wordnik.com:80/v4/word.json/"+ word + "/" +
                            "definitions?limit=1&includeRelated=true&sourceDictionaries=webster&useCanonical" +
                            "=false&includeTags=false&api_key=253b52e5c6d96e812f00b0b0a4602a1240265cf2e539959ad",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            showDefinition(response);
//                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
//                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showDefinition(final JSONObject response) {
        TextView definitionView = (TextView) findViewById(R.id.definitionView);
        definitionView.setText(response.toString());
    }
}
