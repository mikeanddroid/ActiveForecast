package com.mike.givemewingzz.activeforecast.baseclasses;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.mike.givemewingzz.activeforecast.R;
import com.mike.givemewingzz.activeforecast.broadcastnavigator.BroadcastBridge;
import com.mike.givemewingzz.activeforecast.broadcastnavigator.BroadcastReceiverFragment;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbarframework.exceptions.ToolbarInteractionException;
import com.mike.givemewingzz.activeforecast.navigationframework.navigation.CoreNavigationActivity;
import com.mike.givemewingzz.activeforecast.navigationframework.navigation.NavigationHandler;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbarframework.utils.ToolbarOptions;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CoreFragment extends Fragment
    implements Toolbar.OnMenuItemClickListener, BroadcastReceiverFragment
{
    private class ToolbarAnimator extends Animation
    {

        private int dp;
        private boolean hide;

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            ViewGroup.MarginLayoutParams toolbarBufferLayoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            if (hide) {
                toolbarBufferLayoutParams.height = (int) (dp * (1 - interpolatedTime));
            } else {
                toolbarBufferLayoutParams.height = (int) (dp * (interpolatedTime));
            }
            toolbar.setLayoutParams(toolbarBufferLayoutParams);
        }

        public void hide()
        {
            hide = true;
            toolbar.startAnimation(this);
        }

        public void show()
        {
            hide = false;
            toolbar.startAnimation(this);
        }

        public ToolbarAnimator() {
            dp = (int) getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
            this.setDuration(250);
        }
    }


    private static final String TAG = CoreFragment.class.getSimpleName();
    public BroadcastBridge broadcastBridge;
    private CoreNavigationActivity.ToggleNavigationAction drawerToggle;
    public CoreNavigationActivity.ToggleNavigationAction drawerToggleState;
    public Menu menu;
    public Toolbar toolbar;
    private ToolbarAnimator toolbarAnimator;
    private int toolbarDefaultHeight;
    private String uniqueFragmentID;
    private int userDataChangedIndex;
    private UserDataState userDataState;

    public CoreFragment()
    {
        userDataChangedIndex = 0;
    }

    public void checkIfUserDataChanged()
    {
        if (userDateStateChanged())
        {
            userDataChanged();
            setLastUserActionTimestamp();
        }
    }

    public String generateUniqueID()
    {
        return UUID.randomUUID().toString();
    }

    public String getFragmentID()
    {
        return uniqueFragmentID;
    }

    public Set getIntentFilters()
    {
        return new HashSet();
    }

    public int getToolbarHeight()
        throws ToolbarInteractionException
    {
        if (toolbar != null)
        {
            return toolbar.getHeight();
        } else
        {
            throw new ToolbarInteractionException("The toolbar is currently null, call setToolbar first.");
        }
    }

    public void hideToolbar(boolean flag)
        throws ToolbarInteractionException
    {
        if (toolbar != null)
        {
            if (flag)
            {
                toolbarAnimator.hide();
                return;
            } else
            {
                ViewGroup.MarginLayoutParams marginlayoutparams = (ViewGroup.MarginLayoutParams)toolbar.getLayoutParams();
                marginlayoutparams.height = 0;
                toolbar.setLayoutParams(marginlayoutparams);
                return;
            }
        } else
        {
            throw new ToolbarInteractionException("The toolbar is currently null, call setToolbar first.");
        }
    }

    public View initializeView(int i, LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        return layoutinflater.inflate(i, viewgroup, false);
    }

    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof BroadcastBridge)
        {
            broadcastBridge = (BroadcastBridge)activity;
            uniqueFragmentID = generateUniqueID();
            broadcastBridge.registerForBroadcasts(this);
        }
        if (activity instanceof UserDataState)
        {
            userDataState = (UserDataState)activity;
        }
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
    }

    public void onCreateOptionsMenu(Menu menu1, MenuInflater menuinflater)
    {
        super.onCreateOptionsMenu(menu1, menuinflater);
        menu1.clear();
    }

    public void onDetach()
    {
        super.onDetach();
    }

    public void onFragmentResumed()
    {
        if (getActivity() != null && (getActivity() instanceof NavigationHandler) && drawerToggleState != null)
        {
            ((NavigationHandler)getActivity()).setDrawerToggle(drawerToggleState);
        }
        checkIfUserDataChanged();
    }

    public boolean onMenuItemClick(MenuItem menuitem)
    {
        return false;
    }

    public void onResume()
    {
        super.onResume();
    }

    public void receiveBroadcast(Intent intent)
    {
    }

    public void receiveFailedBroadcast(Intent intent)
    {
    }

    public void routeBroadcast(Intent intent)
    {
label0:
        {
            String s = intent.getAction();
            if (s != null)
            {
                Log.d(TAG, (new StringBuilder()).append("ACTION TYPE : ").append(s).toString());
                if (intent.getExtras() != null && intent.getExtras().containsKey("REQUEST_SUCCESS_KEY"))
                {
                    if (!intent.getExtras().getBoolean("REQUEST_SUCCESS_KEY"))
                    {
                        break label0;
                    }
                    receiveBroadcast(intent);
                }
            }
            return;
        }
        receiveFailedBroadcast(intent);
    }

    public void setLastUserActionTimestamp()
    {
        if (userDataState != null)
        {
            userDataChangedIndex = userDataState.getUserDataChangedIndex();
        }
    }

    public void setNavDrawableID(int i)
    {
        toolbar.setNavigationIcon(i);
    }

    public void setToolbar(ToolbarOptions options) throws ToolbarInteractionException {
        this.toolbar = options.getToolbar();
        toolbarAnimator = new ToolbarAnimator();
        toolbarDefaultHeight = toolbar.getHeight();

        // If there is a menuID inflate that menu and setup listener //
        if (options.getMenuID() != -1) {
            toolbar.inflateMenu(options.getMenuID());
            menu = toolbar.getMenu();
            toolbar.setOnMenuItemClickListener(this);
        }

        // Set title and background //
        setToolbarTitle(options.getTitle());
        setToolbarBackground(options.getToolbarBackground());

        /*
         * If this toolbar is supposed to integrate with the nav drawer call setupNavDrawer with the
         * required fields.  Otherwise setup the standalone back arrow.
         */
        if (options.attachToNavDrawer()) {
            if (getActivity() != null && getActivity() instanceof NavigationHandler) {
                NavigationHandler navDrawerHandler = ((NavigationHandler) getActivity());
                drawerToggleState = navDrawerHandler.setDrawerToggle(toolbar, options.getDrawerNavState(), options.getNavDrawerBackDrawableID());
            }
        } else if (options.getNavDrawableID() != -1) {
            toolbar.setNavigationIcon(options.getNavDrawableID());
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() != null) {
                        getActivity().onBackPressed();
                    }
                }
            });
        }

        // If we are not supposed to attach to the navdrawer lock the drawer, otherwise unlock //
        if (getActivity() != null && getActivity() instanceof NavigationHandler) {
            NavigationHandler navDrawerHandler = ((NavigationHandler) getActivity());
            if (!options.attachToNavDrawer()) {
                navDrawerHandler.lockDrawer();
            } else {
                navDrawerHandler.unlockDrawer();
            }
        }
    }

    public void setToolbarBackground(Drawable drawable)
        throws ToolbarInteractionException
    {
        if (toolbar != null)
        {
            toolbar.setBackground(drawable);
            return;
        } else
        {
            throw new ToolbarInteractionException("The toolbar is currently null, call setToolbar first.");
        }
    }

    public void setToolbarTitle(String s)
        throws ToolbarInteractionException
    {
        if (toolbar != null)
        {
            toolbar.setTitle(s);
            return;
        } else
        {
            throw new ToolbarInteractionException("The toolbar is currently null, call setToolbar first.");
        }
    }

    public void showToolbar(boolean flag)
        throws ToolbarInteractionException
    {
        if (toolbar != null)
        {
            if (flag)
            {
                toolbarAnimator.show();
                return;
            } else
            {
                ViewGroup.MarginLayoutParams marginlayoutparams = (ViewGroup.MarginLayoutParams)toolbar.getLayoutParams();
                marginlayoutparams.height = toolbarDefaultHeight;
                toolbar.setLayoutParams(marginlayoutparams);
                return;
            }
        } else
        {
            throw new ToolbarInteractionException("The toolbar is currently null, call setToolbar first.");
        }
    }

    public void userDataChanged()
    {
    }

    public boolean userDateStateChanged()
    {
        return userDataState == null || userDataState.getUserDataChangedIndex() != userDataChangedIndex;
    }

}
