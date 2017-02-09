package com.example.aditya.gojek.injection.component;

import com.example.aditya.gojek.injection.scope.PerFragment;
import com.example.aditya.gojek.injection.module.FragmentModule;

import dagger.Subcomponent;

/**
 * Created by Aditya on 09-Feb-17.
 */

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

}