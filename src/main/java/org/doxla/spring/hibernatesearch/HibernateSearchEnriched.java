package org.doxla.spring.hibernatesearch;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.parsing.ProblemCollector;
import org.springframework.context.config.AbstractFeatureSpecification;
import org.springframework.context.config.FeatureSpecificationExecutor;

public class HibernateSearchEnriched extends AbstractFeatureSpecification {

    private static final Class<? extends FeatureSpecificationExecutor> EXECUTOR_TYPE = HibernateSearchEnrichmentExecutor.class;
    private SessionFactory sessionFactory;

    public HibernateSearchEnriched(SessionFactory sessionFactory) {
        super(EXECUTOR_TYPE);
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected void doValidate(ProblemCollector problems) {
        if(sessionFactory == null){
            problems.error("SessionFactory must not be null");
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
