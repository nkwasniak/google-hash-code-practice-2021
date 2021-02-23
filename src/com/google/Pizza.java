package com.google;

import java.util.ArrayList;
import java.util.List;

class Pizza implements Comparable {
    int id;
    List<String> ingredients;

    public Pizza(int id) {
        this.id = id;
        this.ingredients = new ArrayList<>();
    }

    @Override
    public int compareTo(Object o) {
        Pizza other = (Pizza) o;
        List<String> repeated = new ArrayList<>(this.ingredients);
        repeated.retainAll(other.ingredients);
        if(ingredients.size() < other.ingredients.size()){
            return 1;
        }

        if(ingredients.size() > other.ingredients.size()){
            return -1;
        }

        return 0;
    }
}