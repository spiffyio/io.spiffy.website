package io.spiffy.common;

import javax.inject.Named;

import org.apache.log4j.Logger;

@Named
public abstract class Manager {

    protected final Logger logger;
    
    protected Manager() {
        this.logger = Logger.getLogger(getClass());
    }
}
