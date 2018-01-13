package com.japkaur.one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by jap on 13/1/18.
 */

public class Dashboard extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to activity.xml
        setContentView(R.layout.activity_dashboard);
    }
    public void openDashboard(View view){
        Intent i=new Intent (this, Dashboard.class);
        startActivity(i);


    }
    public void openMyAccount(View view){
        Intent i=new Intent (this, MyAccount.class);
        startActivity(i);


    }
    public void openIAmHereTo(View view){
        Intent i=new Intent (this, IAmHereTo.class);
        startActivity(i);


    }
}
