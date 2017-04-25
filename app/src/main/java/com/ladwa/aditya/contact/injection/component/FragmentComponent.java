package com.ladwa.aditya.contact.injection.component;

import com.ladwa.aditya.contact.injection.scope.PerFragment;
import com.ladwa.aditya.contact.injection.module.FragmentModule;

import dagger.Subcomponent;

/**
 * Created by Aditya on 09-Feb-17.
 */

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

}