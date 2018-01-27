package com.japkaur.one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by jap on 13/1/18.
 */

public class Dashboard extends Activity {
    // User Session Manager Class
    UserSessionManager session;
    // Button Logout
    Button btnLogout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to activity.xml
        setContentView(R.layout.activity_dashboard);
        // Button logout
        btnLogout = (Button) findViewById(R.id.btnLogout);
        FloatingActionButton fab;
        // Session class instance
        session = new UserSessionManager(getApplicationContext());


            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Dashboard.this, IAmHereTo.class);
                    startActivity(intent);
                }
            });
        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if(session.checkLogin())
            finish();


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Clear the User session data
                // and redirect user to LoginActivity
                session.logoutUser();
            }
        });
    }


    public void openDashboard(View view){
        Intent dashboard=new Intent (this, Dashboard.class);
        startActivity(dashboard);


    }
    public void openMyAccount(View view){
        Intent acc=new Intent (this, MyAccount.class);
        startActivity(acc);


    }

    public void openIAmHereTo(View view){
        Intent hereto=new Intent (this, IAmHereTo.class);
        startActivity(hereto);


    }

}
