package io.spiffy.media.repository;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.media.entity.FileEntity;

public class FileRepository extends HibernateRepository<FileEntity> {

    @Inject
    public FileRepository(final SessionFactory sessionFactory) {
        super(FileEntity.class, sessionFactory);
    }

    public FileEntity get(final String name, final MediaType type) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("name", name));
        c.add(Restrictions.eq("type", type));
        return (FileEntity) c.uniqueResult();
    }

    public List<FileEntity> getByName(final String name) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("name", name));
        return asList(c.list());
    }

    public List<FileEntity> getByMD5(final String md5) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("md5", md5));
        return asList(c.list());
    }
}
