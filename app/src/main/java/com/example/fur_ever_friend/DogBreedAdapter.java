package com.example.fur_ever_friend;

import android.animation.LayoutTransition;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

public class DogBreedAdapter extends RecyclerView.Adapter<DogBreedAdapter.DogViewHolder> implements Filterable {

    private List<Dog> dogs;
    private List<Dog> dogsFull;


    public DogBreedAdapter(List<Dog> dogs) {
        this.dogs = dogs;
        this.dogsFull = new ArrayList<>(dogs); // initializing dogsFull list
    }

    public void updateData(List<Dog> dogs) {
        this.dogs = dogs;
        this.dogsFull = new ArrayList<>(dogs);
        notifyDataSetChanged();
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
        notifyDataSetChanged();
    }

    public List<Dog> getDogsFull() {
        return dogsFull;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog, parent, false);
        return new DogViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog dog = dogs.get(position);
        holder.linearLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        holder.tvName.setText("Name:"+dog.getName());
        holder.tvOrigin.setText("Origin:"+dog.getOrigin());
        holder.tvDescription.setText("Description:"+dog.getDescription());
        holder.tvTemperament.setText("Temperament:"+dog.getTemperament());
        holder.tvLifespan.setText("Lifespan:"+dog.getLifespan());
        holder.tvHeight.setText("Height:"+dog.getHeight());
        holder.tvWeight.setText("Weight:"+dog.getWeight());
        System.out.println(dog.getImageUrl());
        Glide.with(holder.itemView.getContext())
                .load(dog.getImageUrl())
                .into(holder.dog_image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int v=(holder.tvDescription.getVisibility()==View.GONE)? View.VISIBLE :View.GONE;
                TransitionManager.beginDelayedTransition(holder.linearLayout,new AutoTransition());
                holder.tvDescription.setVisibility(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchText = charSequence.toString().toLowerCase();
                List<Dog> filteredList = new ArrayList<>();
                if (searchText.isEmpty()) {
                    filteredList.addAll(dogsFull);
                } else {
                    for (Dog dog : dogsFull) {
                        if (dog.getName().toLowerCase().contains(searchText)) {
                            filteredList.add(dog);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dogs.clear();
                dogs.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }


    public static class DogViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvOrigin, tvDescription, tvTemperament, tvLifespan, tvHeight, tvWeight;
        private ImageView dog_image;
        private CardView cardView;
        private LinearLayout linearLayout;
        public DogViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvOrigin = itemView.findViewById(R.id.tv_origin);
            tvDescription = itemView.findViewById(R.id.tv_description);
            cardView=itemView.findViewById(R.id.card_view);
            dog_image=itemView.findViewById(R.id.dog_img);
            linearLayout=itemView.findViewById(R.id.collaped_layout);
            tvTemperament = itemView.findViewById(R.id.tv_temperament);
            tvLifespan = itemView.findViewById(R.id.tv_lifespan);
            tvHeight = itemView.findViewById(R.id.tv_height);
            tvWeight = itemView.findViewById(R.id.tv_weight);

        }
    }
}