package com.demo.group10delicioso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Home extends AppCompatActivity {
ImageView imgWhopper, imgWhopperCheese, imgBakon, imgBakonDouble, imgRoyale, imgRoyaleCheese,
        imgWhopperBakon, imgRoyaleBakon;
Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgWhopper = findViewById(R.id.imgWhopper);
        imgWhopperCheese = findViewById(R.id.imgWhopperCHeese);
        imgBakon = findViewById(R.id.imgBakon);
        imgBakonDouble = findViewById(R.id.imgBakonDouble);
        imgRoyale = findViewById(R.id.imgRoyale);
        imgRoyaleCheese = findViewById(R.id.imgRoyaleCheese);
        imgWhopperBakon = findViewById(R.id.imgWhopperBakon);
        imgRoyaleBakon = findViewById(R.id.imgRoyaleBakon);

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

        imgWhopperBakon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Home.this, Order.class);
                i.putExtra("resId", R.drawable.woppercheesebakon);
                i.putExtra("OrderName", "Plant Base Whopper Bakon");
                i.putExtra("OrderPrice", "80");
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

        imgRoyaleBakon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Home.this, Order.class);
                i.putExtra("resId", R.drawable.royalebakonking);
                i.putExtra("OrderName", "Vegan Royale Bakon King");
                i.putExtra("OrderPrice", "90");
                startActivity(i);
            }
        });
    }
}