package org.doxla.domain;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Transient;

/**
 * @author dan
 */
public abstract class EnrichedEntity {
    @Autowired
    @Transient
    Session session;

    public Long save() {
        return (Long) session.save(this);
    }
}
