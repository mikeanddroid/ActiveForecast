package com.mike.givemewingzz.activeforecast.baseclasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mike.givemewingzz.activeforecast.R;
import com.mike.givemewingzz.activeforecast.applicationhandlers.FragmentTransactionHandler;
import com.mike.givemewingzz.activeforecast.broadcastnavigator.BroadcastBridge;
import com.mike.givemewingzz.activeforecast.broadcastnavigator.BroadcastReceiverFragment;
import com.mike.givemewingzz.activeforecast.broadcastnavigator.FilteredBroadcastManger;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbar.FragmentTransactionAnimation;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoreActivity extends AppCompatActivity
        implements FragmentTransactionHandler, BroadcastBridge {

    private static final String TAG = CoreActivity.class.getSimpleName();
    private int userDataChangedIndex = 0;
    private FilteredBroadcastManger filteredBroadcastManger = new FilteredBroadcastManger();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager manager = getSupportFragmentManager();
                        if (manager != null) {
                            int backStackEntryCount = manager
                                    .getBackStackEntryCount();
                            if (backStackEntryCount < manager.getFragments()
                                    .size()) {
                                Fragment fragment = manager.getFragments().get(
                                        backStackEntryCount);
                                if (fragment != null
                                        & fragment instanceof CoreFragment) {
                                    ((CoreFragment) fragment)
                                            .onFragmentResumed();
                                }
                            }
                        }
                    }
                });

    }

    public int getUserDataChangedIndex() {
        return userDataChangedIndex;
    }

    /**
     * Base BroadcastReceiver allows the activity a change to handle the broadcast and then passes
     * the intent along to any interested fragments.
     */
    BroadcastReceiver fragmentLifecycleReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String broadcastType = intent.getAction();

            Log.d(CoreActivity.TAG, (new StringBuilder()).append("Base broadcast receiver received broadcast with type: ").append(broadcastType).toString());

            switch (broadcastType) {

                case ApplicationUtils.Receivers.CURRENT_WEATHER_DATA:
                case ApplicationUtils.Receivers.FORECAST_WEATHER_DATA:
                case ApplicationUtils.Receivers.HISTORIC_WEATHER_DATA:
                case ApplicationUtils.Receivers.HOURLY_WEATHER_DATA:
                    userDataChangedIndex = ++userDataChangedIndex;
                    break;
                default:
                    Log.d(TAG, "Base broadcast receiver received broadcast with type: " + broadcastType);

            }

            // Pass the intent on to any attached fragments that are interested in this broadcast type //
            for (BroadcastReceiverFragment broadcastReceiverFragment : filteredBroadcastManger.getFragmentsForFilter(intent.getAction())) {
                broadcastReceiverFragment.routeBroadcast(intent);
            }

        }
    };

    private void addExtraIntentFilters() {
    }

    /**
     * Collects a list of intent filters from all registered BroadcastReceiverFragments and combines
     * them into a single IntentFilter to register with the broadcast receiver.
     */
    private void updateBroadcastReceiver() {
        Set<String> filters = new HashSet<>();
        IntentFilter combinedFilter = new IntentFilter();
        filters.addAll(getActivityIntentFilters());
        filters.addAll(filteredBroadcastManger.getActiveFilters());

        for (String intentFilter : filters) {
            combinedFilter.addAction(intentFilter);
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(fragmentLifecycleReceiver);
        LocalBroadcastManager.getInstance(this).registerReceiver(fragmentLifecycleReceiver, combinedFilter);
    }

    public List getActivityIntentFilters() {
        return new ArrayList();
    }

    /**
     * Used to change the currently displayed fragment for an activity.
     *
     * @param fragment       - the new fragment to display
     * @param replace        - if true the fragment will replace the current fragment, else it will be added.
     * @param addToBackStack - if true the fragment will be added to the back stack
     * @param animIn         - used to customize the entrance / exit animations of this transaction
     * @param animOut
     * @param animPopIn
     * @param animPopout
     */
    @Override
    public void loadFragment(CoreFragment fragment, boolean replace, boolean addToBackStack, int animIn, int animOut, int animPopIn, int animPopout, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (animIn != -1 && animOut != -1 && animPopIn != -1 && animPopout != -1) {
            transaction = transaction.setCustomAnimations(animIn, animOut, animPopIn, animOut);
        } else if (animIn != -1 && animOut != -1) {
            transaction = transaction.setCustomAnimations(animIn, animOut);
        }

        if (tag == null) {
            tag = fragment.getClass().getSimpleName();
        }

        if (replace) {
            transaction = transaction.replace(R.id.container, fragment, tag);
        } else {
            transaction = transaction.add(R.id.container, fragment, tag);
        }

        if (addToBackStack) {
            transaction = transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void loadFragment(CoreFragment corefragment, boolean flag, boolean flag1) {
        loadFragment(corefragment, flag, flag1, -1, -1, -1, -1, null);
    }

    @Override
    public void loadFragment(CoreFragment corefragment, boolean flag, boolean flag1, FragmentTransactionAnimation fragmenttransactionanimation) {
        loadFragment(corefragment, flag, flag1, fragmenttransactionanimation.entrance, fragmenttransactionanimation.exit, fragmenttransactionanimation.popEntrance, fragmenttransactionanimation.popExit, null);
    }

    @Override
    public void loadFragment(CoreFragment corefragment, boolean flag, boolean flag1, String s) {
        loadFragment(corefragment, flag, flag1, -1, -1, -1, -1, s);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fragmentLifecycleReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBroadcastReceiver();
    }

    @Override
    public void popBackStack() {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        if (fragmentmanager.getBackStackEntryCount() > 0) {
            fragmentmanager.popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void registerForBroadcast(BroadcastReceiverFragment broadcastreceiverfragment, String s) {
        filteredBroadcastManger.registerForSingleBroadcast(broadcastreceiverfragment, s);
        updateBroadcastReceiver();
    }

    @Override
    public void registerForBroadcasts(BroadcastReceiverFragment broadcastreceiverfragment) {
        filteredBroadcastManger.registerForBroadcasts(broadcastreceiverfragment);
        updateBroadcastReceiver();
    }

    @Override
    public void unRegisterForBroadcast(String s, String s1) {
        filteredBroadcastManger.unRegisterForSingleBroadcast(s, s1);
        updateBroadcastReceiver();
    }

    @Override
    public void unRegisterForBroadcasts(String s) {
        filteredBroadcastManger.unRegisterForBroadcasts(s);
        updateBroadcastReceiver();
    }

}
