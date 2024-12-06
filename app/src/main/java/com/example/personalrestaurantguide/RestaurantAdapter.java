package com.example.personalrestaurantguide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private final List<Restaurant> originalList;
    private List<Restaurant> restaurantList;
    private final OnRestaurantActionListener actionListener;

    public interface OnRestaurantActionListener {
        void onEditClicked(Restaurant restaurant);
        void onDeleteClicked(Restaurant restaurant);
    }

    public RestaurantAdapter(List<Restaurant> restaurantList, OnRestaurantActionListener actionListener) {
        this.originalList = new ArrayList<>(restaurantList);
        this.restaurantList = restaurantList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_card, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);

        holder.nameTextView.setText(restaurant.getName());
        holder.addressTextView.setText(restaurant.getAddress());
        holder.phoneTextView.setText(restaurant.getPhone());
        holder.descriptionTextView.setText(restaurant.getDescription());
        holder.ratingTextView.setText("Rating: " + restaurant.getRating() + "/5");

        holder.facebookButton.setOnClickListener(v -> shareOnFacebook(holder.itemView.getContext(), restaurant));
        holder.twitterButton.setOnClickListener(v -> shareOnTwitter(holder.itemView.getContext(), restaurant));
        holder.pinterestButton.setOnClickListener(v -> shareOnPinterest(holder.itemView.getContext(), restaurant));

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), AddRestaurantActivity.class);
            intent.putExtra("id", restaurant.getId());
            intent.putExtra("name", restaurant.getName());
            intent.putExtra("address", restaurant.getAddress());
            intent.putExtra("phone", restaurant.getPhone());
            intent.putExtra("description", restaurant.getDescription());
            intent.putExtra("rating", restaurant.getRating());
            holder.itemView.getContext().startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onDeleteClicked(restaurant);
            }
        });

        // Harita Aç Butonu İşlemi
        holder.mapButton.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), MapActivity.class);
            intent.putExtra("restaurant_name", restaurant.getName());
            intent.putExtra("restaurant_address", restaurant.getAddress());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    public void updateList(List<Restaurant> newList) {
        restaurantList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void filter(String query) {
        if (query.isEmpty()) {
            restaurantList = new ArrayList<>(originalList); // Reset to original list
        } else {
            List<Restaurant> filteredList = new ArrayList<>();
            for (Restaurant restaurant : originalList) {
                if (restaurant.getName().toLowerCase().contains(query.toLowerCase()) ||
                        restaurant.getAddress().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(restaurant);
                }
            }
            restaurantList = filteredList;
        }
        notifyDataSetChanged();
    }

    private void shareOnFacebook(Context context, Restaurant restaurant) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareMessage = "Check out this amazing restaurant: " +
                restaurant.getName() + "\nAddress: " + restaurant.getAddress();
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        shareIntent.setPackage("com.facebook.katana");
        try {
            context.startActivity(shareIntent);
        } catch (Exception e) {
            Toast.makeText(context, "Facebook app is not installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareOnTwitter(Context context, Restaurant restaurant) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareMessage = "Check out this amazing restaurant: " +
                restaurant.getName() + "\nAddress: " + restaurant.getAddress();
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        shareIntent.setPackage("com.twitter.android");
        try {
            context.startActivity(shareIntent);
        } catch (Exception e) {
            Toast.makeText(context, "Twitter app is not installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareOnPinterest(Context context, Restaurant restaurant) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareMessage = "Check out this amazing restaurant: " +
                restaurant.getName() + "\nAddress: " + restaurant.getAddress();
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        shareIntent.setPackage("com.pinterest");
        try {
            context.startActivity(shareIntent);
        } catch (Exception e) {
            Toast.makeText(context, "Pinterest app is not installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, addressTextView, phoneTextView, descriptionTextView, ratingTextView;
        View editButton, deleteButton, facebookButton, twitterButton, pinterestButton;
        ImageButton mapButton; // Harita butonu

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            addressTextView = itemView.findViewById(R.id.tv_address);
            phoneTextView = itemView.findViewById(R.id.tv_phone);
            descriptionTextView = itemView.findViewById(R.id.tv_description);
            ratingTextView = itemView.findViewById(R.id.tv_rating);
            editButton = itemView.findViewById(R.id.btn_edit);
            deleteButton = itemView.findViewById(R.id.btn_delete);
            facebookButton = itemView.findViewById(R.id.btn_share_facebook);
            twitterButton = itemView.findViewById(R.id.btn_share_twitter);
            pinterestButton = itemView.findViewById(R.id.btn_share_pinterest);
            mapButton = itemView.findViewById(R.id.button_open_map); // Harita butonunun bağlanması
        }
    }
}
