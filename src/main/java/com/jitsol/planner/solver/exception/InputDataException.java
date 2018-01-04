package com.jitsol.planner.solver.exception;

public class InputDataException extends SolverException {
    public InputDataException(String message) {
        super(message);
    }
    public InputDataException(String message, Throwable cause) {
        super(message, cause);
    }
    public InputDataException(Throwable cause) {
        super(cause);
    }
}
