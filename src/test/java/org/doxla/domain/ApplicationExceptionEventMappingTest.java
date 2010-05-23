package org.doxla.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApplicationExceptionEventMappingTest extends AbstractHibernateTest{

    @Test
    public void testMapping() {
        ApplicationException exception = new ApplicationException("some trace");
        ApplicationExceptionEvent event = new ApplicationExceptionEvent(exception);
        Serializable id = session().save(event);
        session().flush();
        session().clear();
        ApplicationExceptionEvent loaded = (ApplicationExceptionEvent) session().get(ApplicationExceptionEvent.class, id);

        assertEquals(event, loaded);
        assertTrue(EqualsBuilder.reflectionEquals(event, loaded));
    }


}