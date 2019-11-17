package com.example.foodie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.foodie.R;
import com.example.foodie.model.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    TextView texts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        texts=findViewById(R.id.texts);
        //final ArrayList<Integer> favs=new ArrayList<Integer>();
        final StringBuilder str=new StringBuilder();
        final DatabaseReference dbFavs = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favourites");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Recipe ss=ds.getValue(Recipe.class);
                    str.append(ss.getTitle());
                    str.append("\n");
                }
                String S=str.toString();
                texts.setText(S);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        dbFavs.addListenerForSingleValueEvent(eventListener);
        //Log.d("TAG", str.toString());
        String S=str.toString();
        texts.setText(S);
    }
}
