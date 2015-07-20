package com.mike.givemewingzz.activeforecast.toolbar.utils;

public class FragmentTransactionAnimation {

    public int entrance;
    public int exit;
    public int popEntrance;
    public int popExit;

    public FragmentTransactionAnimation(int i, int j) {
        entrance = -1;
        exit = -1;
        popEntrance = -1;
        popExit = -1;
        entrance = i;
        exit = j;
    }

    public FragmentTransactionAnimation(int i, int j, int k, int l) {
        entrance = -1;
        exit = -1;
        popEntrance = -1;
        popExit = -1;
        entrance = i;
        exit = j;
        popEntrance = k;
        popExit = l;
    }
}
