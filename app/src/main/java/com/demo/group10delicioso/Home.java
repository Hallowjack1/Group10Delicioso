package com.demo.group10delicioso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {
ImageView imgWhopper, imgWhopperCheese, imgBakon, imgBakonDouble, imgRoyale, imgRoyaleCheese;
Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgWhopper = findViewById(R.id.imgWhopper);
        imgWhopperCheese = findViewById(R.id.imgWhopperCHeese);
        imgBakon = findViewById(R.id.imgBakon);
        imgBakonDouble = findViewById(R.id.imgBakonDouble);
        imgRoyale = findViewById(R.id.imgRoyale);
        imgRoyaleCheese = findViewById(R.id.imgRoyaleCheese);

        imgWhopper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Home.this, Order.class);
                i.putExtra("resId", R.drawable.plantbasewhooper);
                i.putExtra("OrderName", "Plant Base Whopper");
                i.putExtra("OrderPrice", "60");
                startActivity(i);
            }
        });

        imgWhopperCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Home.this, Order.class);
                i.putExtra("resId", R.drawable.plantbasedwhooperwidcheese);
                i.putExtra("OrderName", "Plant Base Whopper w/ Cheese");
                i.putExtra("OrderPrice", "70");
                startActivity(i);
            }
        });

        imgBakon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Home.this, Order.class);
                i.putExtra("resId", R.drawable.bakonking);
                i.putExtra("OrderName", "Plant Base Bakon King");
                i.putExtra("OrderPrice", "90");
                startActivity(i);
            }
        });

        imgBakonDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Home.this, Order.class);
                i.putExtra("resId", R.drawable.bakonkingdouble);
                i.putExtra("OrderName", "Plant Base Bakon King Double");
                i.putExtra("OrderPrice", "100");
                startActivity(i);
            }
        });

        imgRoyale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Home.this, Order.class);
                i.putExtra("resId", R.drawable.veganroyale);
                i.putExtra("OrderName", "Vegan Royale");
                i.putExtra("OrderPrice", "70");
                startActivity(i);
            }
        });

        imgRoyaleCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Home.this, Order.class);
                i.putExtra("resId", R.drawable.veganroyalewidcheese);
                i.putExtra("OrderName", "Vegan Royale w/ Cheese");
                i.putExtra("OrderPrice", "80");
                startActivity(i);
            }
        });
    }
}