package com.maringa.hogwarts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
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

public class Houses extends AppCompatActivity {
    private GridLayout gridLayout;
    private CardView cardView;
    private LinearLayout.LayoutParams layoutParams;
    private LinearLayout linearLayout;
    private ImageView imageView;
    private TextView txtHouse;

    private JSONObject jsonObject;
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses);

        gridLayout = findViewById(R.id.gridlayout_houses);
        rQueue = Volley.newRequestQueue(this);

        jsonParse();
    }

    private void jsonParse() {
        String url = "https://www.potterapi.com/v1/houses?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String house_id, house_name;
                try {

                    for(int i = 0; i < response.length(); i++){

                        jsonObject = response.getJSONObject(i);
                        house_id = jsonObject.getString("_id");
                        house_name = jsonObject.getString("name");

                        CreateCardView(house_id, house_name);
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

    public void CreateCardView(String house_id, String house_name){
        cardView = new CardView(this);
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardView.setLayoutParams(layoutParams);
        cardView.setId(View.generateViewId());
        cardView.setTag(house_id);
        cardView.setClickable(Boolean.TRUE);
        cardView.setForeground(getSelectedItemDrawable());
        cardView.setCardElevation(6);
        cardView.setRadius(12);
        cardView.setCardBackgroundColor(Color.WHITE);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
        layoutParams.setMargins(50, 0, 50, 50);
        cardView.requestLayout();

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(16, 16, 16, 16);
        linearLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams linParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        linearLayout.setLayoutParams(linParams);

        imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.location_city_24);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(200, 200);
        imageView.setLayoutParams(layoutParams2);

        txtHouse = new TextView(this);
        txtHouse.setText(house_name);
        txtHouse.setTextColor(Color.rgb(111, 111, 111));
        txtHouse.setTextSize(18);
        txtHouse.setPadding(25,25,25,10);
        txtHouse.setGravity(Gravity.CENTER);

        linearLayout.addView(imageView);
        linearLayout.addView(txtHouse);

        cardView.addView(linearLayout);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedHouse(v.getTag().toString());
            }
        });

        gridLayout.addView(cardView);
    }

    public Drawable getSelectedItemDrawable() {
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray ta = this.obtainStyledAttributes(attrs);
        Drawable selectedItemDrawable = ta.getDrawable(0);
        ta.recycle();
        return selectedItemDrawable;
    }

    public void getSelectedHouse(String house_id){
        Intent i;
        i = new Intent(this, HouseSelected.class);
        i.putExtra("house_id", house_id);
        startActivity(i);
    }
}