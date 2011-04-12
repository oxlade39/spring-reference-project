package org.doxla.spring.config;

import org.doxla.lucene.CustomAnnotationSessionFactoryBean;
import org.doxla.lucene.MySearchMapping;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.cfg.SearchMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfig {

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.ddl.mode}")
    private String ddlMode;

    @Value("${hibernate.show.sql}")
    private Boolean showSql;

    @Value("${hibernate.format.sql}")
    private Boolean formatSql;

    @Value("${hibernate.search.directory_provider}")
    private String directoryProvider;

    @Value("${hibernate.search.index_base}")
    private String indexBaseDir;

    @Bean
    public CustomAnnotationSessionFactoryBean sessionFactory(DataSource dataSource) {
        CustomAnnotationSessionFactoryBean sessionFactoryBean = new CustomAnnotationSessionFactoryBean();
        sessionFactoryBean.setPackagesToScan(domainPackages());
        sessionFactoryBean.setSearchMapping((Class<SearchMapping>) searchMapping());
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.setDataSource(dataSource);
        return sessionFactoryBean;
    }

    @Bean
    public AbstractPlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }

    @Bean @Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
    public Session session(SessionFactory sessionFactory) {
        return sessionFactory.getCurrentSession();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.hbm2ddl.auto", ddlMode);
        properties.setProperty("hibernate.show_sql", showSql.toString());
        properties.setProperty("hibernate.format_sql", formatSql.toString());
        properties.setProperty("hibernate.search.default.directory_provider", directoryProvider);
        properties.setProperty("hibernate.search.default.indexBase", indexBaseDir);
        return properties;
    }

    private String[] domainPackages() {
        return new String[]{"org.doxla.domain"};
    }

    private Class<? extends SearchMapping> searchMapping() {
        return MySearchMapping.class;
    }
}
