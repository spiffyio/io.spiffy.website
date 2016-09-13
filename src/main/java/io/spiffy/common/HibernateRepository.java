package io.spiffy.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public abstract class HibernateRepository<E> extends Repository<E> {
    public static final boolean EXCLUDE_ARCHIVED = true;
    public static final boolean INCLUDE_ARCHIVED = !EXCLUDE_ARCHIVED;

    private final Class<E> entityClass;
    private final SessionFactory sessionFactory;

    protected HibernateRepository(final Class<E> clazz, final SessionFactory sessionFactory) {
        this.entityClass = clazz;
        this.sessionFactory = sessionFactory;
    }

    public void saveOrUpdate(final E model) {
        currentSession().saveOrUpdate(model);
        currentSession().flush();
    }

    public E get(final long id) {
        return currentSession().get(entityClass, id);
    }

    protected Criteria createCriteria() {
        return createCriteria(EXCLUDE_ARCHIVED);
    }

    protected Criteria createCriteria(final boolean excludeArchived) {
        final Criteria c = currentSession().createCriteria(entityClass);

        if (excludeArchived) {
            c.add(Restrictions.isNull("archivedAt"));
        }

        return c;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected <T> Set<T> asSet(final List<T> list) {
        final Set<T> set = new HashSet<>();
        for (final T t : list) {
            set.add(t);
        }
        return set;
    }

    @SuppressWarnings({ "rawtypes" })
    protected List<E> asList(final List generic) {
        return asList(entityClass, generic);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected <T> List<T> asList(final Class<T> clazz, final List generic) {
        return generic;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected List<Object[]> asProjectionList(final List generic) {
        return generic;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected List<Object> asSingleProjectionList(final List generic) {
        return generic;
    }
}
