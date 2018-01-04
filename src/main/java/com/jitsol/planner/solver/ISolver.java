package com.jitsol.planner.solver;

import com.jitsol.planner.datastore.ISolverDataStore;
import com.jitsol.planner.solver.exception.SolverException;

public interface ISolver {
    void Solve(ISolverDataStore dataStore) throws SolverException;
}
