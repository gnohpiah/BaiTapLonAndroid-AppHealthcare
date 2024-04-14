package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LapTestDetailActivity extends AppCompatActivity {

    TextView tvPackageName,tvTotalCost;
    EditText edDetails;
    Button btnAddToCart, btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_test_detail);

        tvPackageName = findViewById(R.id.textViewLDPackageName);
        tvTotalCost = findViewById(R.id.textViewLDTotalCost);
        edDetails= findViewById(R.id.editTextTextLDMultiLine);
        btnback = findViewById(R.id.buttonLDBack);
        btnAddToCart= findViewById(R.id.buttonLDAddToCart);

        edDetails.setKeyListener(null);
        tvPackageName.setText(getIntent().getStringExtra("text1"));
        edDetails.setText(getIntent().getStringExtra("text2"));
        tvTotalCost.setText("Total Cost : " +getIntent().getStringExtra("text3") + "/-");

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LapTestDetailActivity.this,LapTestActivity.class));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("share_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat(getIntent().getStringExtra("text3").toString());

                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                if(db.checkCart(username, product)==1)
                {
                    Toast.makeText(getApplicationContext(), "Product already added", Toast.LENGTH_SHORT).show();
                } else {
                    db.addCart(username, product, price,"lab");
                    Toast.makeText(getApplicationContext(), "Record Insert to Cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LapTestDetailActivity.this, LapTestActivity.class));
                }
            }
        });

    }
}