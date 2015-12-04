package com.example.nishangunawardena.costgurdapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class DashBoard extends AppCompatActivity {

    TextView boatDetailsType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        getBoatDetails boat = new getBoatDetails();
        String s = null;

        boatDetailsType = (TextView) findViewById(R.id.boatText);
        boatDetailsType.setText("All Boats");
        try {

            s = boat.execute("http://192.248.22.121/GPS_mobile/Nishan/getAllBoats.php").get();
            JSONArray jArray = new JSONArray(s);
            System.out.println(s);
            int count = jArray.length();
            String[] mobileArray = new String[count];
            for(int i=0; i<count;i++) {
                JSONObject json = jArray.getJSONObject(i);
                //System.out.println(json.getString("local_reg_no"));
                mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

            }
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.boats, mobileArray);

            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
            //System.out.print(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        ImageView departureImage = (ImageView) findViewById(R.id.departure);
        ImageView arrivalImage = (ImageView) findViewById(R.id.arrival);
        ImageView livemap = (ImageView) findViewById(R.id.liveMap);

        Button  deported = (Button) findViewById(R.id.toBeDeparture);
        Button allBoats = (Button) findViewById(R.id.allBoats);
        Button reportingRange = (Button) findViewById(R.id.btnreportingRange);
        Button arrivedBoat = (Button) findViewById(R.id.arrived);
        Button QCApprovedBoat = (Button) findViewById(R.id.QCapproved);
        Button FIApproved = (Button) findViewById(R.id.FIapproved);

        departureImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.departure: {

                        Intent intent;
                        intent = new Intent(v.getContext(), Departure.class);
                        startActivity(intent);

                        break;
                    }
                }
            }
        });

        arrivalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.arrival: {
                        /*ImageView view = (ImageView) v;
                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();*/
                        Intent intent;
                        intent = new Intent(v.getContext(), Arrival.class);
                        startActivity(intent);
                        /*view.getDrawable().clearColorFilter();
                        view.invalidate();*/
                        break;
                    }
                }
            }
        });

        livemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.liveMap: {
                        Intent intent;
                        intent = getPackageManager().getLaunchIntentForPackage("com.example.dula.test1");
                        startActivity(intent);
                        break;
                    }
                }
            }
        });

        deported.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = null;
                getBoats deportBoats =  new getBoats();
                try {

                    s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getToBeDeportedBoats.php").get();
                    JSONArray jArray = new JSONArray(s);
                    int count = jArray.length();
                    String[] mobileArray = new String[count];
                    for(int i=0; i<count;i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                    boatDetailsType.setText("Boats To be Deported");
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                    //System.out.print(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        allBoats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = null;
                getBoats deportBoats =  new getBoats();
                try {

                    s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getAllBoats.php").get();
                    JSONArray jArray = new JSONArray(s);
                    int count = jArray.length();
                    String[] mobileArray = new String[count];
                    for(int i=0; i<count;i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                    boatDetailsType.setText("All Boats");
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                    //System.out.print(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        reportingRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = null;
                getBoats deportBoats =  new getBoats();
                try {

                    s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getReportingRangeBoats.php").get();
                    JSONArray jArray = new JSONArray(s);
                    int count = jArray.length();
                    String[] mobileArray = new String[count];
                    for(int i=0; i<count;i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                    boatDetailsType.setText("Boats Within Reporting Range");
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                    //System.out.print(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        arrivedBoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = null;
                getBoats deportBoats =  new getBoats();
                try {

                    s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getArrivedBoats.php").get();
                    JSONArray jArray = new JSONArray(s);
                    int count = jArray.length();
                    String[] mobileArray = new String[count];
                    for(int i=0; i<count;i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                    boatDetailsType.setText("Arrived Boats");
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                    //System.out.print(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        QCApprovedBoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = null;
                getBoats deportBoats =  new getBoats();
                try {

                    s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getQCApprovedBoats.php").get();
                    JSONArray jArray = new JSONArray(s);
                    int count = jArray.length();
                    String[] mobileArray = new String[count];
                    for(int i=0; i<count;i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                    boatDetailsType.setText("QC Approved Boats -  Arrival");
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                    //System.out.print(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        FIApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = null;
                getBoats deportBoats =  new getBoats();
                try {

                    s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getFIApprovedBoats.php").get();
                    JSONArray jArray = new JSONArray(s);
                    int count = jArray.length();
                    String[] mobileArray = new String[count];
                    for(int i=0; i<count;i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                    boatDetailsType.setText("FI Approved Boats -  Arrival");
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                    //System.out.print(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}
