package com.meniadin.RMQ;

import java.io.*;
import java.util.AbstractMap.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

public class RMQRunner {

    public static void main(String[] args) {
        try {
            var runner = new InnerRMQRunner<Integer>(Integer::parseInt);
            runner.run(args);
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

}

class InnerRMQRunner<T extends Comparable<T>> {

    Function<String, T> parser;
    private ArrayList<T> data = new ArrayList<T>();
    private ArrayList<SimpleEntry<Integer, Integer>> queries = new ArrayList<SimpleEntry<Integer, Integer>>();
    private ArrayList<RMQSolver<T>> rmqList = new ArrayList<RMQSolver<T>>();

    public InnerRMQRunner(Function<String, T> parser) {
        this.parser = parser;
    }

    public void run(String[] args) throws Exception {
        importProblems(args);
        addSolvers();
        runSolvers(args[2]);
    }

    private void importProblems(String[] args) throws Exception {
        importData(args[0]);
        importQueries(args[1]);
        validateQueries();
    }

    private void importData(String filePath) throws Exception {
        try (var reader = new FileReader(filePath)) {
            var bufReader = new BufferedReader(reader);
            var line = bufReader.readLine();
            while (line != null) {
                data.add(parser.apply(line));
                line = bufReader.readLine();
            }
        }
    }

    private void importQueries(String filePath) throws Exception {
        try (var reader = new BufferedReader(new FileReader(filePath))) {
            var line = reader.readLine();
            while (line != null) {
                try (var scanner = new Scanner(line)) {
                    scanner.findAll("\\s*(\\d+)\\s*,\\s*(\\d+)\\s*")
                            .forEach(result -> {
                                queries.add(new SimpleEntry<Integer, Integer>(
                                        Integer.parseInt(result.group(1)),
                                        Integer.parseInt(result.group(2))));
                            });
                }
                line = reader.readLine();
            }
        }
    }

    private void validateQueries() {
        for (var query : queries) {
            if (query.getKey() > query.getValue())
                throw new IllegalArgumentException(
                        "Invalid query: start index " + query.getKey() + " is larger than end index "
                                + query.getValue());
            if (query.getValue() >= data.size())
                throw new IllegalArgumentException(
                        "Invalid query: end index " + query.getValue()
                                + " designates an element outside the array of size "
                                + data.size());
        }
    }

    private void addSolvers() throws Exception {
        rmqList.add(new RMQ1<T>(new String("RMQ1")));
        rmqList.add(new RMQ2<T>(new String("RMQ2")));
        rmqList.add(new RMQ3<T>(new String("RMQ3")));
        rmqList.add(new RMQ4<T>(new String("RMQ4")));
    }

    private void runSolvers(String outputDirName) throws Exception {
        new File(outputDirName).mkdirs();
        for (var rmqSolver : rmqList) {
            rmqSolver.preProcess(data);
            try (var outputFile = new FileWriter(outputDirName + "/" + rmqSolver.getName() + ".txt")) {
                for (var query : queries)
                    outputFile.write(rmqSolver.query(query.getKey(), query.getValue()).toString() + "\n");
            }
        }
    }

}
