package com.jitsol.planner.loader;

import com.jitsol.planner.datastore.ILoaderDataStore;

public interface ILoader {
    void prepare() throws LoaderException;
    void load(ILoaderDataStore dataStore) throws LoaderException;
}