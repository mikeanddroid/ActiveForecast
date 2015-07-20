package com.mike.givemewingzz.activeforecast.navigationframework.toolbar;

/**
 * Wrapper class to hold animation which can be used to customize how fragments enter and exit view.
 * Entrance - Used for new fragment entering view
 * Exit - Used for fragment being replaced if replace used instead of add
 * PopEntrance - Used for bottom fragment coming back into view after top fragment popped.
 * PopExit - Used for top fragment leaving view.
 */
public class FragmentTransactionAnimation {
    public int entrance = -1;
    public int exit = -1;
    public int popEntrance = -1;
    public int popExit = -1;

    public FragmentTransactionAnimation(int entrance, int exit) {
        this.entrance = entrance;
        this.exit = exit;
    }

    public FragmentTransactionAnimation(int entrance, int exit, int popEntrance, int popExit) {
        this.entrance = entrance;
        this.exit = exit;
        this.popEntrance = popEntrance;
        this.popExit = popExit;
    }
}