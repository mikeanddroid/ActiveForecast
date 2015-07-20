
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

import com.example.givemewingzz.activeforecast.applicationhandlers.FragmentTransactionHandler;
import com.example.givemewingzz.activeforecast.services.broadcasthandler.BroadcastBridge;
import com.example.givemewingzz.activeforecast.services.broadcasthandler.BroadcastReceiverFragment;
import com.example.givemewingzz.activeforecast.services.broadcasthandler.FilteredBroadcastManger;
import com.example.givemewingzz.activeforecast.toolbar.utils.FragmentTransactionAnimation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

// Referenced classes of package com.example.givemewingz.activeforecast.mike.activeweather:
//            CoreFragment

public class CoreActivity extends AppCompatActivity
    implements FragmentTransactionHandler, BroadcastBridge
{

    private static final String TAG = com/example/givemewingz/activeforecast/mike/activeweather/CoreActivity.getSimpleName();
    private FilteredBroadcastManger filteredBroadcastManger;
    public BroadcastReceiver fragmentLifecycleReceiver;
    private int userDataChangedIndex;

    public CoreActivity()
    {
        userDataChangedIndex = 0;
        filteredBroadcastManger = new FilteredBroadcastManger();
        fragmentLifecycleReceiver = new BroadcastReceiver() {

            final CoreActivity this$0;

            public void onReceive(Context context, Intent intent)
            {
                byte byte0;
                context = intent.getAction();
                byte0 = -1;
                context.hashCode();
                JVM INSTR lookupswitch 4: default 52
            //                           -1586478827: 185
            //                           -1229571005: 199
            //                           -110495141: 157
            //                           1668882681: 171;
                   goto _L1 _L2 _L3 _L4 _L5
_L1:
                byte0;
                JVM INSTR tableswitch 0 3: default 84
            //                           0 213
            //                           1 213
            //                           2 213
            //                           3 213;
                   goto _L6 _L7 _L7 _L7 _L7
_L6:
                Log.d(CoreActivity.TAG, (new StringBuilder()).append("Base broadcast receiver received broadcast with type: ").append(context).toString());
_L9:
                for (context = filteredBroadcastManger.getFragmentsForFilter(intent.getAction()).iterator(); context.hasNext(); ((BroadcastReceiverFragment)context.next()).routeBroadcast(intent)) { }
                break; /* Loop/switch isn't completed */
_L4:
                if (context.equals("CURRENT_WEATHER_DATA"))
                {
                    byte0 = 0;
                }
                continue; /* Loop/switch isn't completed */
_L5:
                if (context.equals("FORECAST_WEATHER_DATA"))
                {
                    byte0 = 1;
                }
                continue; /* Loop/switch isn't completed */
_L2:
                if (context.equals("HISTORIC_WEATHER_DATA"))
                {
                    byte0 = 2;
                }
                continue; /* Loop/switch isn't completed */
_L3:
                if (context.equals("HOURLY_WEATHER_DATA"))
                {
                    byte0 = 3;
                }
                continue; /* Loop/switch isn't completed */
_L7:
                userDataChangedIndex = int i = ((StringBuilder) (this)).StringBuilder + 1;
                if (true) goto _L9; else goto _L8
_L8:
                return;
                if (true) goto _L1; else goto _L10
_L10:
            }

            
            {
                this$0 = CoreActivity.this;
                super();
            }
        };
    }

    private void addExtraIntentFilters()
    {
    }

    private void updateBroadcastReceiver()
    {
        Object obj = new HashSet();
        IntentFilter intentfilter = new IntentFilter();
        ((Set) (obj)).addAll(getActivityIntentFilters());
        ((Set) (obj)).addAll(filteredBroadcastManger.getActiveFilters());
        for (obj = ((Set) (obj)).iterator(); ((Iterator) (obj)).hasNext(); intentfilter.addAction((String)((Iterator) (obj)).next())) { }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fragmentLifecycleReceiver);
        LocalBroadcastManager.getInstance(this).registerReceiver(fragmentLifecycleReceiver, intentfilter);
    }

    public List getActivityIntentFilters()
    {
        return new ArrayList();
    }

    public int getUserDataChangedIndex()
    {
        return userDataChangedIndex;
    }

    public void loadFragment(CoreFragment corefragment, boolean flag, boolean flag1)
    {
        loadFragment(corefragment, flag, flag1, -1, -1, -1, -1, null);
    }

    public void loadFragment(CoreFragment corefragment, boolean flag, boolean flag1, int i, int j, int k, int l, 
            String s)
    {
        Object obj1 = getSupportFragmentManager().beginTransaction();
        Object obj;
        if (i != -1 && j != -1 && k != -1 && l != -1)
        {
            obj = ((FragmentTransaction) (obj1)).setCustomAnimations(i, j, k, j);
        } else
        {
            obj = obj1;
            if (i != -1)
            {
                obj = obj1;
                if (j != -1)
                {
                    obj = ((FragmentTransaction) (obj1)).setCustomAnimations(i, j);
                }
            }
        }
        obj1 = s;
        if (s == null)
        {
            obj1 = corefragment.getClass().getSimpleName();
        }
        if (flag)
        {
            corefragment = ((FragmentTransaction) (obj)).replace(0x7f0c0052, corefragment, ((String) (obj1)));
        } else
        {
            corefragment = ((FragmentTransaction) (obj)).add(0x7f0c0052, corefragment, ((String) (obj1)));
        }
        s = corefragment;
        if (flag1)
        {
            s = corefragment.addToBackStack(null);
        }
        s.commit();
    }

    public void loadFragment(CoreFragment corefragment, boolean flag, boolean flag1, FragmentTransactionAnimation fragmenttransactionanimation)
    {
        loadFragment(corefragment, flag, flag1, fragmenttransactionanimation.entrance, fragmenttransactionanimation.exit, fragmenttransactionanimation.popEntrance, fragmenttransactionanimation.popExit, null);
    }

    public void loadFragment(CoreFragment corefragment, boolean flag, boolean flag1, String s)
    {
        loadFragment(corefragment, flag, flag1, -1, -1, -1, -1, s);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            final CoreActivity this$0;

            public void onBackStackChanged()
            {
                Object obj = getSupportFragmentManager();
                if (obj != null)
                {
                    int i = ((FragmentManager) (obj)).getBackStackEntryCount();
                    if (i < ((FragmentManager) (obj)).getFragments().size())
                    {
                        obj = (Fragment)((FragmentManager) (obj)).getFragments().get(i);
                        if (obj != null)
                        {
                            i = 1;
                        } else
                        {
                            i = 0;
                        }
                        if (i & (obj instanceof CoreFragment))
                        {
                            ((CoreFragment)obj).onFragmentResumed();
                        }
                    }
                }
            }

            
            {
                this$0 = CoreActivity.this;
                super();
            }
        });
    }

    protected void onPause()
    {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fragmentLifecycleReceiver);
    }

    protected void onResume()
    {
        super.onResume();
        updateBroadcastReceiver();
    }

    public void popBackStack()
    {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        if (fragmentmanager.getBackStackEntryCount() > 0)
        {
            fragmentmanager.popBackStack();
            return;
        } else
        {
            finish();
            return;
        }
    }

    public void registerForBroadcast(BroadcastReceiverFragment broadcastreceiverfragment, String s)
    {
        filteredBroadcastManger.registerForSingleBroadcast(broadcastreceiverfragment, s);
        updateBroadcastReceiver();
    }

    public void registerForBroadcasts(BroadcastReceiverFragment broadcastreceiverfragment)
    {
        filteredBroadcastManger.registerForBroadcasts(broadcastreceiverfragment);
        updateBroadcastReceiver();
    }

    public void unRegisterForBroadcast(String s, String s1)
    {
        filteredBroadcastManger.unRegisterForSingleBroadcast(s, s1);
        updateBroadcastReceiver();
    }

    public void unRegisterForBroadcasts(String s)
    {
        filteredBroadcastManger.unRegisterForBroadcasts(s);
        updateBroadcastReceiver();
    }



/*
    static int access$002(CoreActivity coreactivity, int i)
    {
        coreactivity.userDataChangedIndex = i;
        return i;
    }

*/


/*
    static int access$004(CoreActivity coreactivity)
    {
        int i = coreactivity.userDataChangedIndex + 1;
        coreactivity.userDataChangedIndex = i;
        return i;
    }

*/


}
