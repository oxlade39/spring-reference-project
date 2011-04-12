package org.doxla.domain;

import org.doxla.spring.config.TestSpringLoader;
import org.hibernate.Session;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestSpringLoader.class)
@TransactionConfiguration
@Transactional
public abstract class AbstractHibernateTestInfrastructure {

    @Autowired
    private Session session;

    protected Session session() {
        return session;
    }
}
