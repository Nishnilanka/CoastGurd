package com.example.nishangunawardena.costgurdapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Arrival extends Activity implements View.OnClickListener{

    AutoCompleteTextView text;
    TextView harbour;
    TextView date, boatName;
    CheckBox complteLogBook, prohibitCheckBox, sharkCheckBox;
    Button sendButton;
    EditText remarks;
    String regNumber;
    String voyageNo;
    ArrayList<String> IMULANo = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);

        getArrivalIMUL getArrivalIMULA = new getArrivalIMUL();
        getArrivalIMULA.execute("http://192.248.22.121/GPS_mobile/Nishan/getArrivalIMUL.php");
        final Spinner spinner=(Spinner)findViewById(R.id.arrivalspinner);
        text = (AutoCompleteTextView) findViewById(R.id.arrivalRegNo);

        harbour = (TextView) findViewById(R.id.arrharbourText);
        date = (TextView) findViewById(R.id.arrdepDate);
        boatName = (TextView) findViewById(R.id.arrboatTextfield);
        sendButton = (Button) findViewById(R.id.btnArribalSubmit);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String areaCode = spinner.getSelectedItem().toString();

                text.setText("");
                text.setThreshold(1);
                IMULANo.clear();
                getIMULAbyAreaCode(areaCode);
                adapter = new ArrayAdapter<String>(Arrival.this, R.layout.support_simple_spinner_dropdown_item, IMULANo);
                adapter.notifyDataSetChanged();
                text.setAdapter(adapter);
                RegNoSelect regNo = new RegNoSelect();
                text.setOnItemClickListener(regNo);
                text.setOnItemSelectedListener(regNo);
                text.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_DEL)
                {
                    System.out.println(text.getText().length());
                    if(text.getText().length()!= 12) {
                        //Toast.makeText(getApplicationContext(), "Nishan", Toast.LENGTH_LONG).show();
                        harbour.setText("");
                        boatName.setText("");
                        date.setText("");
                        sendButton.setEnabled(false);
                    }
                }
                return false;
            }
        });

        remarks = (EditText) findViewById(R.id.arrivalRemarks);
        complteLogBook = (CheckBox) findViewById(R.id.checkCompleteLogBook);
        prohibitCheckBox = (CheckBox) findViewById(R.id.checksea);
        sharkCheckBox = (CheckBox) findViewById(R.id.checkshark);
        sendButton.setOnClickListener(this);

    }

    public void getIMULAbyAreaCode(String code)
    {

        for (String original : getArrivalIMUL.ArrivalRegNo)
        {

            System.out.println(original.substring(original.length()-3) +" "+ code);
            //System.out.println(IMULANo);
            if (original.substring(original.length()-3).equals(code)){
                System.out.println(original.substring(original.length()-3));
                IMULANo.add(original);

            }
            //System.out.println(IMULANo);
        }


    }

    @Override
    public void onClick(View v) {
        switch  (v.getId())
        {
            case R.id.btnArribalSubmit:
                String completeLogCheck = null;
                String prohibitCheck = null;
                String sharkCheck = null;
                String imulArrival = text.getText().toString();
                if(complteLogBook.isChecked()) {
                    completeLogCheck = "Yes";
                }
                else if(!complteLogBook.isChecked())
                {
                    completeLogCheck = "No";
                }
                if (prohibitCheckBox.isChecked()) {
                    prohibitCheck = "Yes";
                }
                else if (!prohibitCheckBox.isChecked()) {
                    prohibitCheck = "NO";
                }
                if(sharkCheckBox.isChecked()) {
                    sharkCheck = "yes";
                }
                else if(!sharkCheckBox.isChecked()) {
                    sharkCheck = "No";
                }

                String remarksValue = remarks.getText().toString();
                SendArrivalData sendArrivalData = new SendArrivalData();

                if(isConnectingToInternet()) {
                    try {
                        String safeUrl = "http://192.248.22.121/GPS_mobile/Nishan/SendArrivalData.php?" +
                                "q=" + URLEncoder.encode(imulArrival) + "&voyageNo=" + URLEncoder.encode(voyageNo) +
                                "&log_sheets=" + URLEncoder.encode(completeLogCheck) +
                                "&prohibited_species=" + URLEncoder.encode(prohibitCheck) +
                                "&whole_shark=" + URLEncoder.encode(sharkCheck) +
                                "&remarks=" + URLEncoder.encode(remarksValue) + "";


                        String result = sendArrivalData.execute(safeUrl).get();
                        if (result.length() > 0)
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
                        getArrivalIMUL.ArrivalRegNo.clear();
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Arrival.this);
                    builder.setMessage("You are not connected to the internet!\nඔබ අන්තර්ජාලයට සම්බන්ද නැත!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                //System.out.print(s);

                break;
        }
    }


    @Override
    public void onBackPressed() {
        getArrivalIMUL.ArrivalRegNo.clear();
        finish();
        return;
    }


    class RegNoSelect implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            regNumber = (String) text.getAdapter().getItem(position);
            String[] array;
            GetHarbourAndDate GHD = new GetHarbourAndDate();
            String s = null;
            if(isConnectingToInternet()) {
                try {
                    s = GHD.execute("http://192.248.22.121/GPS_mobile/Nishan/getHarbourAndDate.php?q=" + regNumber).get();
                    System.out.println("PHP array" + s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                array = s.split("@");
                harbour.setText("");
                date.setText("");
                boatName.setText("");
                harbour.setText(array[1]);
                date.setText(array[0]);
                boatName.setText(array[2]);
                voyageNo = array[3];
                sendButton.setEnabled(true);
            }else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Arrival.this);
                builder.setMessage("You are not connected to the internet!\nඔබ අන්තර්ජාලයට සම්බන්ද නැත!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

        }



        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            regNumber = (String) text.getAdapter().getItem(position);
            String[] array;
            System.out.println(regNumber);
            GetHarbourAndDate GHD = new GetHarbourAndDate();
            String s = null;
            try {
                s = GHD.execute("http://192.248.22.121/GPS_mobile/Nishan/getHarbourAndDate.php?q="+regNumber).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            array = s.split("@");
            //System.out.print(array);
            harbour.setText("");
            date.setText("");
            boatName.setText("");
            harbour.setText(array[1]);
            date.setText(array[0]);
            boatName.setText(array[2]);
            voyageNo = array[3];
            sendButton.setEnabled(true);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            sendButton.setEnabled(false);
        }
    }

    public boolean isConnectingToInternet(){
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;
    }
}
