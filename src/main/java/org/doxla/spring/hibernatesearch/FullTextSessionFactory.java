package org.doxla.spring.hibernatesearch;

import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class FullTextSessionFactory extends AbstractFactoryBean<FullTextSession> {

    private SessionFactory sessionFactory;

    @Override
    public Class<?> getObjectType() {
        return FullTextSession.class;
    }

    @Override
    protected FullTextSession createInstance() throws Exception {
        return Search.getFullTextSession(sessionFactory.getCurrentSession());
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
