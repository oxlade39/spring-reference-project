package org.doxla.spring.config;

import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class SearchConfig {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Bean @Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
    public FullTextSession fullTextSession() {
        return Search.getFullTextSession(sessionFactory.getCurrentSession());
    }
}
