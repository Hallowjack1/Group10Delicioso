package com.demo.group10delicioso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Order extends AppCompatActivity {
    ImageView OrderPicture;
    TextView OrderName, OrderPrice;
    EditText etName, etQuantity;
    Button btnFinish;
    private static JSONParser jParser = new JSONParser();
    private static String urlHost = "http://192.168.110.91/Delicioso/Order.php";
    private static String TAG_MESSAGE = "message", TAG_SUCCESS = "success";
    private static String online_dataset = "";
    private static String customerName = "";
    private static String quantity = "";
    private static String orderName = "";
    private static String orderPrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        OrderPicture = findViewById(R.id.imgOrder);
        OrderName = findViewById(R.id.editOrderName);
        OrderPrice = findViewById(R.id.editOrderPrice);
        etName = findViewById(R.id.editName);
        etQuantity = findViewById(R.id.editQuantity);
        btnFinish = findViewById(R.id.btnEdit);

        Bundle bundle = getIntent().getExtras();
        String name, price;

        if (bundle != null) {
            int resId = bundle.getInt("resId");
            OrderPicture.setImageResource(resId);

            name = bundle.getString("OrderName");
            price = bundle.getString("OrderPrice");
            OrderName.setText(name);
            OrderPrice.setText(price);
        }

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderName = OrderName.getText().toString();
                orderPrice = OrderPrice.getText().toString();
                customerName = etName.getText().toString();
                quantity = etQuantity.getText().toString();
                new uploadDataToURL().execute();

                //DialogFragment dialogFragment=new DialogFragment();
                //dialogFragment.show(getSupportFragmentManager(),"Total");

                Intent i = new Intent(Order.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private class uploadDataToURL extends AsyncTask<String, String, String> {
        String CPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(Order.this);

        public uploadDataToURL() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setMessage(cMessage);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            int nSuccess;
            try {
                ContentValues cv = new ContentValues();
                //insert anything in this code
                cPostSQL = " '" + customerName + "' , '" + quantity + "' , '" + orderName + "' , '" + orderPrice + "' ";
                cv.put("code", cPostSQL);

                JSONObject json = jParser.makeHTTPRequest(urlHost, "POST", cv);
                if (json != null) {
                    nSuccess = json.getInt(TAG_SUCCESS);
                    if (nSuccess == 1) {
                        online_dataset = json.getString(TAG_MESSAGE);
                        return online_dataset;
                    } else {
                        return json.getString(TAG_MESSAGE);
                    }
                } else {
                    return "HTTPSERVER_ERROR";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            String isEmpty = "";
            AlertDialog.Builder alert = new AlertDialog.Builder(Order.this);
            if (s != null) {
                if (isEmpty.equals("") && !s.equals("HTTPSERVER_ERROR")) {
                }
                Toast.makeText(Order.this, s, Toast.LENGTH_SHORT).show();
            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }
    }
}