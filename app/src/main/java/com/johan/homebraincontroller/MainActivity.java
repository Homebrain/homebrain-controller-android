package com.johan.homebraincontroller;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity
        implements  NavigationDrawerFragment.NavigationDrawerCallbacks,
                    DebugFragment.OnFragmentInteractionListener, OverviewFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        Toolbar toolbar = new Toolbar(this);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        ApplicationController.getInstance();
    }

    public void onFragmentInteraction(Uri uri){}

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment targetfragment;
        switch (position){
            default:
            case 0:
                targetfragment = OverviewFragment.newInstance();
                break;
            case 1:
                targetfragment = DebugFragment.newInstance();
                break;
            case 2:
                targetfragment = SettingsFragment.newInstance();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, targetfragment)//PlaceholderFragment.newInstance(position + 1))
                .commit();
    }
}
