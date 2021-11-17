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

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }


    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    //Following tests added by Yoann
    @Test
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        int sizeBeforeSetSomeFavorites = favoriteNeighbours.size();
        for ( int i=1; i<4; i++) {neighbours.get(i).setIsFavorite(true);}
        favoriteNeighbours = service.getFavoriteNeighbours();
        assertEquals(favoriteNeighbours.size(), sizeBeforeSetSomeFavorites + 3);
    }

    @Test
    public void CreateNeighbourWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        int sizeBeforeAddingOneNeighbour = neighbours.size();
        Neighbour newNeighbour = new Neighbour(100, "", "", "",
                "", "", false,"");
        service.createNeighbour(newNeighbour);
        neighbours = service.getNeighbours();
        assertEquals(sizeBeforeAddingOneNeighbour+1,neighbours.size());
    }

    @Test
    public void setFavoriteNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
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
