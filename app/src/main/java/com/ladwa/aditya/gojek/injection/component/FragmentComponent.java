package com.ladwa.aditya.gojek.injection.component;

import com.ladwa.aditya.gojek.injection.scope.PerFragment;
import com.ladwa.aditya.gojek.injection.module.FragmentModule;

import dagger.Subcomponent;

/**
 * Created by Aditya on 09-Feb-17.
 */

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

}