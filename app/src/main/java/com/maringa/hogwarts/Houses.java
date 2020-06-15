package com.maringa.hogwarts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Houses extends AppCompatActivity implements View.OnClickListener{
    private CardView house1Card, house2Card, house3Card, house4Card;
    private TextView txtHouses1, txtHouses2, txtHouses3, txtHouses4;
    private JSONObject jsonObject1, jsonObject2, jsonObject3, jsonObject4;
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses);

        //Defining cards
        house1Card = findViewById(R.id.house1_card);
        house2Card = findViewById(R.id.house2_card);
        house3Card = findViewById(R.id.house3_card);
        house4Card = findViewById(R.id.house4_card);

        //Add onClick listener to the cards
        house1Card.setOnClickListener(this);
        house2Card.setOnClickListener(this);
        house3Card.setOnClickListener(this);
        house4Card.setOnClickListener(this);

        //Defining Text Views
        txtHouses1 = findViewById(R.id.text_view_house1);
        txtHouses2 = findViewById(R.id.text_view_house2);
        txtHouses3 = findViewById(R.id.text_view_house3);
        txtHouses4 = findViewById(R.id.text_view_house4);

        rQueue = Volley.newRequestQueue(this);

        jsonParse();
    }

    private void jsonParse() {
        String url = "https://www.potterapi.com/v1/houses?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    jsonObject1 = response.getJSONObject(0);
                    jsonObject2 = response.getJSONObject(1);
                    jsonObject3 = response.getJSONObject(2);
                    jsonObject4 = response.getJSONObject(3);

                    String house1Name = jsonObject1.getString("name");
                    String house2Name = jsonObject2.getString("name");
                    String house3Name = jsonObject3.getString("name");
                    String house4Name = jsonObject4.getString("name");

                    txtHouses1.setText(house1Name);
                    txtHouses2.setText(house2Name);
                    txtHouses3.setText(house3Name);
                    txtHouses4.setText(house4Name);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        rQueue.add(request);
    }

    @Override
    public void onClick(View v) {

    }
}