package com.example.app1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    public static final String user_pin="1234";
    public static final String manager_pin="4321";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button1=findViewById(R.id.button);

        EditText editText = findViewById(R.id.password);
        button1.setOnClickListener(v ->  {


            SharedPreferences sp= this.getSharedPreferences("user",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();


            if(user_pin.equals(editText.getText().toString().trim())){

                editor.putString("user","user");
                editor.apply();
                Toast.makeText(login.this, "Credentials is"+sp.getString("user",""), Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(login.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
            else if(manager_pin.equals(editText.getText().toString().trim())){

                editor.putString("user","manager");
                editor.apply();
                Toast.makeText(login.this, "Credentials is"+sp.getString("user",""), Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(login.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
            else {
                Toast.makeText(login.this, "Invalid Credentials"+sp.getString("user",""), Toast.LENGTH_SHORT).show();

            }
//
        });
    }
}