package org.doxla.domain;

import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;

public class ApplicationExceptionMappingTest extends AbstractHibernateTestInfrastructure {

    @Test
    public void testMappingSingleEntity() {
        ApplicationException exception = new ApplicationException("some trace");
        Serializable id = session().save(exception);
        session().flush();
        session().clear();
        ApplicationException loaded = (ApplicationException) session().get(ApplicationException.class, id);

        assertEquals(exception, loaded);
        assertEquals(1, loaded.getOccurrences().size());
    }

    @Test
    public void testMappingMultiOccurence() throws Exception {
        ApplicationException exception = new ApplicationException("some other trace");
        Serializable id = session().save(exception);
        Thread.sleep(100L);
        exception.addOccurrenceNow();
        Thread.sleep(100L);
        exception.addOccurrenceNow();
        Thread.sleep(100L);
        exception.addOccurrenceNow();
        session().flush();
        session().clear();
        ApplicationException loaded = (ApplicationException) session().get(ApplicationException.class, id);

        assertEquals(4, loaded.getOccurrences().size());
    }


}
