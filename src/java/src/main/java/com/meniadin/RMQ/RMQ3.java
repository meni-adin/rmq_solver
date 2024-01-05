package com.meniadin.RMQ;

import java.util.ArrayList;
import java.lang.Math;

public class RMQ3<T extends Comparable<T>> extends RMQSolver<T> {

    private ArrayList<T> data;
    private ArrayList<T> db;
    private int sectionsCount;
    private int normalSectionLength;
    private int lastSectionLength;

    RMQ3(String solverName) {
        super(solverName);
    }

    @Override
    public void preProcess(ArrayList<T> data) {
        this.data = data;
        normalSectionLength = (int) Math.ceil(Math.sqrt(data.size()));
        sectionsCount = (data.size() / normalSectionLength) + ((data.size() % normalSectionLength) == 0 ? 0 : 1);
        lastSectionLength = data.size() - (normalSectionLength * (sectionsCount - 1));

        db = new ArrayList<T>();
        for (int sectionIdx = 0; sectionIdx < (sectionsCount - 1); ++sectionIdx)
            db.add(minInRange(sectionIdx * normalSectionLength, normalSectionLength));
        db.add(minInRange((sectionsCount - 1) * normalSectionLength, lastSectionLength));
    }

    @Override
    public T query(int startIndex, int endIndex) {
        T min, startMin, middleMin, endMin;

        if (startIndex == endIndex)
            return data.get(startIndex);

        if (sectionIdx(startIndex) == sectionIdx(endIndex))
            if ((endIndex - startIndex + 1 == normalSectionLength) ||
                    ((sectionIdx(startIndex) == sectionsCount - 1) && (endIndex - startIndex + 1 == lastSectionLength)))
                return db.get(sectionIdx(startIndex));
            else
                return minInRange(startIndex, endIndex - startIndex + 1);

        if ((startIndex % normalSectionLength) == 0)
            startMin = db.get(sectionIdx(startIndex));
        else
            startMin = minInRange(startIndex, ((sectionIdx(startIndex) + 1) * normalSectionLength) - startIndex);

        if (((endIndex + 1) % normalSectionLength) == 0)
            endMin = db.get(sectionIdx(endIndex));
        else
            endMin = minInRange(sectionIdx(endIndex) * normalSectionLength,
                    endIndex - (sectionIdx(endIndex) * normalSectionLength) + 1);

        if (startMin.compareTo(endMin) < 0)
            min = startMin;
        else
            min = endMin;

        if (sectionIdx(endIndex) - sectionIdx(startIndex) == 1)
            return min;

        middleMin = db.get(sectionIdx(startIndex) + 1);
        for (int idx = 1; idx < (sectionIdx(endIndex) - sectionIdx(startIndex) - 1); ++idx)
            if (db.get(sectionIdx(startIndex) + idx).compareTo(middleMin) < 0)
                middleMin = db.get(sectionIdx(startIndex) + idx);

        if (middleMin.compareTo(min) < 0)
            min = middleMin;

        return min;
    }

    private T minInRange(int startIdx, int length) {
        T min = data.get(startIdx);

        for (int idx = 0; idx < length; ++idx) {
            T current = data.get(startIdx + idx);
            if (current.compareTo(min) < 0)
                min = current;
        }

        return min;
    }

    private int sectionIdx(int idx) {
        return idx / normalSectionLength;
    }
}
