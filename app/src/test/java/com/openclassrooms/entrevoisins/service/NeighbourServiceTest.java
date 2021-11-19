package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;
    private final int neighboursTab = 0;
    private final int favoriteTab = 1;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }


    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours(neighboursTab);
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours(neighboursTab).get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours(neighboursTab).contains(neighbourToDelete));
        assertFalse(service.getNeighbours(favoriteTab).contains(neighbourToDelete));
    }

    //Following tests added by Yoann
    @Test
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours(neighboursTab);
        List<Neighbour> favoriteNeighbours = service.getNeighbours(favoriteTab);
        int sizeBeforeSetSomeFavorites = favoriteNeighbours.size();
        for ( int i=1; i<4; i++) {neighbours.get(i).setIsFavorite(true);}
        favoriteNeighbours = service.getNeighbours(favoriteTab);
        assertEquals(favoriteNeighbours.size(), sizeBeforeSetSomeFavorites + 3);
    }

    @Test
    public void CreateNeighbourWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours(neighboursTab);
        int sizeBeforeAddingOneNeighbour = neighbours.size();
        Neighbour newNeighbour = new Neighbour(100, "", "", "",
                "", "", false,"");
        service.createNeighbour(newNeighbour);
        neighbours = service.getNeighbours(neighboursTab);
        assertEquals(sizeBeforeAddingOneNeighbour+1,neighbours.size());
    }

    @Test
    public void setFavoriteNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours(neighboursTab);
        neighbours.clear();
        neighbours.add(new Neighbour(100, "", "", "",
                "", "", false,""));
        //switch favorite from false to true
        service.setFavorite(neighbours.get(0));
        //check the list contains only this neighbour
        assertEquals(neighbours.get(0).getId(),100);
        //check Favorite status
        assertEquals(neighbours.get(0).getIsFavorite(),true);
        ///switch favorite from true to false and check
        service.setFavorite(neighbours.get(0));
        assertEquals(neighbours.get(0).getIsFavorite(),false);
    }
}
