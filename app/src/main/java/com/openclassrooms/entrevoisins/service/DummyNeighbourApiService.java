package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {


    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    //new specific list for favorite neighbours
    private List<Neighbour> favoriteNeighbours = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    //added by Yoann
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        //clear() needed to refresh the list if actions done on neighbours list.
        //example: delete a favorite neighbour from the neighbours list. he will still appears on the favoriteNeighbours
        //and existing favorite neighbours will be added after each initList()
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

    public void setFavorite(Neighbour neighbour) {

        if (neighbour.getIsFavorite()) {neighbour.setIsFavorite(false);
        }else {neighbour.setIsFavorite(true);}
    }

}

