package com.example.formation.newadvancedlayout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.formation.newadvancedlayout.model.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;

import java.util.ArrayList;
import java.util.List;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User user;
    public final int LOGIN_REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Instanciation de l'utilisateur
        this.user = new User();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
           navigateToFragment(new FragmentB());
        } else if (id == R.id.nav_gallery) {
            navigateToFragment(new FragmentInscription());
        } else if (id == R.id.nav_slideshow) {
            navigateToFragment(new RandomUserFragment());
        } else if (id == R.id.nav_manage) {
            Intent mapIntention = new Intent(this, MapsActivity.class);
            startActivity(mapIntention);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Affichage du fragment passé en argument à la place
    //du composant identifié comme fragmentContainer
    private void navigateToFragment(Fragment targetFragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, targetFragment)
                .commit();
    }

    //Méthode permettant d'accéder à l'utilisateur
    public User getUser(){
        return this.user;
    }

    //Naviguer vers le fragment B
    public void goToFragmentB(){
        navigateToFragment(new FragmentB());
    }

    //Lancement de la procédure d'authentification
    public void onLogin(MenuItem item) {
        //Definition des fournisseurs d'authentification
        List<AuthUI.IdpConfig> providers = new ArrayList<>();
        providers.add(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER)
                        .build()
        );

        //Lancement de l'activité d'authentification
        startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                LOGIN_REQUESTCODE
        );
    }
}
