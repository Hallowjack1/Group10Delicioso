package com.demo.group10delicioso;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Manage extends AppCompatActivity {
    Button btnSearch;
    TextView textView,txtDefault_CustomerName,txtDefault_Quantity,txtDefault_OrderName,txtDefault_OrderPrice,txtDefault_ID;
    private static EditText edtitemcode;
    private static JSONParser jParser = new JSONParser();
    private static String urlHostDelete = "http://192.168.254.107/Delicioso/Delete.php";
    private static String urlHostCustomerName ="http://192.168.254.107/Delicioso/SelectName.php";
    private static String urlHostQuantity = "http://192.168.254.107/Delicioso/SelectQuantity.php";
    private static String urlHostOrderName= "http://192.168.254.107/Delicioso/SelectOrderName.php";
    private static String urlHostOrderPrice = "http://192.168.254.107/Delicioso/SelectOrderPrice.php";
    private static String urlHostID = "http://192.168.254.107/Delicioso/SelectID.php";
    private static String TAG_MESSAGE = "message", TAG_SUCCESS = "success";
    private static String online_dataset = "";
    private static String cItemcode = "";

    private String aydi,neym,quant,ordneym,ordpryce;

    String cItemSelected_CustomerName,cItemSelected_Quantity,cItemSelected_OrderName,cItemSelected_OrderPrice,cItemSelected_ID;
    ArrayAdapter<String> adapter_CustomerName;
    ArrayAdapter <String> adapter_Quantity;
    ArrayAdapter <String> adapter_OrderName;
    ArrayAdapter <String> adapter_OrderPrice;
    ArrayAdapter <String> adapter_ID;
    ArrayList<String> list_CustomerName;
    ArrayList <String> list_Quantity;
    ArrayList <String> list_OrderName;
    ArrayList <String> list_OrderPrice;
    ArrayList <String> list_ID;
    Context context = this;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        edtitemcode = (EditText) findViewById(R.id.edtitemcode);
        listView = (ListView) findViewById(R.id.listview);
        textView = (TextView) findViewById(R.id.textView4);
        txtDefault_CustomerName = (TextView) findViewById(R.id.txt_CustomerName);
        txtDefault_Quantity = (TextView) findViewById(R.id.txt_Quantity);
        txtDefault_OrderName = (TextView) findViewById(R.id.txt_OrderName);
        txtDefault_OrderPrice = (TextView) findViewById(R.id.txt_OrderPrice);
        txtDefault_ID = (TextView) findViewById(R.id.txt_ID);

        txtDefault_CustomerName.setVisibility(View.GONE);
        txtDefault_Quantity.setVisibility(View.GONE);
        txtDefault_OrderName.setVisibility(View.GONE);
        txtDefault_OrderPrice.setVisibility(View.GONE);
        txtDefault_ID.setVisibility(View.GONE);

        Toast.makeText(Manage.this, "Nothing Selected", Toast.LENGTH_SHORT).show();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cItemcode = edtitemcode.getText().toString();
                new uploadDataToURL().execute();
                //new CustomerName().execute();
                new Quantity().execute();
                new OrderName().execute();
                new OrderPrice().execute();
                new ID().execute();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                cItemSelected_CustomerName = adapter_CustomerName.getItem(position);
                cItemSelected_Quantity = adapter_Quantity.getItem(position);
                cItemSelected_OrderName = adapter_OrderName.getItem(position);
                cItemSelected_OrderPrice = adapter_OrderPrice.getItem(position);
                cItemSelected_ID = adapter_ID.getItem(position);

                androidx.appcompat.app.AlertDialog.Builder alert_confirm =
                        new androidx.appcompat.app.AlertDialog.Builder(context);
                alert_confirm.setMessage("Edit the records of" + " " + cItemSelected_CustomerName);
                alert_confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        txtDefault_CustomerName.setText(cItemSelected_CustomerName);
                        txtDefault_Quantity.setText(cItemSelected_Quantity);
                        txtDefault_OrderName.setText(cItemSelected_OrderName);
                        txtDefault_OrderPrice.setText(cItemSelected_OrderPrice);
                        txtDefault_ID.setText(cItemSelected_ID);

                        neym = txtDefault_CustomerName.getText().toString().trim();
                        quant = txtDefault_Quantity.getText().toString().trim();
                        ordneym = txtDefault_OrderName.getText().toString().trim();
                        ordpryce = txtDefault_OrderPrice.getText().toString().trim();
                        aydi = txtDefault_ID.getText().toString().trim();

                        Intent intent = new Intent(Manage.this, EditOrder.class);
                        intent.putExtra(EditOrder.NEYM,neym);
                        intent.putExtra(EditOrder.QUANT,quant);
                        intent.putExtra(EditOrder.ORDNEYM,ordneym);
                        intent.putExtra(EditOrder.ORDPRYCE,ordpryce);
                        intent.putExtra(EditOrder.ID,aydi);

                        startActivity(intent);

                    }
                });
                alert_confirm.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert_confirm.show();
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                cItemSelected_CustomerName = adapter_CustomerName.getItem(position);
                cItemSelected_Quantity = adapter_Quantity.getItem(position);
                cItemSelected_OrderName = adapter_OrderName.getItem(position);
                cItemSelected_OrderPrice = adapter_OrderPrice.getItem(position);
                cItemSelected_ID = adapter_ID.getItem(position);

                androidx.appcompat.app.AlertDialog.Builder alert_confirm =
                        new androidx.appcompat.app.AlertDialog.Builder(context);
                alert_confirm.setMessage("Are you sure you want to delete" + " " + cItemSelected_CustomerName);
                alert_confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        txtDefault_ID.setText(cItemSelected_ID);
                        aydi = txtDefault_ID.getText().toString().trim();
                        new delete().execute();
                    }
                });
                alert_confirm.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert_confirm.show();
            }
        });
    }

    private class uploadDataToURL extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(Manage.this);

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

                cPostSQL = cItemcode;
                cv.put("code", cPostSQL);

                JSONObject json = jParser.makeHTTPRequest(urlHostCustomerName, "POST", cv);
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
            android.app.AlertDialog.Builder alert = new AlertDialog.Builder(Manage.this);
            if (s != null) {
                if (isEmpty.equals("") && !s.equals("HTTPSERVER_ERROR")) { }
                //toast.makeText(ManageRecords.this, s, Toast.LENGTH_SHORT).show();
                String wew = s;

                String str = wew;
                final String CustomerName[] = str.split("-");
                list_CustomerName = new ArrayList<String>(Arrays.asList(CustomerName));
                adapter_CustomerName = new ArrayAdapter<String>(Manage.this,
                        android.R.layout.simple_list_item_1,list_CustomerName);

                listView.setAdapter(adapter_CustomerName);
                textView.setText(listView.getAdapter().getCount() + " " +"record(s) fround.");


            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }
    }

    private class Quantity extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(Manage.this);

        public Quantity() {
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

                cPostSQL = cItemcode;
                cv.put("code", cPostSQL);

                JSONObject json = jParser.makeHTTPRequest(urlHostQuantity, "POST", cv);
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


            } catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String Quantity) {
            super.onPostExecute(Quantity);
            pDialog.dismiss();
            String isEmpty = "";
            android.app.AlertDialog.Builder alert = new AlertDialog.Builder(Manage.this);
            if (Quantity != null) {
                if (isEmpty.equals("") && !Quantity.equals("HTTPSERVER_ERROR")) { }


                String quant = Quantity;

                String str = quant;
                final String Quanti[] = str.split("-");
                list_Quantity = new ArrayList<String>(Arrays.asList(Quanti));
                adapter_Quantity = new ArrayAdapter<String>(Manage.this,
                        android.R.layout.simple_list_item_1,list_Quantity);

                //listView.setAdapter(adapter_gender);



            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }
    }
    private class OrderName extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(Manage.this);

        public OrderName() {
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

                cPostSQL = cItemcode;
                cv.put("code", cPostSQL);

                JSONObject json = jParser.makeHTTPRequest(urlHostOrderName, "POST", cv);
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


            } catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String OrderName) {
            super.onPostExecute(OrderName);
            pDialog.dismiss();
            String isEmpty = "";
            android.app.AlertDialog.Builder alert = new AlertDialog.Builder(Manage.this);
            if (OrderName != null) {
                if (isEmpty.equals("") && !OrderName.equals("HTTPSERVER_ERROR")) { }


                String OName = OrderName;

                String str = OName;
                final String OrderNeym[] = str.split("-");
                list_OrderName = new ArrayList<String>(Arrays.asList(OrderNeym));
                adapter_OrderName = new ArrayAdapter<String>(Manage.this,
                        android.R.layout.simple_list_item_1,list_OrderName);

                //listView.setAdapter(adapter_gender);



            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }
    }

    private class OrderPrice extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(Manage.this);

        public OrderPrice() {
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

                cPostSQL = cItemcode;
                cv.put("code", cPostSQL);

                JSONObject json = jParser.makeHTTPRequest(urlHostOrderPrice, "POST", cv);
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


            } catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String OrderPrice) {
            super.onPostExecute(OrderPrice);
            pDialog.dismiss();
            String isEmpty = "";
            android.app.AlertDialog.Builder alert = new AlertDialog.Builder(Manage.this);
            if (OrderPrice != null) {
                if (isEmpty.equals("") && !OrderPrice.equals("HTTPSERVER_ERROR")) { }


                String ordpryce = OrderPrice;

                String str = ordpryce;
                final String OrderPryce[] = str.split("-");
                list_OrderPrice = new ArrayList<String>(Arrays.asList(OrderPryce));
                adapter_OrderPrice = new ArrayAdapter<String>(Manage.this,
                        android.R.layout.simple_list_item_1,list_OrderPrice);

                //listView.setAdapter(adapter_gender);

            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }
    }

    private class ID extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(Manage.this);

        public ID() {
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

                cPostSQL = cItemcode;
                cv.put("code", cPostSQL);

                JSONObject json = jParser.makeHTTPRequest(urlHostID, "POST", cv);
                if (json != null) {
                    nSuccess = json.getInt(TAG_SUCCESS);
                    if (nSuccess == 1){
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
        protected void onPostExecute(String aydi) {
            super.onPostExecute(aydi);
            pDialog.dismiss();
            String isEmpty = "";
            android.app.AlertDialog.Builder alert = new AlertDialog.Builder(Manage.this);
            if (aydi != null) {
                if (isEmpty.equals("") && !aydi.equals("HTTPSERVER_ERROR")) { }
                Toast.makeText(Manage.this, "Data selected", Toast.LENGTH_SHORT).show();

                String AYDI = aydi;

                String str = AYDI;
                final String ayds[] = str.split("-");
                list_ID = new ArrayList<String>(Arrays.asList(ayds));
                adapter_ID = new ArrayAdapter<String>(Manage.this,
                        android.R.layout.simple_list_item_1,list_ID);

                //listView.setAdapter(adapter_gender);



            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }
    }

    private class delete extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(Manage.this);

        public delete() {
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

                cPostSQL = cItemSelected_ID;
                cv.put("id", cPostSQL);

                JSONObject json = jParser.makeHTTPRequest(urlHostDelete, "POST", cv);
                if (json != null) {
                    nSuccess =json.getInt(TAG_SUCCESS);
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
        protected void onPostExecute(String del) {
            super.onPostExecute(del);
            pDialog.dismiss();
            String isEmpty = "";
            android.app.AlertDialog.Builder alert = new AlertDialog.Builder(Manage.this);
            if (aydi != null) {
                if (isEmpty.equals("") && !del.equals("HTTPSERVER_ERROR")) { }
                Toast.makeText(Manage.this, "Data Deleted", Toast.LENGTH_SHORT).show();
            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }
    }
}