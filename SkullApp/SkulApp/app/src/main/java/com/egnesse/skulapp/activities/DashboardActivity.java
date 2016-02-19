package com.egnesse.skulapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.egnesse.skulapp.R;
import com.egnesse.skulapp.ui.blur_image.AndroidImage;
import com.egnesse.skulapp.ui.blur_image.GaussianBlur;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;
    private GaussianBlur gaussianBlur;
    private MenuItem previousMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        gaussianBlur = new GaussianBlur();
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        AndroidImage androidImage = new AndroidImage(icon);
        androidImage = gaussianBlur.process(androidImage);

        View headerView = navigationView.getHeaderView(0);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), androidImage.getImage());

        headerView.setBackgroundDrawable(bitmapDrawable);
        ((ImageView)headerView.findViewById(R.id.profile_image)).setImageBitmap(icon);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(previousMenuItem != null)
                    previousMenuItem.setChecked(false);

                menuItem.setCheckable(true);
                menuItem.setChecked(true);

                previousMenuItem = menuItem;

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        ContentFragment fragment = new ContentFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        return true;

                    /*case R.id.starred:
                        Toast.makeText(getApplicationContext(), "Stared Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.sent_mail:
                        Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drafts:
                        Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.allmail:
                        Toast.makeText(getApplicationContext(), "All Mail Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(), "Trash Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.spam:
                        Toast.makeText(getApplicationContext(), "Spam Selected", Toast.LENGTH_SHORT).show();
                        return true;*/
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        navigationView.getMenu().getItem(0).setChecked(true);
        ContentFragment fragment = new ContentFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



       /* drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.closeDrawer(drawer);



        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.dashboard)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.news_feed)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.teachers)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.student)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.calender)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.events)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.gallery)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.classes)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.forms)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.lost_n_found)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.advertise)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));
        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.contact_us)).setImage(ContextCompat.getDrawable(this, R.mipmap.abc_ic_drawer_dashboard)));


       *//* drawer.addItem(new DrawerItem().setImage(ContextCompat.getDrawable(this, R.drawable.cat))
                        .setTextPrimary(getString(R.string.lorem_ipsum_short)).setTextSecondary(getString(R.string.lorem_ipsum_long)));

        drawer.addDivider();

        drawer.addItem(new DrawerItem().setRoundedImage((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.splash))
                        .setTextPrimary(getString(R.string.lorem_ipsum_short)).setTextSecondary(getString(R.string.lorem_ipsum_long)));

        drawer.addItem(new DrawerHeaderItem().setTitle(getString(R.string.lorem_ipsum_short)));

        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.lorem_ipsum_short))
        );

        drawer.addItem(new DrawerItem().setRoundedImage((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.cat), DrawerItem.SMALL_AVATAR)
                        .setTextPrimary(getString(R.string.lorem_ipsum_short)).setTextSecondary(getString(R.string.lorem_ipsum_long), DrawerItem.THREE_LINE));*//*

        drawer.selectItem(1);

        drawer.setOnItemClickListener(new DrawerItem.OnItemClickListener() {
            @Override
            public void onClick(DrawerItem item, long id, int position) {
                drawer.selectItem(position);
                drawerLayout.closeDrawer(drawer);
            }
        });


        Drawable drawable = new BitmapDrawable(getResources(), androidImage.getImage());

        drawer.addProfile(new DrawerProfile().setId(1).setRoundedAvatar((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.cat))
                        .setBackground(drawable).setName(getString(R.string.lorem_ipsum_short)).setDescription(getString(R.string.lorem_ipsum_long))
        );


        drawer.setOnProfileClickListener(new DrawerProfile.OnProfileClickListener() {
            @Override
            public void onClick(DrawerProfile profile, long id) {
                Toast.makeText(DashboardActivity.this, "Clicked profile *" + id, Toast.LENGTH_SHORT).show();
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                String url = "https://github.com/HeinrichReimer/material-drawer";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}