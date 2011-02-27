package org.doxla.spring.hibernatesearch;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.config.AbstractSpecificationExecutor;
import org.springframework.context.config.SpecificationContext;

import static org.springframework.aop.scope.ScopedProxyUtils.createScopedProxy;

public class HibernateSearchEnrichmentExecutor extends AbstractSpecificationExecutor<HibernateSearchEnriched> {
    private static final String HIBERNATE_SEARCH_BEAN_NAME = HibernateSearchEnrichmentExecutor.class.getName() + ".searchSession";

    @Override
    protected void doExecute(HibernateSearchEnriched specification, SpecificationContext specificationContext) {
        BeanDefinitionHolder definition = fullTextSessionDefinition(specification);
        BeanDefinitionHolder holder =
            createScopedProxy(definition, specificationContext.getRegistry(), true);

        specificationContext.getRegistrar().registerBeanComponent(new BeanComponentDefinition(holder));
    }

    private BeanDefinitionHolder fullTextSessionDefinition(HibernateSearchEnriched specification) {
        SessionFactory sessionFactory = specification.getSessionFactory();
        RootBeanDefinition def = new RootBeanDefinition(FullTextSessionFactory.class);
        def.setScope("prototype");
        def.getPropertyValues().addPropertyValue("sessionFactory", sessionFactory);

        return new BeanDefinitionHolder(def, HIBERNATE_SEARCH_BEAN_NAME);
    }
}
