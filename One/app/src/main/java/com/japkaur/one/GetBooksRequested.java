package com.japkaur.one;

        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.DividerItemDecoration;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;

        import retrofit2.Callback;
        import retrofit2.Response;

        public class GetBooksRequested extends AppCompatActivity {
            private final static String API_KEY = "key";
            Button btnGetBooksRequested;

            List<Book> books;
            BooksAdapter adapter;
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.books_recycler_view);


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_contentview);
                books = new ArrayList<>();
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                btnGetBooksRequested = (Button) findViewById(R.id.getbooksrequested);


                //setting click event on Button
                btnGetBooksRequested.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //executing Async task class to create HTTP connection
                        new FetchInfo().execute();
                    }
                });
            }

            public class FetchInfo extends AsyncTask<Void, Void, Void> {

                ProgressDialog progressDialog;
                static final String URL_STRING =
                        "http://ec2-13-127-54-127.ap-south-1.compute.amazonaws.com/users/getbooksrequested";
                String response;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressDialog = new ProgressDialog(GetBooksRequested.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Fetching data..");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setProgress(0);
                    progressDialog.show();
                }

                @Override
                protected Void doInBackground(Void... voids) {
            /*
            creatingURLConnection is a function use to establish connection
            */
                    response = creatingURLConnection(URL_STRING);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            "Connection successful.", Toast.LENGTH_SHORT).show();

                    try {
                        if (response != null && !response.equals("")) {
                    /*
                    converting JSON response string into JSONArray
                    */

                            JSONArray responseArray = new JSONArray(response);
                            if (responseArray.length() > 0) {
                        /*
                        Iterating JSON object from JSON Array one by one
                        */
                                for (int i = 0; i < responseArray.length(); i++) {
                                    JSONObject battleObj = responseArray.getJSONObject(i);

                                    //creating object of model class
                                    Book book = new Book();
                            /*
                            fetching data based on key from JSON and setting into model class
                            */
                                  //  book.setId("id");
                                   // book.setQuantity("quantity");
                                    //book.setPrice("price");
                                    Integer a=book.getId();
                                    ApiInterface serv= ApiClient.getClient().create(ApiInterface.class);
                                    serv.getBookInfoById(API_KEY,a);
                                    //adding data into List
                                    books.add(book);

                                }

                                //calling RecyclerViewAdapter constructor by passing context and list
                                adapter = new BooksAdapter(books, getApplicationContext());

                                //setting adapter on recyclerView
                                recyclerView.setAdapter(adapter);

                                // to notify adapter about changes in list data(if changes)
                                adapter.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Error in fetching data.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            public String creatingURLConnection(String GET_URL) {
                String response = "";
                HttpURLConnection conn;
                StringBuilder jsonResults = new StringBuilder();
                try {
                    //setting URL to connect with
                    URL url = new URL(GET_URL);
                    //creating connection
                    conn = (HttpURLConnection) url.openConnection();
            /*
            converting response into String
            */
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    int read;
                    char[] buff = new char[1024];
                    while ((read = in.read(buff)) != -1) {
                        jsonResults.append(buff, 0, read);
                    }
                    response = jsonResults.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response;
            }
        }



