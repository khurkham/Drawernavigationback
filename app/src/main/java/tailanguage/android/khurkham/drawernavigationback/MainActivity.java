package tailanguage.android.khurkham.drawernavigationback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private Import importFragment = new Import();
    private Gallery galleryFragment = new Gallery();
    private Slideshow slideShowFragment = new Slideshow();
    private Tools toolsFragment = new Tools();
    private Share shareFragment = new Share();
    private Send sendFragment = new Send();

    private Fragment fragmentCurrent;
    private int currentMenuItem;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//           // w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
//            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }



        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XXXXXXXX" , "click");
                onBackPressed();
            }
        });

        drawer.addDrawerListener(toggle);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);


        currentMenuItem = R.id.nav_camera;//default first item
        this.getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        if (fragmentCurrent instanceof Import) {
                            currentMenuItem = R.id.nav_camera;
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            toggle.setDrawerIndicatorEnabled(true);
                        } else if (fragmentCurrent instanceof Gallery) {
                            currentMenuItem = R.id.nav_gallery;
                            toggle.setDrawerIndicatorEnabled(false);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        } else if (fragmentCurrent instanceof Slideshow) {
                            currentMenuItem = R.id.nav_slideshow;
                            toggle.setDrawerIndicatorEnabled(false);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        } else if (fragmentCurrent instanceof Tools) {
                            currentMenuItem = R.id.nav_manage;
                            toggle.setDrawerIndicatorEnabled(false);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        } else if (fragmentCurrent instanceof Share) {
                            currentMenuItem = R.id.nav_share;
                            toggle.setDrawerIndicatorEnabled(false);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        } else {
                            currentMenuItem = R.id.nav_send;
                            toggle.setDrawerIndicatorEnabled(false);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        }
                        navigationView.setCheckedItem(currentMenuItem);
                    }
                });

        if (savedInstanceState == null) {
            addFragment(importFragment);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentCurrent.equals(importFragment)) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                replaceFragment(importFragment);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragmentSelected = null;
        int id = item.getItemId();
        if (id == currentMenuItem) {
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }
        if (id == R.id.nav_camera) {
            fragmentSelected = importFragment;
        } else if (id == R.id.nav_gallery) {
            fragmentSelected = galleryFragment;
        } else if (id == R.id.nav_slideshow) {
            fragmentSelected = slideShowFragment;
        } else if (id == R.id.nav_manage) {
            fragmentSelected = toolsFragment;
        } else if (id == R.id.nav_share) {
            fragmentSelected = shareFragment;
        } else if (id == R.id.nav_send) {
            fragmentSelected = sendFragment;
        }
        currentMenuItem = id;
        replaceFragment(fragmentSelected);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addFragment(Fragment fragment) {
        fragmentCurrent = fragment;
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, fragment).commit();
        navigationView.setCheckedItem(currentMenuItem);
    }

    private void replaceFragment(Fragment fragment) {
        fragmentCurrent = fragment;
        navigationView.setCheckedItem(currentMenuItem);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}