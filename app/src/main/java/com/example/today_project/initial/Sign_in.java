package com.example.today_project.initial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.today_project.MainActivity;
import com.example.today_project.NavigationActivity;
import com.example.today_project.R;

public class Sign_in extends AppCompatActivity {
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        button=(Button) findViewById(R.id.enter_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Sign_in.this, MainActivity.class);
                startActivity(intent);

            }
        });

        textView=(TextView)findViewById(R.id.already_has_account);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_in.this, Log_in.class);
                startActivity(intent);
            }

        });

    }


}