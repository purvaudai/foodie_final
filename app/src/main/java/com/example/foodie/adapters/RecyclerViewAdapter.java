package com.example.foodie.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodie.R ;
import com.example.foodie.model.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Recipe> mData ;
    RequestOptions option;


    public RecyclerViewAdapter(Context mContext, List<Recipe> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.recipe_row_item,parent,false) ;
        return new MyViewHolder(view) ;


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.tv_name.setText(mData.get(position).getTitle());
        holder.tv_missing.setText(mData.get(position).getMissedIngredients());


        // Load Image from the internet and set it into Imageview using Glide

        if(mData.get(position).getFav()){
            holder.check.setChecked(true);
        }

        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(option).into(holder.img_thumbnail);




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        TextView tv_name ;
        ImageView img_thumbnail;
        TextView tv_missing;
        LinearLayout view_container;
        CheckBox check;







        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            tv_name = itemView.findViewById(R.id.recipe_name);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);
            tv_missing= itemView.findViewById(R.id.missing_ingredient);
            tv_missing.setMovementMethod(new ScrollingMovementMethod());
            check=itemView.findViewById(R.id.checkbox);
            check.setOnCheckedChangeListener(this);




        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean b) {
            int position = getAdapterPosition();
            Recipe w = mData.get(position);


            DatabaseReference dbFavs = FirebaseDatabase.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("favourites");

            if (b) {
                dbFavs.child(String.valueOf(w.id)).setValue(w);
                //dbFavs.child(w.name).setValue(w);
            } else {
                dbFavs.child(String.valueOf(w.id)).setValue(null);
            }

        }
    }

}
