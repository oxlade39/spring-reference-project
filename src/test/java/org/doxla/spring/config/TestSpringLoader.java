package org.doxla.spring.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.support.AbstractContextLoader;

public class TestSpringLoader extends AbstractContextLoader {

    public ApplicationContext loadContext(String... strings) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles(
                "test"
        );
        applicationContext.scan("org.doxla.spring.config");
        applicationContext.refresh();
        return applicationContext;
    }

    @Override
    protected String getResourceSuffix() {
        return ".class";
    }
}
