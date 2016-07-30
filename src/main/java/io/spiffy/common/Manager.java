package io.spiffy.common;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public abstract class Manager {

    protected final Logger logger;

    protected Manager() {
        logger = LoggerFactory.getLogger(getClass());
    }
}
