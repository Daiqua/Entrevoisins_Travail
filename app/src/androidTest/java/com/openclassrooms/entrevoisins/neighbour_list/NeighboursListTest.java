
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.ClickOnItem;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.

        //TODO: use the tab position // issue due to the use of the same fragment on both tab item - give a description for each frag
        //Modified by Yoann: added withContentDescription("firstPage")) + allOf to check what is ()
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("firstPage")))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        //TODO: add delete on fav tab
        //Modified by Yoann: list_neighbours replaced by main_content

        // Given : We remove the element at position 2
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("firstPage"))).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("firstPage")))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("firstPage"))).check(withItemCount(ITEMS_COUNT - 1));
    }

    //TODO
    @Test
    public void getNeighbourDetailsWithSuccess() {
        // Given : We click on the first item of the RecyclerView, Tab 0
        // When perform a click on a delete icon
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("firstPage")))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, new ClickOnItem()));
        // Then : the NeighbourDetailsActivity start
        onView(ViewMatchers.withId(R.id.activity_neighbour_details_id))
                .check(matches(isDisplayed()));

    }

    //TODO
    @Test
    public void getCorrectNameInNeighbourDetailsWithSuccess() {
    }


    //TODO
    @Test
    public void FavoriteTabOfMyNeighbourList_shouldShowOnlyFavoriteNeighbour() {
    }

    //TODO check if not fav neighbour is correctly set as favorite after click on the button
    @Test
    public void () {
    }
}