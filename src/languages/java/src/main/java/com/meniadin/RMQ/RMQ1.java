package com.meniadin.RMQ;

import java.util.ArrayList;

public class RMQ1<T extends Comparable<T>> extends RMQSolver<T> {

    private ArrayList<T> data;

    RMQ1(String solverName) {
        super(solverName);
    }

    @Override
    public void preProcess(ArrayList<T> data) {
        this.data = data;
    }

    @Override
    public T query(int startIndex, int endIndex) {
        var min = data.get(startIndex);

        for (int idx = startIndex + 1; idx <= endIndex; ++idx) {
            if (data.get(idx).compareTo(min) < 0)
                min = data.get(idx);
        }

        return min;
    }
}
