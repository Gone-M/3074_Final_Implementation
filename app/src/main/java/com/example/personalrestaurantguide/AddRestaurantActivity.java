package com.example.personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddRestaurantActivity extends AppCompatActivity {

    private SQLiteHelper dbHelper;
    private EditText etName, etAddress, etPhone, etDescription, etRating;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        dbHelper = new SQLiteHelper(this);

        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        etDescription = findViewById(R.id.et_description);
        etRating = findViewById(R.id.et_rating);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String ratingStr = etRating.getText().toString().trim();

            if (!name.isEmpty() && !address.isEmpty() && !ratingStr.isEmpty()) {
                int rating = Integer.parseInt(ratingStr);
                boolean isInserted = dbHelper.insertRestaurant(name, address, phone, description, rating);

                if (isInserted) {
                    Toast.makeText(this, "Restaurant Saved Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error Saving Restaurant!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please Fill All Fields!", Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (itemId == R.id.nav_view_restaurants) {
                startActivity(new Intent(this, RestaurantListActivity.class));
                return true;
            } else if (itemId == R.id.nav_add_restaurant) {
                return true;
            } else if (itemId == R.id.nav_about) {
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_add_restaurant);
    }
}
