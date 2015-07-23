package com.mike.givemewingzz.activeforecast.UI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mike.givemewingzz.activeforecast.R;
import com.mike.givemewingzz.activeforecast.applicationhandlers.FragmentTransactionHandler;
import com.mike.givemewingzz.activeforecast.baseclasses.CoreFragment;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbar.ToolbarOptions;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbar.ToolbarOptionsBuilder;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbarframework.exceptions.ToolbarInteractionException;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

public class TestClassFour extends CoreFragment {

    private static final String TAG = TestClassFour.class.getSimpleName();
    private FragmentTransactionHandler fragmentTransactionHandler;

    public TestClassFour() {
    }

    public static TestClassFour getInstance() {
        return new TestClassFour();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentTransactionHandler) {
            fragmentTransactionHandler = (FragmentTransactionHandler) activity;
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        View rootView = layoutinflater.inflate(R.layout.testclasslayoutfour, viewgroup, false);
        try {
            Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_actionbar);
            ToolbarOptions options = new ToolbarOptionsBuilder()
                    .setToolbar(toolbar)
                    .setBackDrawableID(R.drawable.appbar_back_themed)
                    .setAttachToNavDrawer(false)
                    .build(getActivity());

            setToolbar(options);
        } catch (ToolbarInteractionException e) {
            Log.e(TAG, ApplicationUtils.ApplicationConstants.LOG_TAG_SOMETHING_WENT_WRONG, e);
        }
        return rootView;
    }

}
