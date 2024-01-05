package com.meniadin.RMQ;

import java.util.ArrayList;

public abstract class RMQSolver<T extends Comparable<T>> {

    private String name;

    RMQSolver(String solverName) {
        this.name = solverName;
    }

    public String getName() {
        return name;
    }

    abstract public void preProcess(ArrayList<T> data);

    abstract T query(int startIndex, int endIndex);

}
