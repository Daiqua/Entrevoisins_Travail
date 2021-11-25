package com.openclassrooms.entrevoisins.utils;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import com.openclassrooms.entrevoisins.R;
import org.hamcrest.Matcher;

public class ClickOnItem implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View itemView = view.findViewById(R.id.fragment_neighbour_id);
        // Maybe check for null
        itemView.performClick();
    }
}