package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours;

    //added by Yoann to test generateFavoriteNeighbours
    private List<Neighbour> favoriteNeighbours = new ArrayList<>();





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
        favoriteNeighbours.clear();
            for ( Neighbour neighbour : neighbours) {
                if (neighbour.getIsFavorite()){
                    favoriteNeighbours.add(neighbour);
                }

            }
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

    //TODO: ajouter la m"thodes setfavoris
}

