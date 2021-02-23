package com.google;

import java.util.ArrayList;
import java.util.List;

class Result {
    int teamId;
    List<Integer> pizzas;

    public Result(int teamId) {
        this.teamId = teamId;
        pizzas = new ArrayList<>();
    }
}