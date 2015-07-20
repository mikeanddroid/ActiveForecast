package com.mike.givemewingzz.activeforecast.broadcastnavigator;

import android.content.Intent;

import java.util.Set;

public interface BroadcastReceiverFragment {

    String getFragmentID();

    Set<String> getIntentFilters();

    void receiveBroadcast(Intent intent);

    void receiveFailedBroadcast(Intent intent);

    void routeBroadcast(Intent intent);
}
