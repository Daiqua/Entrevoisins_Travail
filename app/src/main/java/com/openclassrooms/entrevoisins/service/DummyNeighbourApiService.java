package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours;

    //added by Yoann to test generateFavoriteNeighbours
    private List<Neighbour> favoriteNeighbours;





    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours = DummyNeighbourGenerator.generateNeighbours();
    }


    //added by Yoann
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        favoriteNeighbours = DummyNeighbourGenerator.generateNeighbours();

            return favoriteNeighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }
}

