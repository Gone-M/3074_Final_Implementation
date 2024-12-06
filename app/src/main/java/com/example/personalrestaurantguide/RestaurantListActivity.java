package com.example.personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private SQLiteHelper dbHelper;
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        recyclerView = findViewById(R.id.recycler_view_restaurants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new SQLiteHelper(this);

        List<Restaurant> restaurantList = dbHelper.getAllRestaurants();

        adapter = new RestaurantAdapter(restaurantList, new RestaurantAdapter.OnRestaurantActionListener() {
            @Override
            public void onEditClicked(Restaurant restaurant) {
                Intent intent = new Intent(RestaurantListActivity.this, AddRestaurantActivity.class);
                intent.putExtra("restaurant_id", restaurant.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClicked(Restaurant restaurant) {
                new AlertDialog.Builder(RestaurantListActivity.this)
                        .setTitle("Delete Confirmation")
                        .setMessage("Are you sure you want to delete this restaurant?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            dbHelper.deleteRestaurant(restaurant.getId());
                            restaurantList.remove(restaurant);
                            adapter.notifyDataSetChanged();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }
        });


        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (itemId == R.id.nav_view_restaurants) {
                return true;
            } else if (itemId == R.id.nav_add_restaurant) {
                startActivity(new Intent(this, AddRestaurantActivity.class));
                return true;
            } else if (itemId == R.id.nav_about) {
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_view_restaurants);
    }
}
