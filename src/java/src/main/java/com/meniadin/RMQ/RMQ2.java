package com.meniadin.RMQ;

import java.util.ArrayList;

public class RMQ2<T extends Comparable<T>> extends RMQSolver<T> {

    private ArrayList<ArrayList<T>> db;

    RMQ2(String solverName) {
        super(solverName);
    }

    @Override
    public void preProcess(ArrayList<T> data) {
        this.db = new ArrayList<ArrayList<T>>(data.size());

        for (int startIndex = 0, size = data.size(); startIndex < data.size(); ++startIndex)
            this.db.add(new ArrayList<T>(size - startIndex));

        for (int startIndex = data.size() - 1; startIndex >= 0; --startIndex) {
            this.db.get(startIndex).add(data.get(startIndex));
            for (int endIndex = startIndex + 1; endIndex < data.size(); ++endIndex) {
                var current = query(startIndex, startIndex);
                var otherRange = query(startIndex + 1, endIndex);
                this.db.get(startIndex)
                        .add(current.compareTo(otherRange) < 0 ? current : otherRange);
            }
        }
    }

    @Override
    public T query(int startIndex, int endIndex) {
        int distance = endIndex - startIndex;
        return this.db.get(startIndex).get(distance);
    }
}
