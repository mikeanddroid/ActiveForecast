package com.mike.givemewingzz.activeforecast.navigationframework.navigation;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mike.givemewingzz.activeforecast.R;
import com.mike.givemewingzz.activeforecast.UI.TestClassFourActivity;
import com.mike.givemewingzz.activeforecast.UI.TestClassOneActivity;
import com.mike.givemewingzz.activeforecast.UI.TestClassThreeActivity;
import com.mike.givemewingzz.activeforecast.UI.TestClassTwoActivity;
import com.mike.givemewingzz.activeforecast.baseclasses.CoreActivity;

import java.util.ArrayList;
import java.util.List;

public class CoreNavigationActivity extends CoreActivity implements
        NavigationListener, NavigationHandler {

    private static final String TAG = CoreNavigationActivity.class
            .getSimpleName();

    private RecyclerView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationListAdapter navigationListAdapter;

    private int mCurrentSelectedPosition = 0;
    private int mNewSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_navigation);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        drawerList = (RecyclerView) findViewById(R.id.drawerList);
        drawerList.setLayoutManager(layoutManager);
        drawerList.setHasFixedSize(true);

        List<NavigationModel> list = setupNavigationList();
        navigationListAdapter = new NavigationListAdapter(list);
        navigationListAdapter.setNavigationListener(this);
        drawerList.setAdapter(navigationListAdapter);

    }

    private List<NavigationModel> setupNavigationList() {

        List<NavigationModel> navigationItems = new ArrayList<NavigationModel>();
        navigationItems.add(new NavigationModel("Active Weather",
                getResources().getDrawable(
                        R.drawable.abc_btn_radio_to_on_mtrl_000),
                getResources().getDrawable(
                        R.drawable.abc_btn_radio_to_on_mtrl_015)));

        navigationItems.add(new NavigationModel("Hourly Data", getResources()
                .getDrawable(R.drawable.abc_btn_radio_to_on_mtrl_000),
                getResources().getDrawable(
                        R.drawable.abc_btn_radio_to_on_mtrl_015)));

        navigationItems.add(new NavigationModel("Forecast", getResources()
                .getDrawable(R.drawable.abc_btn_radio_to_on_mtrl_000),
                getResources().getDrawable(
                        R.drawable.abc_btn_radio_to_on_mtrl_015)));

        navigationItems.add(new NavigationModel("Settings", getResources()
                .getDrawable(R.drawable.abc_btn_radio_to_on_mtrl_000), getResources()
                .getDrawable(R.drawable.abc_btn_radio_to_on_mtrl_015)));

        return navigationItems;
    }

    public ToggleNavigationAction setupToobar(Toolbar toolbar,
                                              final DrawerNavState drawerNavState, int backArrowDrawable) {

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                launchActivity();
                navigationListAdapter.unlock();
                unlockDrawer();
            }
        };

        // Set the icon to use as the back icon //
        if (drawerNavState == DrawerNavState.BACK) {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            actionBarDrawerToggle.setHomeAsUpIndicator(backArrowDrawable);
            // Add listener to the drawer toggle back button, pop the back stack
            // when it is clicked /
            actionBarDrawerToggle
                    .setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();
                        }
                    });
        } else if (drawerNavState == DrawerNavState.TOGGLE) {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        } else {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        }

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        return new ToggleNavigationAction(actionBarDrawerToggle, drawerNavState);

    }

    public void setSelectedNavigationIndex(int position) {
        ((NavigationListAdapter) drawerList.getAdapter())
                .selectPosition(position);
        mCurrentSelectedPosition = position;
        mNewSelectedPosition = position;
    }

    private void launchActivity() {
        if (mNewSelectedPosition != mCurrentSelectedPosition) {
            Intent intent;

            switch (mNewSelectedPosition) {
                case 1:
                    intent = new Intent(this, TestClassTwoActivity.class);

                    break;
                case 2:
                    intent = new Intent(this, TestClassThreeActivity.class);

                    break;
                case 3:
                    intent = new Intent(this, TestClassFourActivity.class);

                    break;
                default:
                    intent = new Intent(this, TestClassOneActivity.class);

            }
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }

    public static enum DrawerNavState {
        TOGGLE, BACK, NONE
    }

    public static class ToggleNavigationAction {

        public ActionBarDrawerToggle actionBarDrawerToggle;
        public DrawerNavState drawerNavState;

        public ToggleNavigationAction(
                ActionBarDrawerToggle actionBarDrawerToggle,
                DrawerNavState drawerNavState) {
            this.actionBarDrawerToggle = actionBarDrawerToggle;
            this.drawerNavState = drawerNavState;
        }

    }

    public void selectedNavItemClicked() {
        // Override in subclasses
    }

    public void closeDrawer() {
        drawerLayout.closeDrawer(drawerList);
    }

    public boolean isDrawerOpen() {
        boolean isDrawerOpen = drawerLayout != null && drawerLayout.isDrawerOpen(drawerList);
        return !(isDrawerOpen && !drawerLayout.isDrawerVisible(drawerLayout)) && isDrawerOpen;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mNewSelectedPosition = position;
        if (mNewSelectedPosition == mCurrentSelectedPosition) {
            selectedNavItemClicked();
        }

        lockDrawer();
        closeDrawer();

    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen()) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setBackArrowDrawable(int drawableID) {
        actionBarDrawerToggle.setHomeAsUpIndicator(drawableID);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void lockDrawer() {
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public void unlockDrawer() {
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private static final String SELECTED_NAVIGATION_DRAWER_POSITION = "SELECTED_NAVIGATION_DRAWER_POSITION";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_NAVIGATION_DRAWER_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public ToggleNavigationAction setupNavigationDrawer(Toolbar toolbar,
                                                        DrawerNavState drawerNavState, int backArrowDrawable) {
        return setupToobar(toolbar, drawerNavState, backArrowDrawable);
    }

    @Override
    public void setDrawerToggle(ToggleNavigationAction toggleNavigationAction) {
        actionBarDrawerToggle = toggleNavigationAction.actionBarDrawerToggle;
        // Set the listener defined above to the drawer, handles open and close of the drawer //
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        unlockDrawer();

    }

}
