package com.maringa.hogwarts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
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

public class Spells extends AppCompatActivity {
    private RequestQueue rQueue;
    private GridLayout gridLayout;
    private CardView cardView;
    private LinearLayout.LayoutParams layoutParams;
    private TextView txtSpell, txtType, txtEffect;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spells);

        gridLayout = findViewById(R.id.gridlayout_spells);
        rQueue = Volley.newRequestQueue(this);

        jsonParse();
    }

    private void jsonParse() {
        String url = "https://www.potterapi.com/v1/spells/?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String spell_id, spell_name, spell_type, spell_effect;
                try {

                    for(int i = 0; i < response.length(); i++){
                        jsonObject = response.getJSONObject(i);

                        spell_id = jsonObject.getString("_id");
                        spell_name = jsonObject.getString("spell");
                        spell_type = jsonObject.getString("type");
                        spell_effect = jsonObject.getString("effect");

                        CreateCardView(spell_id, spell_name, spell_type, spell_effect);
                    }

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

    public void CreateCardView(String spell_id, String spell_name, String spell_type, String spell_effect){
        cardView = new CardView(this);
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardView.setLayoutParams(layoutParams);
        cardView.setId(View.generateViewId());
        cardView.setTag(spell_id);

        cardView.setClickable(Boolean.TRUE);

        cardView.setCardElevation(6);
        cardView.setRadius(12);
        cardView.setCardBackgroundColor(Color.WHITE);

        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
        layoutParams.setMargins(50, 0, 50, 20);
        cardView.requestLayout();

        txtSpell = new TextView(this);
        txtSpell.setLayoutParams(layoutParams);
        txtSpell.setText(spell_name);
        txtSpell.setTextSize(20);
        txtSpell.setTextColor(Color.rgb(1, 133, 119));
        txtSpell.setPadding(25,25,25,10);
        txtSpell.setGravity(Gravity.CENTER);
        cardView.addView(txtSpell);

        txtType = new TextView(this);
        txtType.setTextSize(16);
        txtType.setTextColor(Color.BLACK);
        txtType.setPadding(25,130,25,10);
        txtType.setGravity(Gravity.LEFT);
        txtType.setText("Type: " + spell_type);
        cardView.addView(txtType);

        txtEffect = new TextView(this);
        txtEffect.setTextSize(16);
        txtEffect.setTextColor(Color.BLACK);
        txtEffect.setPadding(25,200,25,10);
        txtEffect.setGravity(Gravity.LEFT);
        txtEffect.setText("Effect: " + spell_effect);
        cardView.addView(txtEffect);

        gridLayout.addView(cardView);
    }
}