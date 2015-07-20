// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.mike.givemewingzz.activeforecast.broadcastnavigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.example.givemewingz.activeforecast.mike.services.broadcasthandler:
//            BroadcastReceiverFragment

public class FilteredBroadcastManger
{

    private final Map filterMap = Collections.synchronizedMap(new HashMap());

    public FilteredBroadcastManger()
    {
    }

    public Set getActiveFilters()
    {
        Set set;
        synchronized (filterMap)
        {
            set = filterMap.keySet();
        }
        return set;
        exception;
        map;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public List getFragmentsForFilter(String s)
    {
label0:
        {
            synchronized (filterMap)
            {
                if (!filterMap.containsKey(s))
                {
                    break label0;
                }
                s = new ArrayList(((Map)filterMap.get(s)).values());
            }
            return s;
        }
        s = new ArrayList();
        map;
        JVM INSTR monitorexit ;
        return s;
        s;
        map;
        JVM INSTR monitorexit ;
        throw s;
    }

    public void registerForBroadcasts(BroadcastReceiverFragment broadcastreceiverfragment)
    {
        for (Iterator iterator = broadcastreceiverfragment.getIntentFilters().iterator(); iterator.hasNext(); registerForSingleBroadcast(broadcastreceiverfragment, (String)iterator.next())) { }
    }

    public void registerForSingleBroadcast(BroadcastReceiverFragment broadcastreceiverfragment, String s)
    {
        String s1 = broadcastreceiverfragment.getFragmentID();
        Map map = filterMap;
        map;
        JVM INSTR monitorenter ;
        if (!filterMap.containsKey(s))
        {
            break MISSING_BLOCK_LABEL_82;
        }
        Map map1 = (Map)filterMap.get(s);
        if (!map1.containsKey(s1))
        {
            map1.put(s1, broadcastreceiverfragment);
            filterMap.put(s, map1);
        }
_L2:
        return;
        HashMap hashmap = new HashMap();
        hashmap.put(s1, broadcastreceiverfragment);
        filterMap.put(s, hashmap);
        if (true) goto _L2; else goto _L1
_L1:
        broadcastreceiverfragment;
        map;
        JVM INSTR monitorexit ;
        throw broadcastreceiverfragment;
    }

    public void unRegisterForBroadcasts(String s)
    {
        Map map = filterMap;
        map;
        JVM INSTR monitorenter ;
        Iterator iterator = filterMap.entrySet().iterator();
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            Object obj = (Map.Entry)iterator.next();
            obj = (Map)filterMap.get(((Map.Entry) (obj)).getKey());
            ((Map) (obj)).remove(s);
            if (((Map) (obj)).isEmpty())
            {
                iterator.remove();
            }
        } while (true);
        break MISSING_BLOCK_LABEL_96;
        s;
        map;
        JVM INSTR monitorexit ;
        throw s;
        map;
        JVM INSTR monitorexit ;
    }

    public void unRegisterForSingleBroadcast(String s, String s1)
    {
        synchronized (filterMap)
        {
            if (filterMap.containsKey(s1))
            {
                Map map1 = (Map)filterMap.get(s1);
                map1.remove(s);
                if (map1.isEmpty())
                {
                    filterMap.remove(s1);
                }
            }
        }
        return;
        s;
        map;
        JVM INSTR monitorexit ;
        throw s;
    }
}
