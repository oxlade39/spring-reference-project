package org.doxla.lucene;

import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.search.Environment;
import org.hibernate.search.cfg.SearchMapping;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

public class CustomAnnotationSessionFactoryBean extends AnnotationSessionFactoryBean {

    private Class<SearchMapping> searchMapping;

    @Override
    protected void postProcessAnnotationConfiguration(AnnotationConfiguration config) throws HibernateException {
        super.postProcessAnnotationConfiguration(config);
        config.getProperties().put(Environment.MODEL_MAPPING, searchMapping);
    }



    public Class<SearchMapping> getSearchMapping() {
        return searchMapping;
    }

    public void setSearchMapping(Class<SearchMapping> searchMapping) {
        this.searchMapping = searchMapping;
    }
}
