package com.example.aditya.gojek.data;

import com.example.aditya.gojek.data.remote.GoJekService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Aditya on 09-Feb-17.
 */

@Singleton
public class DataManager {

    private final GoJekService mGoJekService;

    @Inject
    public DataManager(GoJekService mGoJekService) {
        this.mGoJekService = mGoJekService;
    }
}
