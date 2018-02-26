package prvaaplikacija.moviesproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import prvaaplikacija.moviesproject.activities.LoginActivity;
import prvaaplikacija.moviesproject.adapters.MyPageAdapter;
import prvaaplikacija.moviesproject.adapters.MyPagePeopleAdapter;
import prvaaplikacija.moviesproject.fragments.FavouritesFragment;
import prvaaplikacija.moviesproject.fragments.NowPlayingFragment;
import prvaaplikacija.moviesproject.fragments.PeoplesFragment;
import prvaaplikacija.moviesproject.fragments.PopularFragment;
import prvaaplikacija.moviesproject.fragments.TopRatedFragment;
import prvaaplikacija.moviesproject.fragments.UpcomingFragment;
import prvaaplikacija.moviesproject.preferences.PreferenceManager;

public class Explorer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    int flag=0;
    public MyPageAdapter pageAdapter;
    public MyPagePeopleAdapter peopleAdapter;

    public String token;
    public String sessionid;
    MenuItem menuItem;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);


        token = PreferenceManager.getToken(this);
        sessionid = PreferenceManager.getSessionId(this);






//        Intent i = getIntent();
//        if(i.hasExtra("EXTRA_TOKEN") && i.hasExtra("EXTRA_SESSION_ID")) {
//            token = i.getStringExtra("EXTRA_TOKEN");
//            sessionid=i.getStringExtra("EXTRA_SESSION_ID");
//        }
//
        if(flag==0) {
            setUpViewPager();
            tabLayout.setupWithViewPager(pager);
        } else if(flag==1) {
            setUpPeoplePager();
        } else if(flag==2) {
            setFavouritesPager();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        menuItem = navigationView.getMenu().findItem(R.id.login);

        if(sessionid.isEmpty()) {
            menuItem.setTitle("Login");
        } else {
            menuItem.setTitle("Logout");
        }

    }



    public void setUpViewPager() {
        pageAdapter = new MyPageAdapter(this.getSupportFragmentManager());
        pageAdapter.addFragment(new PopularFragment(),"popular    ");
        pageAdapter.addFragment(new TopRatedFragment(),"top rated  ");
        pageAdapter.addFragment(new UpcomingFragment(),"upcoming   ");
        pageAdapter.addFragment(new NowPlayingFragment(),"now playing");
        pager.setAdapter(pageAdapter);
        flag=0;
    }

    public void setUpPeoplePager() {
        peopleAdapter = new MyPagePeopleAdapter(this.getSupportFragmentManager());

        peopleAdapter.addFragment(new PeoplesFragment());

        pager.setAdapter(peopleAdapter);
        flag = 1;

    }

    public void setFavouritesPager() {
        pageAdapter = new MyPageAdapter(this.getSupportFragmentManager());
        pageAdapter.addFragment(new FavouritesFragment(),"Favourites");
        pager.setAdapter(pageAdapter);
        flag=2;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.explore) {
            setUpViewPager();
            tabLayout.setVisibility(View.VISIBLE);
            tabLayout.setupWithViewPager(pager);
            toolbar.setTitle("Explorer");


        } else if (id == R.id.favourites) {
            toolbar.setTitle("Favourites");
            setFavouritesPager();
        } else if (id == R.id.rated) {

        } else if (id == R.id.watchlist) {

        } else if (id == R.id.people) {
            setUpPeoplePager();
            toolbar.setTitle("People");
            tabLayout.setVisibility(View.INVISIBLE);
        } else if (id == R.id.login) {
            if (menuItem.getTitle().equals("Logout")) {
                PreferenceManager.removeSessionId(this);
                PreferenceManager.removeToken(this);
                sessionid="";
                token="";
                menuItem.setTitle("Login");

            } else {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
