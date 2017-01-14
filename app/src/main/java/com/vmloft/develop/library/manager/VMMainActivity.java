package com.vmloft.develop.library.manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.vmloft.develop.library.tools.VMBaseActivity;

public class VMMainActivity extends VMBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Activity mActivity;

    private Toolbar mToolbar;

    private View mImageView;
    private View jumpBtn;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        setupWindowTransitions();

        mToolbar = (Toolbar) findViewById(R.id.widget_toolbar);
        mToolbar.setTitle("MainActivity");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        jumpBtn = findViewById(R.id.btn_scene_anim_pair);
        mImageView = findViewById(R.id.img_shared_element);

        findViewById(R.id.btn_custom_anim).setOnClickListener(viewListener);
        findViewById(R.id.btn_scale_up_anim).setOnClickListener(viewListener);
        findViewById(R.id.btn_thumbnail_scale_up_anim).setOnClickListener(viewListener);
        findViewById(R.id.btn_scene_anim).setOnClickListener(viewListener);
        findViewById(R.id.btn_scene_anim_pair).setOnClickListener(viewListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 设置界面过度效果
     */
    protected void setupWindowTransitions() {
        Transition enterTransition = TransitionInflater.from(mActivity)
                .inflateTransition(R.transition.transition_slide_enter);
        Transition exitTransition = TransitionInflater.from(mActivity)
                .inflateTransition(R.transition.transition_slide_exit);
        Transition explode = TransitionInflater.from(mActivity)
                .inflateTransition(R.transition.transition_explode);

        getWindow().setEnterTransition(explode);
        getWindow().setExitTransition(explode);
    }

    private View.OnClickListener viewListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            Intent intent = new Intent(mActivity, VMTransitionOneActivity.class);
            ActivityOptionsCompat options = null;
            switch (v.getId()) {
                case R.id.btn_custom_anim:
                    options = ActivityOptionsCompat.makeCustomAnimation(mActivity,
                            R.transition.transition_slide_enter,
                            R.transition.transition_slide_exit);
                    break;
                case R.id.btn_scale_up_anim:
                    options = ActivityOptionsCompat.makeScaleUpAnimation(mImageView,
                            mImageView.getWidth() / 2, mImageView.getHeight() / 2,
                            mImageView.getWidth(), mImageView.getHeight());
                    break;
                case R.id.btn_thumbnail_scale_up_anim:
                    Bitmap bitmap = mImageView.getDrawingCache();
                    options =
                            ActivityOptionsCompat.makeThumbnailScaleUpAnimation(mImageView, bitmap,
                                    mImageView.getWidth() / 2, mImageView.getHeight() / 2);
                    break;
                case R.id.btn_scene_anim:
                    View view = findViewById(R.id.img_shared_element);
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view,
                            view.getTransitionName());
                    break;
                case R.id.btn_scene_anim_pair:
                    Pair<View, String> imgPair =
                            Pair.create(mImageView, mImageView.getTransitionName());
                    Pair<View, String> btnPair = Pair.create(jumpBtn, jumpBtn.getTransitionName());

                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, imgPair,
                            btnPair);
                    break;
                default:
                    options = ActivityOptionsCompat.makeTaskLaunchBehind();
                    break;
            }
            startActivity(intent, options.toBundle());
        }
    };

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
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

    @SuppressWarnings("StatementWithEmptyBody") @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            onFinish();
        }
    }
}