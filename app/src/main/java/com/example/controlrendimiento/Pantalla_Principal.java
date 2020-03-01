package com.example.controlrendimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Pantalla_Principal extends AppCompatActivity {
private Button mLogoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla__principal);
        mLogoutBtn= (Button) findViewById(R.id.Logout);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Pantalla_Principal.this, MainActivity.class));
            }
        });
    }
    public void Registro(View view){
        Intent i = new Intent(this, Registro_Produccion.class);
        startActivity(i);
    }
}
