package org.doxla.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.util.EqualsHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
                "classpath:/spring/hibernate-context.xml",
                "classpath:/spring/transaction-context.xml"
})
@TransactionConfiguration
@Transactional
public class MappingTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testMapping() {
        ApplicationException exception = new ApplicationException("some trace", "a checksum");
        Serializable id = session().save(exception);
        session().flush();
        session().clear();
        ApplicationException loaded = (ApplicationException) session().get(ApplicationException.class, id);

        assertEquals(exception, loaded);
        assertTrue(EqualsBuilder.reflectionEquals(exception, loaded));
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

}
