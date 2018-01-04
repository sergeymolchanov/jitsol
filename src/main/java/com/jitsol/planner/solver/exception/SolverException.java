package com.jitsol.planner.solver.exception;

public class SolverException extends Exception {
    public SolverException(String message) {
        super(message);
    }
    public SolverException(String message, Throwable cause) {
        super(message, cause);
    }
    public SolverException(Throwable cause) {
        super(cause);
    }
}
