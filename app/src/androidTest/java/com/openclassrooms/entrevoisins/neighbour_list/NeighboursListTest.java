
package com.openclassrooms.entrevoisins.neighbour_list;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.ClickOnItem;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static final int ITEMS_COUNT = 12;
    private static final int FAVORITE_ITEMS_COUNT = 2;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mApiService;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        mApiService = DI.getNewInstanceApiService();
        for (Neighbour neighbour: DummyNeighbourGenerator.DUMMY_NEIGHBOURS) {neighbour.setIsFavorite(false);}
        DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0).setIsFavorite(true);
        DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(1).setIsFavorite(true);
        assertThat(mActivity, notNullValue());

    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test

    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        //Modified by Yoann: added withContentDescription("firstPage")) + allOf to check what is ()
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("firstPage")))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursListTabMyNeighbours_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 1 of Neighbour tab (N??0)
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), withContentDescription("firstPage")))
                .check(matches(isDisplayed()));
        //check the number of items displayed
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), withContentDescription("firstPage")))
                .check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), withContentDescription("firstPage")))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        // Then : the number of element is 11 of Neighbour tab (N??1)
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), withContentDescription("firstPage")))
                .check(withItemCount(ITEMS_COUNT - 1));
        //switch on tab 2 - Favorites
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), withContentDescription("firstPage")))
                .perform(swipeLeft());
        //Check tab 2 is displayed
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("secondPage")))
                .check(matches(isDisplayed()));
        //check the number of items displayed
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("secondPage")))
                .check(withItemCount(FAVORITE_ITEMS_COUNT));
        //click on delete icon of first item
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("secondPage")))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // Then : the number of item should be 1
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("secondPage")))
                .check(withItemCount(FAVORITE_ITEMS_COUNT - 1));
    }

    @Test
    public void getNeighbourDetailsWithSuccess() {
        // Given : We click on the first item of the RecyclerView, Tab 0
        // When perform a click a neighbour item n?? 2
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("firstPage")))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, new ClickOnItem()));
        // Then : the NeighbourDetailsActivity start
        onView(ViewMatchers.withId(R.id.activity_neighbour_details_id))
                .check(matches(isDisplayed()));
    }

    @Test
    public void getCorrectNameInNeighbourDetailsWithSuccess() {
        // Given : We click on the first item of the RecyclerView, Tab 0
        // When perform a click a neighbour item n?? 0
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("firstPage")))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ClickOnItem()));
        // Then : the NeighbourDetailsActivity start
        onView(ViewMatchers.withId(R.id.activity_neighbour_details_id))
                .check(matches(isDisplayed()));
        //check the name
        onView(ViewMatchers.withId(R.id.activity_neighbour_cardviews_name))
                .check(matches(withText("Caroline")))
                .perform(pressBack());
        //go to favorites tab
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), withContentDescription("firstPage"))).perform(swipeLeft());
        // When perform a click a neighbour item n?? 1
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("secondPage")))
                        .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ClickOnItem()));
        // Then : the NeighbourDetailsActivity start
        onView(ViewMatchers.withId(R.id.activity_neighbour_details_id))
                .check(matches(isDisplayed()));
        //check the name
        onView(ViewMatchers.withId(R.id.activity_neighbour_cardviews_name))
                .check(matches(withText("Caroline")));
    }

    @Test
    public void FavoriteTabOfMyNeighbourList_shouldShowOnlyFavoriteNeighbour() {
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), withContentDescription("firstPage")))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, new ClickOnItem()));
        onView(ViewMatchers.withId(R.id.activity_details_neighbour_favorite_button))
                .perform(click());
        onView(withId(R.id.activity_details_neighbour_collapsing_toolbar))
                .perform(pressBack());
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("firstPage")))
                .perform(swipeLeft());
        //Check one item has been added to favorite
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("secondPage")))
                .check(withItemCount(FAVORITE_ITEMS_COUNT+1));
        // When perform a click a neighbour item n?? 2
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),withContentDescription("secondPage")))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, new ClickOnItem()));
        // Then : the NeighbourDetailsActivity start
        onView(ViewMatchers.withId(R.id.activity_neighbour_details_id))
                .check(matches(isDisplayed()));
        //check the name
        onView(ViewMatchers.withId(R.id.activity_neighbour_cardviews_name))
                .check(matches(withText("Elodie")));
    }
}