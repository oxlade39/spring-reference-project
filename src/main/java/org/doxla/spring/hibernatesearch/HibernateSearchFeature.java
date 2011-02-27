package org.doxla.spring.hibernatesearch;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Feature;
import org.springframework.context.annotation.FeatureConfiguration;

@FeatureConfiguration
public class HibernateSearchFeature {

    @Feature
    public HibernateSearchEnriched hibernateSearchEnriched(SessionFactory sessionFactory) {
        return new HibernateSearchEnriched(sessionFactory);
    }
}
