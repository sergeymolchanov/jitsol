package com.jitsol.planner.manager;

import com.jitsol.planner.datastore.DataStore;
import com.jitsol.planner.datastore.model.Resource;
import com.jitsol.planner.loader.ILoader;
import com.jitsol.planner.loader.LoaderException;
import com.jitsol.planner.solver.ISolver;
import com.jitsol.planner.solver.exception.InputDataException;
import com.jitsol.planner.solver.exception.SolverException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class Manager {

    @Autowired
    private ISolver solver;

    @Autowired
    private ILoader loader;

    @Autowired
    private DataStore dataStore;

    private ApplicationState state = ApplicationState.Started;

    public void LoadData() throws InputDataException {
        changeState(ApplicationState.Started, ApplicationState.Import);

        try {
            loader.prepare();
            loader.load(dataStore);
        } catch (LoaderException e) {
            throw new InputDataException(e);
        }

        dataStore.fillCaches();

        changeState(ApplicationState.Import, ApplicationState.Imported);
    }

    public void Solve() throws SolverException {
        changeState(ApplicationState.Imported, ApplicationState.Process);

        solver.Solve(dataStore);

        changeState(ApplicationState.Process, ApplicationState.Processed);
    }

    public void UploadData() {
        changeState(ApplicationState.Processed, ApplicationState.Export);
        System.out.println("TODO: Do upload data");
        changeState(ApplicationState.Export, ApplicationState.Exported);
    }

    @Scheduled(fixedDelay = 5000000)
    public void Test() throws SolverException {
        LoadData();
        Solve();
        UploadData();
    }

    private void changeState(ApplicationState oldState, ApplicationState newState) {
        checkState(oldState);
        System.out.println(this.state + " -> " + newState);
        this.state = newState;
    }

    private void checkState(ApplicationState mustState) {
        if (this.state != mustState) {
            throw new RuntimeException("Invalid state");
        }
    }

    public enum ApplicationState {
        Started,
        Import,
        Imported,
        Process,
        Processed,
        Export,
        Exported
    }
}