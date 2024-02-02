package com.meniadin.RMQ;

import java.util.ArrayList;

public abstract class RMQSolver<T extends Comparable<T>> {

    private String name;

    RMQSolver(String solverName) {
        this.name = solverName;
    }

    public final String getName() {
        return name;
    }

    public abstract void preProcess(ArrayList<T> data);

    public abstract T query(int startIndex, int endIndex);

}
