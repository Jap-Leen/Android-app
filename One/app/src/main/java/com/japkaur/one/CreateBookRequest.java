
package com.japkaur.one;

/**
 * Created by jap on 15/1/18.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.japkaur.one.LoginActivity;

import static com.android.volley.Request.Method.GET;
import com.japkaur.one.Book;


public class CreateBookRequest extends AppCompatActivity {
    String url;
    private static final String TAG_RESULT = "predictions";
    JSONObject json;

    ArrayList<String> names;
    ArrayList<String> booksString;
    List<Book> books;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> bookAdapter;
    String browserKey = "AIzaSyC2JsOqNmUAzZA_IN83bjpotK0CyFwvTxE";
    private final static String ApiKEY = "N7qI6ygxuHc0ZxIlhK0KJg";

    private Book book;



    public void updateList(String place) {
        String input = "";

        try {
            input = "input=" + URLEncoder.encode(place, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        String output = "json";
        String parameter = input + "&types=geocode&sensor=true&key="
        + browserKey;

        url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
        + output +"?" + parameter;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray ja = response.getJSONArray(TAG_RESULT);
                    names.clear();
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject c = ja.getJSONObject(i);
                        String description = c.getString("description");
                        Log.d("description", description);
                        names.add(description);
                    }

                    adapter = new ArrayAdapter<String>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1, names) {
                        @Override
                        public View getView(int position,
                                            View convertView, ViewGroup parent) {
                            View view = super.getView(position,
                                    convertView, parent);
                            TextView text = (TextView) view
                                    .findViewById(android.R.id.text1);
                            text.setTextColor(Color.BLACK);
                            return view;
                        }
                    };
                    createreqInputLocation.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        MyApplication.getInstance().addToReqQueue(jsonObjReq,"jreq");
    }


    public void updateBookList(final String bookText) {

        ApiInterface serv= ApiClient.getClient().create(ApiInterface.class);
        serv.getBookInfoByString(ApiKEY,bookText).enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, retrofit2.Response<BooksResponse> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "] Size: " + response.body().search.books.size());

                books = response.body().getSearch().getBooks();
                
                booksString.clear();
                
                for(int i=0;i<books.size();i++){
                    booksString.add(books.get(i).getBookDetails().getTitle());
                }

                bookAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1, booksString) {
                    @Override
                    public View getView(int position,
                                        View convertView, ViewGroup parent) {
                        View view = super.getView(position,
                                convertView, parent);
                        TextView text = (TextView) view
                                .findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                createreqInputName.setAdapter(bookAdapter);
                createreqInputName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        book = books.get(i);
                    }
                });
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }





    private static final String TAG = "CreateBookRequestActivity";
     Integer id;
    private static final String URL_FOR_REQUESTING = "http://ec2-13-127-54-127.ap-south-1.compute.amazonaws.com/users/createrequest";
    ProgressDialog progressDialog;

    //private List<Book> books;

    private EditText createreqInputQuantity, createreqInputEmail;
    private Button btnCreateReq;
    UserSessionManager sess=LoginActivity.session;

    private AutoCompleteTextView createreqInputLocation, createreqInputName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createrequest);
        //sess = LoginActivity.session;
        if(sess.checkLogin())
           finish();

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        createreqInputLocation = (AutoCompleteTextView) findViewById(R.id.enterrequestlocation);
        createreqInputName = (AutoCompleteTextView) findViewById(R.id.enterrequestname);
        createreqInputQuantity = (EditText) findViewById(R.id.enterrequestquantity);
        createreqInputEmail = (EditText) findViewById(R.id.enterrequestemail);

        createreqInputLocation.setThreshold(0);
        createreqInputName.setThreshold(0);

        names = new ArrayList<String>();

        createreqInputLocation.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                    updateList(s.toString());

            }
        });

        createreqInputName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                    updateBookList(s.toString());
            }
        });

        booksString = new ArrayList<>();

        btnCreateReq = (Button) findViewById(R.id.submitrequest);
        Book bookrequested = new Book();
        //bookrequested.getTitle();


        id = bookrequested.getId();
        btnCreateReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm() {

        createRequest(createreqInputLocation.getText().toString(), book.getId(),
                Integer.parseInt(createreqInputQuantity.getText().toString()),
                createreqInputEmail.getText().toString());
    }

    private void createRequest( final String location, final int id, final int quantity,
                               final String email) {
        // Tag used to cancel the request
        Log.d(TAG, "createRequest() called with: location = [" + location + "], id = [" + id + "], quantity = [" + quantity + "], email = [" + email + "]");
        String cancel_req_tag = "cancelrequest";

        progressDialog.setMessage("Adding request ...");
        showDialog();



        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REQUESTING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Request Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("username");
                        Toast.makeText(getApplicationContext(), "Hi " + user +", Your request was successfully Added!", Toast.LENGTH_SHORT).show();

                        // Launch dashboard activity
                        Intent intent = new Intent(
                                CreateBookRequest.this,
                                Dashboard.class);
                        startActivity(intent);
                        finish();
                    } else {

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
                Log.e(TAG, "Request Error: " + error);
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            //String a=sess.fetchToken();
            @Override
            protected Map<String, String> getParams() {
                // Posting params to request url
                Map<String, String> params = new HashMap<String, String>();
                params.put("location", location);
                params.put("isbnNumber", String.valueOf(id));
                params.put("quantity", String.valueOf(quantity));
                params.put("email", email);
                params.put("token", sess.fetchToken());
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