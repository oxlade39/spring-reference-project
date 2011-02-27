package org.doxla.domain.spring;

import org.doxla.spring.DataSourceConfig;
import org.doxla.spring.HibernateConfig;
import org.doxla.spring.PropertiesConfiguration;
import org.doxla.spring.TransactionFeature;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.support.AbstractContextLoader;

public class TestSpringLoader extends AbstractContextLoader {

    public ApplicationContext loadContext(String... strings) throws Exception {
        return new AnnotationConfigApplicationContext(
                DataSourceConfig.class,
                HibernateConfig.class,
                PropertiesConfiguration.class,
                TransactionFeature.class,
                TestDatabaseConfig.class
        );
    }

    @Override
    protected String getResourceSuffix() {
        return ".class";
    }
}
