package com.demo.group10delicioso;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class EditOrder extends AppCompatActivity {
    Button btnEdit;
    private static EditText custneym,quanti;
    private  static TextView orderneym,orderpryce;
    private static String cItemcode = "";
    private static JSONParser jParser = new JSONParser();
    private static String urlHost = "http://192.168.110.91/Delicioso/UpdateQty.php";
    private static String TAG_MESSAGE = "message" , TAG_SUCCESS = "success";
    private static String online_dataset = "";
    public static String String_isempty = "";
    public static final String NEYM = "NEYM";
    public static final String QUANT = "QUANT";
    public static final String ORDNEYM = "ORDNEYM";
    public static final String ORDPRYCE = "ORDPRYCE";
    public static final String ID = "ID";

    private String aydi,neym,quant,ordneym,ordpryce;

    public static String CustomerName = "";
    public static String Quantity = "";
    public static String OrderName = "";
    public static String OrderPrice = "";



    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        btnEdit = (Button) findViewById(R.id.btnEdit);
        custneym = (EditText) findViewById(R.id.editName);
        quanti = (EditText) findViewById(R.id.editQuantity);
        orderneym = (TextView) findViewById(R.id.editOrderName);
        orderpryce = (TextView) findViewById(R.id.editOrderPrice);

        Intent i = getIntent();
        neym = i.getStringExtra(NEYM);
        quant = i.getStringExtra(QUANT);
        ordneym = i.getStringExtra(ORDNEYM);
        ordpryce = i.getStringExtra(ORDPRYCE);
        aydi = i.getStringExtra(ID);

        custneym.setText(neym);
        quanti.setText(quant);
        orderneym.setText(ordneym);
        orderpryce.setText(ordpryce);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerName = custneym.getText().toString();
                Quantity = quanti.getText().toString();
                OrderName = orderneym.getText().toString();
                OrderPrice = orderpryce.getText().toString();

                new uploadDataToURL().execute();

                Intent i = new Intent(EditOrder.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private class uploadDataToURL extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(EditOrder.this);

        public uploadDataToURL() { }
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
                //insert anything in this cod

                cPostSQL = aydi;
                cv.put("id", cPostSQL);

                cPostSQL = " '" + CustomerName + "' ";
                cv.put("CustomerName", cPostSQL);

                cPostSQL = " '" + Quantity + "' ";
                cv.put("Quantity", cPostSQL);

                cPostSQL = " '" + OrderName + "' ";
                cv.put("OrderName",cPostSQL);

                cPostSQL = " '" + OrderPrice + "' ";
                cv.put("OrderPrice", cPostSQL);



                JSONObject json = jParser.makeHTTPRequest(urlHost, "POST" , cv);
                if(json != null) {
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
            AlertDialog.Builder alert = new AlertDialog.Builder(EditOrder.this);
            if (s !=null) {
                if (isEmpty.equals("") && !s.equals("HTTPSERVER_ERROR")) { }
                Toast.makeText(EditOrder.this, s , Toast.LENGTH_SHORT).show();
            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }
    }
}