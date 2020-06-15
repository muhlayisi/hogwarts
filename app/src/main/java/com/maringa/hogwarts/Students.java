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
import android.widget.LinearLayout;
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

public class Students extends AppCompatActivity {
    private RequestQueue rQueue;
    private GridLayout gridLayout;
    private CardView cardView;
    private LayoutParams layoutparams;
    private TextView txtStudent;
    private JSONObject jsonObject1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        gridLayout = findViewById(R.id.gridlayout_students);
        rQueue = Volley.newRequestQueue(this);

        jsonParse();
    }

    private void jsonParse() {
        String url = "https://www.potterapi.com/v1/characters/?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String student_id, student_name;
                try {

                    for(int i = 0; i < response.length(); i++){
                        jsonObject1 = response.getJSONObject(i);

                        if(jsonObject1.has("role")){
                            if(jsonObject1.getString("role").equalsIgnoreCase("student")){
                                student_id = jsonObject1.getString("_id");
                                student_name = jsonObject1.getString("name");

                                CreateCardView(student_id, student_name);
                            }
                        }
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

    public void CreateCardView(String student_id, String student_name){
        cardView = new CardView(this);
        layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardView.setLayoutParams(layoutparams);
        cardView.setId(View.generateViewId());
        cardView.setTag(student_id);

        cardView.setClickable(Boolean.TRUE);
        cardView.setForeground(getSelectedItemDrawable());

        cardView.setCardElevation(6);
        cardView.setRadius(12);
        cardView.setCardBackgroundColor(Color.WHITE);

        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
        layoutParams.setMargins(50, 0, 50, 20);
        cardView.requestLayout();

        txtStudent = new TextView(this);
        txtStudent.setLayoutParams(layoutparams);
        txtStudent.setText(student_name);
        txtStudent.setTextSize(16);
        txtStudent.setTextColor(Color.BLACK);
        txtStudent.setPadding(25,25,25,10);
        txtStudent.setGravity(Gravity.CENTER);

        cardView.addView(txtStudent);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedStudent(v.getTag().toString());
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

    public void getSelectedStudent(String student_id){
        Intent i;
        i = new Intent(this, StudentSelected.class);
        i.putExtra("student_id", student_id);
        startActivity(i);
    }
}