package com.example.rajn.enterpriseresiliencyawareness;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class description extends AppCompatActivity {

    String tmp;
    EditText feed;
    AutoCompleteTextView email;
    String FILE_NAME;
    HashMap<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        FILE_NAME = "users_list.xml";
        map = new HashMap<>();
        tmp ="";

        final Context context = this;
        email = (AutoCompleteTextView) findViewById(R.id.mail);
        feed = (EditText) findViewById(R.id.describe);
        email.setText("");

        final String emailFeed = email.getText().toString();
        final String feedD = feed.getText().toString();

        final String feel = getIntent().getStringExtra("feel");


        //words
        final int index = -1;
        final List<String> name = new ArrayList<>(); //if searched by name
        final List<String> id = new ArrayList<>();   //if searched by id
        name.add("Nikhil Raj");id.add("rajn");
        //-----





        //setting up the asset manager
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("name.txt")));
            BufferedReader id_reader = new BufferedReader(new InputStreamReader(getAssets().open("id.txt")));
            String line = "";

            while((line = reader.readLine()) != null) name.add(line);

            int i = 0;

            while((line = id_reader.readLine()) != null)
            {
                if(line.trim().equals("")) line = name.get(i);
                line = line.replace("@vmware.com","");
                id.add(line);++i;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //----------------------------





        //setting up the suggestions
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,id);
        email.setAdapter(adapter);
        email.setThreshold(1);
        //--------------------------

        String email_feed = "";


        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b)
                {
                    if(id.contains(email.getText().toString()))
                    {
                        tmp = email.getText().toString();
                        int index = -1;
                        index = id.indexOf(tmp);
                        email.setText(name.get(index));
                    }
                    else if(email.getText().toString() == "") {
                        //Alerting for error
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set title
                        alertDialogBuilder.setTitle("User ID empty");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("User ID can not be empty")
                                .setCancelable(false)
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        //description.this.finish();
                                        email.setText("");
                                        email.requestFocus();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                    else{
                        //Alerting for error
                        email.setText("");
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set title
                        alertDialogBuilder.setTitle("ID not found");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Please give a proper ID")
                                .setCancelable(false)
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        //description.this.finish();
                                        email.setText("");
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                }
            }
        });



        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (email.getText().toString() != "" && !name.contains(email.getText().toString())) {
                    //Alerting for error
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title
                    alertDialogBuilder.setTitle("ID not found");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Please give a proper ID")
                            .setCancelable(false)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    //description.this.finish();
                                    email.setText("");
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                } else if (email.getText().toString() == "") {
                    //Alerting for error
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title
                    alertDialogBuilder.setTitle("ID Empty");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("ID can not be empty")
                            .setCancelable(false)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    //description.this.finish();
                                    email.setText("");
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }else{


                //Specifying the components of the portal submission
                String url = "http://oasisfeedback.000webhostapp.com/feed.php";
                String request_method = "GET";


                String email_feed = tmp;


                String description_feed = feed.getText().toString();
                description_feed = description_feed.replace(" ", "+");
                String USER_AGENT = "Mozilla/5.0";
                //--------------------------------------------------

                //Modifying the url to accomodate the form data
                url += "?";
                url += "email=" + email_feed + "%40vmware.com&feel=" + feel + "&description=" + description_feed;
                //---------------------------------------------

                //actually submitting the data to the portal
                try {
                    RequestQueue queue = Volley.newRequestQueue(description.this);
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // display response
                                    Log.d("Response", response.toString());
                                    System.out.println("Response : " + response.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //Log.d("Error.Response", response);
                                    System.out.println("Galti ho gayi bhai !! error : ");
                                }
                            }
                    );

                    // add it to the RequestQueue
                    queue.add(getRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                    //Alerting for error
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title
                    alertDialogBuilder.setTitle("Error in sending");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("error in sending data to the portal")
                            .setCancelable(false)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    description.this.finish();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                    //------------------
                }


                //------------------------------------------


                //Alerting
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Your Feed back as been recorded");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Thanks for your feed back")
                        .setCancelable(false)
                        .setPositiveButton("Okay, Great!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                description.this.finish();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                //--------
            }
            }
        });
    }
}