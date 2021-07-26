package com.example.semiparams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View view) {
        if(view.getId()==R.id.Semi_but)
        {
            Intent intent = new Intent(this, Semi_act.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.PN_but)
        {
            Intent intent = new Intent(this, PN_act.class);
            startActivity(intent);
        }
    }
}