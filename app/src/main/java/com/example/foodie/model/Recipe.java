package com.example.foodie.model;

import com.google.firebase.database.Exclude;

public class Recipe {private String title ;

    private String image_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;
    @Exclude
    public boolean fav = false;

    public void setFav(){this.fav=true;}

    public boolean getFav() {return fav;}



    public String missedIngredients;



    public Recipe() {
    }

    public Recipe(String title,String image_url,String missedIngredients) {
        this.title = title;
        this.image_url = image_url;
        this.missedIngredients=missedIngredients;




    }


    public String getTitle() {
        return title;
    }


    public String getImage_url() {
        return image_url;
    }


    public void setTitle(String title) {
        this.title = title;
    }
    public String getMissedIngredients() {
        return missedIngredients;
    }
    public void setMissedIngredients(String missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


}
