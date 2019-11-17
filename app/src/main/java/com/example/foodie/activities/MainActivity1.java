package com.example.foodie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.example.foodie.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import com.example.foodie.adapters.RecyclerViewAdapter;
import com.example.foodie.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity1 extends AppCompatActivity {

    private final String JSON_Base_URL = "https://api.spoonacular.com/recipes/findByIngredients?apiKey=85a8432d7500489db29c6782e7821a5d&ingredients=";
    private String JSON_URL;

    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Recipe> lstRecipe;
    private RecyclerView recyclerView;
    Button btnGetRecipes;
    EditText ingredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        lstRecipe = new ArrayList<>();
        ingredients = findViewById(R.id.recipe_ingredients);
        this.btnGetRecipes = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerviewid);
        requestQueue = Volley.newRequestQueue(MainActivity1.this);



    }

    private void setuprecyclerview(List<Recipe> lstRecipe) {


        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, lstRecipe);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myadapter);

    }
    private void clearList()
    {
        lstRecipe.clear();
    }


    private void getRecipeList(String ingredients) {
        JSON_URL = JSON_Base_URL + ingredients;
        final ArrayList<Integer> favs=new ArrayList<Integer>();
        DatabaseReference dbFavs = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favourites");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    //String key = ds.getKey();
                    favs.add(Integer.parseInt(ds.getKey()));
                    //Log.d("TAG", key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        dbFavs.addListenerForSingleValueEvent(eventListener);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, JSON_URL,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        StringBuilder str = new StringBuilder();

                        if (response.length() > 0) {
                            clearList();
                            setuprecyclerview(null);

                            for (int i = 0; i < response.length(); i++) {


                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    Recipe recipe = new Recipe();
                                    recipe.setTitle(jsonObject.getString("title"));
                                    recipe.setImage_url(jsonObject.getString("image"));
                                    int id=jsonObject.getInt("id");
                                    recipe.setId(id);
                                    if(favs.contains(id)){
                                        recipe.setFav();
                                    }

                                    JSONArray array = jsonObject.getJSONArray("missedIngredients");
                                    for (int j = 0; j < array.length(); j++) {

                                        JSONObject json = array.getJSONObject(j);
                                        str.append(json.getString("name")+"\n");

                                    }
                                    recipe.setMissedIngredients(str.toString());
                                    System.out.println(str.toString());
                                    lstRecipe.add(recipe);
                                    setuprecyclerview(lstRecipe);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Volley", error.toString());
            }
        });


        requestQueue.add(request);
    }
    public void getRecipes(View v)
    {

        getRecipeList(ingredients.getText().toString());


    }


}









