package com.hissab.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hissab.HomePage.ui.account.Account;
import com.hissab.HomePage.ui.home.HomeFragment;
import com.hissab.HomePage.ui.patient.patient_details;
import com.hissab.HomePage.ui.product.product;
import com.hissab.HomePage.ui.spent.spent;
import com.hissab.Login.Login;
import com.hissab.R;
import com.hissab.staticValue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;

public class homePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    staticValue staticValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        staticValue=new staticValue(homePage.this);
        com.hissab.staticValue.getDefaultValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                homePage.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Glide.with(this)
                .load(user.getPhotoUrl())
                .into((CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_imageView));

        TextView name,email;
        name=navigationView.getHeaderView(0).findViewById(R.id.nav_name);
        name.setText(user.getDisplayName());
        email=navigationView.getHeaderView(0).findViewById(R.id.nav_email);
        email.setText(user.getEmail());
        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        int id=item.getItemId();
        Fragment fragment = null;
        if(id==R.id.nav_signout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(homePage.this, Login.class));
        }else if(id==R.id.nav_home){
            fragment=new HomeFragment();
        }else if(id==R.id.nav_account){
            fragment=new Account();

        }else if(id==R.id.nav_product){
            fragment=new product();
        }else if(id==R.id.nav_patien){
            fragment=new patient_details();
        }else if(id==R.id.nav_spent){
            fragment=new spent();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return loadFragment(fragment);
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}