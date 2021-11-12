package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    //added by Yoann: new... to instantiate before clear()
    private List<Neighbour> neighbours;
    Neighbour mNeighbour;

    //added by Yoann to test generateFavoriteNeighbours
    private List<Neighbour> favoriteNeighbours = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        //added by Yoann: clear() to not set favorite in not updated list
        neighbours = DummyNeighbourGenerator.generateNeighbours();
        return neighbours;
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

    //added by Yoann
    public void setFavorite(Neighbour neighbour) {
        mNeighbour=neighbour;
        if (mNeighbour.getIsFavorite()) {mNeighbour.setIsFavorite(false);
        }else {mNeighbour.setIsFavorite(true);}
    }

}

