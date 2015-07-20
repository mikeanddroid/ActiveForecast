package com.mike.givemewingzz.activeforecast.applicationhandlers;

import com.mike.givemewingzz.activeforecast.baseclasses.CoreFragment;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbar.FragmentTransactionAnimation;

public interface FragmentTransactionHandler {

    void loadFragment(CoreFragment fragment, boolean replace, boolean addToBackStack);

    void loadFragment(CoreFragment fragment, boolean replace, boolean addToBackStack, String tag);

    void loadFragment(CoreFragment fragment, boolean replace, boolean addToBackStack, FragmentTransactionAnimation anim);

    void loadFragment(CoreFragment fragment, boolean replace, boolean addToBackStack, int animIn, int animOut, int animPopIn, int animPopout, String tag);

    void popBackStack();
}
