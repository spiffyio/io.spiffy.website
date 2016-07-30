package io.spiffy.common;

@org.springframework.stereotype.Service
public abstract class Service<E, R extends Repository<E>> extends Manager {

    protected final R repository;

    protected Service(final R repository) {
        this.repository = repository;
    }

}
