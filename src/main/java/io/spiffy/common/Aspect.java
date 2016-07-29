package io.spiffy.common;

import org.aspectj.lang.annotation.Pointcut;

public abstract class Aspect extends Manager {

    @Pointcut("within(io.spiffy..*)")
    protected void spiffy() {
    }
}