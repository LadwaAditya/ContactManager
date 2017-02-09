package com.example.aditya.gojek.injection.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Aditya on 09-Feb-17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPersistent {
}
