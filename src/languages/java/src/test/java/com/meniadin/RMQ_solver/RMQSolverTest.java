package com.meniadin.RMQ_solver;

import org.junit.jupiter.api.Test;

import com.meniadin.RMQ.RMQRunner;

public class RMQSolverTest {
    @Test
    public void testRMQSolver() {
        RMQRunner.main(new String[] { "input/01_data.txt", "input/01_queries.txt", "output" });
    }
}
