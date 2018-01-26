
package com.japkaur.one;

/**
 * Created by jap on 15/1/18.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "http://ec2-13-127-54-127.ap-south-1.compute.amazonaws.com/users/register";
    ProgressDialog progressDialog;


    private EditText signupInputUsername, signupInputEmail, signupInputPassword ,signupInputType;
    private Button btnSignUp;

    private  Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        signupInputUsername = (EditText) findViewById(R.id.register_enterusername);

        signupInputEmail = (EditText) findViewById(R.id.register_enteremail);
        signupInputPassword = (EditText) findViewById(R.id.register_enterpassword);

        spinner = (Spinner) findViewById(R.id.spinner);
        btnSignUp = (Button) findViewById(R.id.createaccount);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        addItemsOnSpinner();

    }
    public void addItemsOnSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);
        List<String > list = new ArrayList<String>();
        list.add("User");
        list.add("Admin");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void submitForm() {

        registerUser(signupInputUsername.getText().toString(),signupInputEmail.getText().toString(),
                signupInputPassword.getText().toString(),

                String.valueOf(signupInputType));
    }

    private void registerUser( final String username, final String email, final String password,
                               final String ty) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Adding you ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try{
                    Log.d(TAG, "Register Response: " + response.toString());
                    hideDialog();
                }
              catch (Exception e)
              { Log.e(TAG, "whhhhhhhhhat");}


                try {
                    JSONObject jObj = new JSONObject(response);
                    int intError = jObj.getInt("error");
                    boolean error = (intError > 0) ? true : false;
                    //boolean error = jObj.getBoolean("error");
                    //Log.d(TAG, "vygbuy");

                    if (!error) {

                       // String user = jObj.getJSONObject("user").getString("username");
                        Toast.makeText(getApplicationContext(), "Hi! You have been registered successfully!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        Log.e(TAG, "errrrrror1");
                        finish();
                    } else {
                        Log.e(TAG, "error2");
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                  e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("ty", ty);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}