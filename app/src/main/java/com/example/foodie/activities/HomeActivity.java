package com.example.foodie.activities;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodie.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnLogout;
    Button mapButton;
    Button recipeButton;
    Button favs;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLogout = findViewById(R.id.logout);
        btnLogout.setOnClickListener(this);

        mapButton = findViewById(R.id.map);
        mapButton.setOnClickListener(this);

        recipeButton = findViewById(R.id.recipe);
        recipeButton.setOnClickListener(this);

        favs=findViewById(R.id.favs);
        favs.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
                break;
            case R.id.map:
                openActivityPermissions();
                //openActivityMap();
                break;
            case R.id.recipe:
                openActivityRecipe();
                break;
            case R.id.favs:
                Intent intToFavs=new Intent(HomeActivity.this, FavouriteActivity.class);
                startActivity(intToFavs);
            default:
                break;


        }
    }


    public void openActivityMap() {
        Intent intent=new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void openActivityRecipe() {
        Intent intent=new Intent(this,MainActivity1.class);
        startActivity(intent);
    }

    public void openActivityPermissions() {
        Intent intent=new Intent(this, PermissionsActivity.class);
        startActivity(intent);
    }

}
