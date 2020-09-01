package com.example.armoryandmachine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StartCompanyNameActivity extends AppCompatActivity {

    EditText mEdit;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_name);
        mButton = (Button)findViewById(R.id.submitCompanyName);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mEdit   = (EditText)findViewById(R.id.editText1);
            }
        });
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("master", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("companyName", String.valueOf(mEdit));
        editor.commit();
        Intent i = new Intent(this, MainActivity.class);
        finish();  //Kill the activity from which you will go to next activity
        startActivity(i);
    }
}
