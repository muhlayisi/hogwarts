package com.maringa.hogwarts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class StudentSelected extends AppCompatActivity {
    private TextView txtStudentName, txtStudentRole, txtStudentHouse, txtStudentSchool, txtMinistryOfMagic,
            txtOrderOfPhoenix, txtDumbledoresArmy, txtDeathEater, txtBloodStatus, txtSpecies;
    private JSONObject jsonObject;
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_selected);

        txtStudentName = findViewById(R.id.txt_student_name);
        txtStudentRole = findViewById(R.id.txt_student_role);
        txtStudentHouse = findViewById(R.id.txt_student_house);
        txtStudentSchool = findViewById(R.id.txt_student_school);
        txtMinistryOfMagic = findViewById(R.id.txt_ministry_of_magic);
        txtOrderOfPhoenix = findViewById(R.id.txt_order_of_phoenix);
        txtDumbledoresArmy = findViewById(R.id.txt_dumbledores_army);
        txtDeathEater = findViewById(R.id.txt_death_eater);
        txtBloodStatus = findViewById(R.id.txt_blood_status);
        txtSpecies = findViewById(R.id.txt_species);

        if(getIntent().getExtras() != null) {

            try {
                String student_id = getIntent().getStringExtra("student_id");

                rQueue = Volley.newRequestQueue(this);

                jsonParse(student_id);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void jsonParse(String student_id) {
        String url = "https://www.potterapi.com/v1/characters/?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y&_id="+
                student_id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    jsonObject = response.getJSONObject(0);

                    txtStudentName.setText(jsonObject.getString("name"));
                    txtStudentRole.append(jsonObject.getString("role"));
                    txtStudentHouse.append(jsonObject.getString("house"));
                    txtStudentSchool.append(jsonObject.getString("school"));
                    txtMinistryOfMagic.append(jsonObject.getBoolean("ministryOfMagic")+"");
                    txtOrderOfPhoenix.append(jsonObject.getBoolean("orderOfThePhoenix")+"");
                    txtDumbledoresArmy.append(jsonObject.getBoolean("dumbledoresArmy")+"");
                    txtDeathEater.append(jsonObject.getBoolean("deathEater")+"");
                    txtBloodStatus.append(jsonObject.getString("bloodStatus"));
                    txtSpecies.append(jsonObject.getString("species"));
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
}