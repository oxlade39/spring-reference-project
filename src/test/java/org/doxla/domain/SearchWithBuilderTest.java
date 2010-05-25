package org.doxla.domain;

import org.doxla.domain.search.ApplicationExceptionSearchBuilder;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchWithBuilderTest extends AbstractSearchTestWithDefaultTestData {

    @Test
    public void testSearch() throws Exception {
        String searchText = "java.lang.IllegalArgumentException";
        List<ApplicationException> result = new ApplicationExceptionSearchBuilder(session())
                                                    .exception().search(searchText);
        assertFalse(result.isEmpty());
        assertTrue(result.contains(new ApplicationException(EXCEPTION_TRACE)));
    }

}