package com.mike.givemewingzz.activeforecast.broadcastframework.broadcastnavigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FilteredBroadcastManger {

    private final Map<String, Map<String, BroadcastReceiverFragment>> filterMap = Collections.synchronizedMap(new HashMap<String, Map<String, BroadcastReceiverFragment>>());

    /**
     * Returns the set of active filters
     *
     * @return - set of active intent filters
     */
    public Set<String> getActiveFilters() {
        synchronized (filterMap) {
            return filterMap.keySet();
        }
    }

    /**
     * Returns a list of BroadcastReceiverFragment that are registered for the passed in
     * intent filter.
     *
     * @param intentFilter - filter to get registered fragments for
     * @return - list of fragments registered to the specified filter
     */
    public List<BroadcastReceiverFragment> getFragmentsForFilter(String intentFilter) {
        synchronized (filterMap) {
            if (filterMap.containsKey(intentFilter)) {
                Map<String, BroadcastReceiverFragment> fragmentMap = filterMap.get(intentFilter);

                return new ArrayList<>(fragmentMap.values());
            } else {
                return new ArrayList<>();
            }
        }
    }

    /**
     * Registers all the filters for the specified fragment.
     *
     * @param fragment - fragment to specify filters for.
     */
    public void registerForBroadcasts(BroadcastReceiverFragment fragment) {
        Set<String> intentFilters = fragment.getIntentFilters();
        for (String intentFilter : intentFilters) {
            registerForSingleBroadcast(fragment, intentFilter);
        }
    }

    /**
     * Allows a BroadcastReceiverFragment to register for a single intent filter
     *
     * @param fragment     - fragment to register
     * @param intentFilter - filter to register for the fragment
     */
    public void registerForSingleBroadcast(BroadcastReceiverFragment fragment, String intentFilter) {
        String fragmentID = fragment.getFragmentID();
        synchronized (filterMap) {
            Map<String, BroadcastReceiverFragment> fragmentMap;
            if (filterMap.containsKey(intentFilter)) {
                fragmentMap = filterMap.get(intentFilter);
                if (!fragmentMap.containsKey(fragmentID)) {
                    fragmentMap.put(fragmentID, fragment);
                    filterMap.put(intentFilter, fragmentMap);
                }
            } else {
                fragmentMap = new HashMap<>();
                fragmentMap.put(fragmentID, fragment);
                filterMap.put(intentFilter, fragmentMap);
            }
        }
    }

    /**
     * Unregisters all intent filters for the given fragment id.
     *
     * @param id
     */
    public void unRegisterForBroadcasts(String id) {
        synchronized (filterMap) {
            Iterator<Map.Entry<String, Map<String, BroadcastReceiverFragment>>> iter = filterMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Map<String, BroadcastReceiverFragment>> entry = iter.next();
                Map<String, BroadcastReceiverFragment> fragmentMap = filterMap.get(entry.getKey());
                fragmentMap.remove(id);
                if (fragmentMap.isEmpty()) {
                    iter.remove();
                }
            }
        }
    }

    /**
     * Unregisters the specified intent filter for the given fragment id.
     *
     * @param id           - id of fragment to remove filter for
     * @param intentFilter - filter to remove
     */
    public void unRegisterForSingleBroadcast(String id, String intentFilter) {
        synchronized (filterMap) {
            if (filterMap.containsKey(intentFilter)) {
                Map<String, BroadcastReceiverFragment> fragmentMap = filterMap.get(intentFilter);
                fragmentMap.remove(id);
                if (fragmentMap.isEmpty()) {
                    filterMap.remove(intentFilter);
                }
            }
        }
    }
}
