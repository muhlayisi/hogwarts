package com.maringa.hogwarts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private CardView housesCard, studentsCard, spellsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Defining cards
        housesCard = findViewById(R.id.houses_card);
        studentsCard = findViewById(R.id.students_card);
        spellsCard = findViewById(R.id.spells_card);

        //Add onClick listener to the cards
        housesCard.setOnClickListener(this);
        studentsCard.setOnClickListener(this);
        spellsCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        //Switching screens on click event
        switch (v.getId()){
            case R.id.houses_card : i = new Intent(this, Houses.class); startActivity(i); break;
            case R.id.students_card : i = new Intent(this, Students.class); startActivity(i); break;
            case R.id.spells_card : i = new Intent(this, Spells.class); startActivity(i); break;
            default: break;
        }
    }
}
