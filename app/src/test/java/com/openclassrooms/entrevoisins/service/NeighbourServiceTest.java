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

    //following tests added by Yoann
    //TODO: redo with only few neighbour as set as favorite
    @Test
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        for ( Neighbour neighbour : neighbours) {neighbour.setIsFavorite(true);}
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        List<Neighbour> expectedNeighbours = neighbours;
        assertThat(favoriteNeighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void CreateNeighbourWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        int a = neighbours.size();
        Neighbour newNeighbour = new Neighbour(100, "", "", "",
                "", "", false,"");
        service.createNeighbour(newNeighbour);
        neighbours = service.getNeighbours();
        int b = neighbours.size();
        assertEquals(a+1,b);

    }

    @Test
    //TODO: clear fav - set one neighbour fav - check fav list size
    public void setFavoriteNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        neighbours.clear();
        neighbours.add(new Neighbour(100, "", "", "",
                "", "", false,""));
        service.setFavorite(neighbours.get(0));
        assertEquals(neighbours.size(),1);
        assertEquals(neighbours.get(0).getIsFavorite(),true);
        assertEquals(neighbours.get(0).getId(),100);
        service.setFavorite(neighbours.get(0));
        assertEquals(neighbours.get(0).getIsFavorite(),false);
        assertEquals(neighbours.get(0).getId(),100);
    }
}
