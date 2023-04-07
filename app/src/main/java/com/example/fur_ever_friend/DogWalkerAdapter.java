package com.example.fur_ever_friend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

public class DogWalkerAdapter extends RecyclerView.Adapter<DogWalkerAdapter.ViewHolder> implements Filterable {

    private List<DogWalker> dogWalkers;
    private List<DogWalker> dogWalkersFull;
    private int radius=30;

    public DogWalkerAdapter(List<DogWalker> dogWalkers) {
        this.dogWalkers = dogWalkers;
        this.dogWalkersFull = new ArrayList<>(dogWalkers);
    }
    public void updateData(List<DogWalker> dogWalkers) {
        this.dogWalkers = dogWalkers;
        this.dogWalkersFull = new ArrayList<>(dogWalkers);
        notifyDataSetChanged();
    }

    public void setDogWalkers(List<DogWalker> dogWalkerList) {
        this.dogWalkers = dogWalkerList;
        notifyDataSetChanged();
    }

    public List<DogWalker> getDogsWalkersFull() {
        return dogWalkersFull;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.walkers_for_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DogWalker dogWalker = dogWalkers.get(position);

        holder.walker_name.setText(dogWalker.getName());
        Glide.with(holder.itemView.getContext())
                .load(dogWalker.getImageUrl())
                .transform(new RoundedCorners(radius))
                .into(holder.walker_image);
    }

    @Override
    public int getItemCount() {
        return dogWalkers.size();
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchText = charSequence.toString().toLowerCase();
                List<DogWalker> filteredList = new ArrayList<>();
                if (searchText.isEmpty()) {
                    filteredList.addAll(dogWalkersFull);
                } else {
                    for (DogWalker dogWalkers : dogWalkersFull) {
                        if (dogWalkers.getName().toLowerCase().contains(searchText)) {
                            filteredList.add(dogWalkers);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dogWalkers.clear();
                dogWalkers.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }




    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView walker_image;
        TextView walker_name;

        public ViewHolder(View itemView) {
            super(itemView);

            walker_image = itemView.findViewById(R.id.walkers_img);
            walker_name = itemView.findViewById(R.id.dog_walker_name);
        }
    }
}

