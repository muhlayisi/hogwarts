package com.maringa.hogwarts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout.LayoutParams;
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

public class HouseSelected extends AppCompatActivity {
    private TextView txtHouseName, txtHouseFounder, txtHouseHead, txtHouseGhost, txtHouseMascot,
            txtHouseSchool, txtHouseValues, txtHouseColors, txtMember;

    private JSONObject jsonObject;
    private RequestQueue rQueue;
    private GridLayout gridLayout;
    private CardView cardview;
    private LayoutParams layoutparams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_selected);

        gridLayout = findViewById(R.id.gridlayout_members);
        txtHouseName = findViewById(R.id.txt_house_name);
        txtHouseFounder = findViewById(R.id.txt_house_founder);
        txtHouseHead = findViewById(R.id.txt_house_head);
        txtHouseGhost = findViewById(R.id.txt_house_ghost);
        txtHouseMascot = findViewById(R.id.txt_house_mascot);
        txtHouseSchool = findViewById(R.id.txt_house_school);
        txtHouseValues = findViewById(R.id.txt_house_values);
        txtHouseColors = findViewById(R.id.txt_house_colors);

        if(getIntent().getExtras() != null) {

            try {
                String house_id = getIntent().getStringExtra("house_id");

                rQueue = Volley.newRequestQueue(this);

                jsonParse(house_id);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void jsonParse(String house_id) {
        String url = "https://www.potterapi.com/v1/houses/"+house_id+"/?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String member_name;
                try {
                    jsonObject = response.getJSONObject(0);

                    txtHouseName.setText(jsonObject.getString("name"));
                    txtHouseFounder.append(jsonObject.getString("founder"));
                    txtHouseHead.append(jsonObject.getString("headOfHouse"));
                    txtHouseGhost.append(jsonObject.getString("houseGhost"));
                    txtHouseMascot.append(jsonObject.getString("mascot"));

                    if(jsonObject.has("school")){
                        txtHouseSchool.append(jsonObject.getString("school"));
                    }else{
                        txtHouseSchool.append("Unknown");
                    }

                    String values = jsonObject.getString("values")
                            .replace("[", "")
                            .replace("]", "")
                            .replace("\"", "");
                    txtHouseValues.append(values);
                    String colours = jsonObject.getString("colors")
                            .replace("[", "")
                            .replace("]", "")
                            .replace("\"", "");
                    txtHouseColors.append(colours);

                    JSONArray jsonMembersArray = jsonObject.getJSONArray("members");

                    gridLayout.setRowCount(jsonMembersArray.length());

                    for(int i = 0; i < jsonMembersArray.length(); i++){
                        member_name = jsonMembersArray.getJSONObject(i).getString("name");

                        CreateCardViewProgrammatically(member_name);
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

    public void CreateCardViewProgrammatically(String member_name){
        cardview = new CardView(this);
        layoutparams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        cardview.setLayoutParams(layoutparams);
        cardview.setCardElevation(6);
        cardview.setRadius(12);
        cardview.setCardBackgroundColor(Color.WHITE);

        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) cardview.getLayoutParams();
        layoutParams.setMargins(50, 0, 50, 20);
        cardview.requestLayout();

        txtMember = new TextView(this);
        txtMember.setLayoutParams(layoutparams);
        txtMember.setText(member_name);
        txtMember.setTextSize(16);
        txtMember.setTextColor(Color.BLACK);
        txtMember.setPadding(25,25,25,10);
        txtMember.setGravity(Gravity.CENTER);
        cardview.addView(txtMember);
        gridLayout.addView(cardview);
    }
}