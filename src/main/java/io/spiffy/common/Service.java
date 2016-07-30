package io.spiffy.common;

public abstract class Service<E, R extends Repository<E>> extends Manager {

    protected final R repository;

    protected Service(final R repository) {
        this.repository = repository;
    }

}
