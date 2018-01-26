package com.japkaur.one;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
