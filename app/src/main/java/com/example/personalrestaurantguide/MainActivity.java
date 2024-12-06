package com.example.personalrestaurantguide;
// by civan
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteHelper dbHelper;
    private RestaurantAdapter adapter;
    private RecyclerView recyclerView;
    private View categoryScroll, headerLayout, cardAbout, cardAddRestaurant, cardViewRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable immersive fullscreen mode
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.setTooltipText(null);



        dbHelper = new SQLiteHelper(this);

        categoryScroll = findViewById(R.id.category_scroll);
        headerLayout = findViewById(R.id.header_layout);
        cardAbout = findViewById(R.id.card_about);
        cardAddRestaurant = findViewById(R.id.card_add_restaurant);
        cardViewRestaurants = findViewById(R.id.card_view_restaurants);

        setupCardListeners();
        setupRecyclerView();
        setupSearchBarListener();
    }

    private void setupCardListeners() {
        cardAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        cardAddRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRestaurantActivity.class);
            startActivity(intent);
        });

        cardViewRestaurants.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setVisibility(View.GONE);

        List<Restaurant> restaurantList = dbHelper.getAllRestaurants();
        adapter = new RestaurantAdapter(restaurantList, new RestaurantAdapter.OnRestaurantActionListener() {
            @Override
            public void onEditClicked(Restaurant restaurant) {
                Intent intent = new Intent(MainActivity.this, AddRestaurantActivity.class);
                intent.putExtra("id", restaurant.getId());
                intent.putExtra("name", restaurant.getName());
                intent.putExtra("address", restaurant.getAddress());
                intent.putExtra("phone", restaurant.getPhone());
                intent.putExtra("description", restaurant.getDescription());
                intent.putExtra("rating", restaurant.getRating());
                startActivity(intent);
            }

            @Override
            public void onDeleteClicked(Restaurant restaurant) {
                dbHelper.deleteRestaurant(restaurant.getId());
                List<Restaurant> updatedList = dbHelper.getAllRestaurants();
                adapter.updateList(updatedList);

                if (updatedList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    toggleOtherViews(View.VISIBLE);
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void setupSearchBarListener() {
        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().trim();

                if (query.isEmpty()) {
                    toggleOtherViews(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    List<Restaurant> filteredList = dbHelper.searchRestaurants(query);
                    if (filteredList.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        toggleOtherViews(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        toggleOtherViews(View.GONE);
                        adapter.updateList(filteredList);
                    }
                }
            }
        });
    }

    private void toggleOtherViews(int visibility) {
        categoryScroll.setVisibility(visibility);
        headerLayout.setVisibility(visibility);
        cardAbout.setVisibility(visibility);
        cardAddRestaurant.setVisibility(visibility);
        cardViewRestaurants.setVisibility(visibility);
    }
}
