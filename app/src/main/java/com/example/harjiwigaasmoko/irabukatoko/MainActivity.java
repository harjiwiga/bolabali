package com.example.harjiwigaasmoko.irabukatoko;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.harjiwigaasmoko.irabukatoko.fragments.UserCredentialInput;
import com.example.harjiwigaasmoko.irabukatoko.fragments.UserListFragment;
import com.example.harjiwigaasmoko.irabukatoko.handler.DatabaseHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,UserCredentialInput.OnFragmentInteractionListener,View.OnClickListener {

    SharedPreferences prefs;
    SharedPreferences.Editor edits;
    TextView txtNamePersist;
    EditText editTextName;

    private DatabaseHandler databaseHandler;
    private DrawerLayout drawerGlob;
    private boolean isStartup = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawerGlob = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerGlob, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerGlob.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



//        txtNamePersist = (TextView) findViewById(R.id.editText);
        editTextName = (EditText)findViewById(R.id.editText);

        prefs = getSharedPreferences("view", 0);
        edits = prefs.edit();
        databaseHandler= DatabaseHandler.getInstance(this);


//        populateValues();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v){
        String nama = null;
        switch(v.getId()) {
            case R.id.savebutton:
                if (txtNamePersist.getText() != null) {
                    nama = txtNamePersist.getText().toString();
                    Log.i("onClick", nama);
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("WrongViewCast")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.i("currentView","view ID: "+ String.valueOf(getWindow().getCurrentFocus().getId()));

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        RelativeLayout contentMain = ((RelativeLayout) findViewById(R.id.content_main));
        if(isStartup) {
            contentMain.removeAllViews();
            isStartup = false;
        }

        if (id != R.id.nav_manage) {
            if (id == R.id.nav_camara) {

                fragmentClass = UserCredentialInput.class;
                contentMain.removeAllViews();

                // Handle the camera action
            } else if (id == R.id.nav_gallery) {
//            Log.i("onNavigation","in naf gallery");
                fragmentClass = UserListFragment.class;
                contentMain.removeAllViews();
            }
            try {
                fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
//            drawerGlob.setVisibility(View.GONE);
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();



        }else{
//            fragmentManager.popBackStack();
//            contentMain.
            fragmentManager.popBackStackImmediate();

        }

        setTitle(item.getTitle());

        drawerGlob = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerGlob.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("onFragmentInteraction ",uri.getEncodedFragment());
    }

    public void populateValues() {
        String persistedText = prefs.getString("txtVal", "Default Name");
        txtNamePersist.setText(persistedText);

    }
}
