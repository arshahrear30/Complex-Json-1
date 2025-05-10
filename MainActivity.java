package com.example.complexjson;

import static android.view.View.GONE;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        progressBar =findViewById(R.id.progressBar);

        String url="https://nubsoft.xyz/complex.json";

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject objectResponse) {
                progressBar.setVisibility(GONE);
                Log.d("serverRes",objectResponse.toString());
                //objectResponse এটাতো একটা Object । String এ রুপান্তর করতে .toString() সাথে দিবো
                try {
                    String name=objectResponse.getString("name");
                    String age=objectResponse.getString("age");
                    textView.append("name");
                    textView.append("\n");
                    textView.append("age");

                    //এভার object এর ভিতরে থাকা  Array টা কে নাম ধরে(videos) কল করবো

                    JSONArray jsonArray = objectResponse.getJSONArray("videos");

                    for(int x=0;x<jsonArray.length();x++) {

                        //এবার videos jsonArray এর ভিতরে যেই সjsonObject গুলোকে নিব
                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                        //json এ "title" নামক string টা দেও এবং title এ রাখো ।
                        String title = jsonObject.getString("title");
                        String video_id = jsonObject.getString("video_id");
                        textView.append("\n");
                        textView.append("title");
                        textView.append("\n");
                        textView.append("video_id");
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
