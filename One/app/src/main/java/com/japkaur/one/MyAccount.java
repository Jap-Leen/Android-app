package com.japkaur.one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by jap on 13/1/18.
 */

public class MyAccount extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to activity.xml
        setContentView(R.layout.activity_myaccount);
    }
    public void openDashboard(View view){
        Intent dashboard=new Intent (this, Dashboard.class);
        startActivity(dashboard);
    }
    public void openEditDetails(View view){
        Intent edit=new Intent (this, EditDetails.class);
        startActivity(edit);


    }
    public void openIAmHereTo(View view) {
        Intent hereto = new Intent(this, MyAccount.class);
        startActivity(hereto);
    }
    public void openMyAccount(View view) {
        Intent acc = new Intent(this, MyAccount.class);
        startActivity(acc);
    }

}
