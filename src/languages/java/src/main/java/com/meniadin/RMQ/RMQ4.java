package com.meniadin.RMQ;

import java.util.ArrayList;

public class RMQ4<T extends Comparable<T>> extends RMQSolver<T> {

    private ArrayList<ArrayList<T>> db;
    private ArrayList<Integer> powersOf2;
    private ArrayList<Integer> log2Floor;

    RMQ4(String solverName) {
        super(solverName);
    }

    @Override
    public void preProcess(ArrayList<T> data) {
        var log2ArrayCapacity = data.size() + 1;
        this.powersOf2 = new ArrayList<Integer>();
        this.log2Floor = new ArrayList<Integer>(log2ArrayCapacity);
        this.db = new ArrayList<ArrayList<T>>(data.size());

        this.log2Floor.add(0); // first entry isn't used
        for (int idx = 1, length = 1, value = 0; idx < log2ArrayCapacity; length *= 2, ++value) {
            powersOf2.add(length);
            for (int innerIdx = 0; (innerIdx < length) && (idx < log2ArrayCapacity); ++innerIdx, ++idx) {
                this.log2Floor.add(value);
            }
        }

        for (int idx = 0; idx < data.size(); ++idx) {
            var iterations = log2Floor.get(data.size() - idx) + 1;
            var currentArrayList = new ArrayList<T>(iterations);
            db.add(currentArrayList);
        }
        for (int idx = data.size() - 1; idx >= 0; --idx) {
            var iterations = log2Floor.get(data.size() - idx) + 1;
            var currentArrayList = db.get(idx);
            T previousValue = data.get(idx);
            for (int length = 1, iteration = 0; iteration < iterations; length *= 2, ++iteration) {
                if (iteration == 0) {
                    currentArrayList.add(previousValue);
                } else {
                    var opt1 = previousValue;
                    var opt2 = db.get(idx + (length / 2)).get(iteration - 1);
                    currentArrayList.add(min(opt1, opt2));
                }
            }
        }
    }

    @Override
    public T query(int startIndex, int endIndex) {
        int length = endIndex - startIndex + 1;
        T part1 = db.get(startIndex).get(log2Floor.get(length));
        T part2 = db.get(endIndex - powersOf2.get(log2Floor.get(length)) + 1).get(log2Floor.get(length));
        return min(part1, part2);
    }

    private T min(T lhs, T rhs) {
        if (lhs.compareTo(rhs) < 0)
            return lhs;
        else
            return rhs;
    }
}
