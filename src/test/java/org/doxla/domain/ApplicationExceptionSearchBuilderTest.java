package org.doxla.domain;

import org.doxla.domain.search.ApplicationExceptionSearchBuilder;
import org.hibernate.search.FullTextSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.doxla.domain.DataCreator.EXCEPTION_TRACE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ApplicationExceptionSearchBuilderTest extends AbstractHibernateTestInfrastructure {

    @Autowired
    private FullTextSession fullTextSession;

    @Test
    public void testExceptionSearch() throws Exception {
        String searchText = "java.lang.IllegalArgumentException";
        List<ApplicationException> result = new ApplicationExceptionSearchBuilder(fullTextSession)
                                                    .exception().executeSearch(searchText);
        assertFalse(result.isEmpty());
        assertTrue(result.contains(new ApplicationException(EXCEPTION_TRACE)));
    }

    @Test
    public void testChecksumSearch() throws Exception {
        // this search is almost certainly better executed with normal hibernate rather than lucene.
        String searchText = new ApplicationException(EXCEPTION_TRACE).getChecksum();
        List<ApplicationException> result = new ApplicationExceptionSearchBuilder(fullTextSession)
                                                    .checksum().executeSearch(searchText);
        assertFalse(result.isEmpty());
        assertTrue(result.contains(new ApplicationException(EXCEPTION_TRACE)));
    }

}