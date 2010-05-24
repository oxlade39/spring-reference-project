package org.doxla.domain;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.junit.Before;
import org.junit.Ignore;

import java.io.PrintWriter;
import java.io.StringWriter;

@Ignore("test infrastructure")
public class AbstractSearchTestWithDefaultTestData extends AbstractHibernateTest {
    protected static final String TO_SEARCH_FOR = "to search for";
    protected static final String EXCEPTION_TRACE = generateExceptionTrace();

    @Override
    protected FullTextSession session() {
        return Search.getFullTextSession(super.session());
    }


    @Before
    public void createData() {
        ApplicationException toSave = new ApplicationException(EXCEPTION_TRACE);
        session().save(toSave);
        session().save(new ApplicationException(TO_SEARCH_FOR));

        for(int i = 0; i < 100; i++) {
            toSave = new ApplicationException(RandomStringUtils.randomAlphabetic(i + 1));
            session().save(toSave);
        }
        session().flush();
        session().flushToIndexes();
    }

    protected static String generateExceptionTrace() {
        StringWriter stringWriter = new StringWriter();
        try {
            new ApplicationException(null);
        }catch(Exception e) {
            e.printStackTrace(new PrintWriter(stringWriter));
        }
        return stringWriter.toString();
    }
}
