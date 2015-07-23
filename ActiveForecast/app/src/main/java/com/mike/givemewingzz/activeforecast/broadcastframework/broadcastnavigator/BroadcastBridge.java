package com.mike.givemewingzz.activeforecast.broadcastframework.broadcastnavigator;

public interface BroadcastBridge {

    void registerForBroadcast(BroadcastReceiverFragment broadcastreceiverfragment, String intentFilter);

    void registerForBroadcasts(BroadcastReceiverFragment broadcastreceiverfragment);

    void unRegisterForBroadcast(String id, String broadcastFilter);

    void unRegisterForBroadcasts(String id);
}
