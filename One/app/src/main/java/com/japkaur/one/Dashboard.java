package com.japkaur.one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
        FloatingActionButton fab;


            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Dashboard.this, IAmHereTo.class);
                    startActivity(intent);
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
