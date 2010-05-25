package org.doxla.domain;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.junit.Test;

import java.util.List;

import static org.doxla.domain.DataCreator.TO_SEARCH_FOR;
import static org.hibernate.search.Search.getFullTextSession;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ApplicationExceptionSearchTest extends AbstractHibernateTest {

    @Test
    public void testSearch() throws Exception {
        // create native Lucene query
        String[] fields = new String[]{"exceptionTrace"};
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
        org.apache.lucene.search.Query query = parser.parse( "search" );

        // wrap Lucene query in a org.hibernate.Query
        org.hibernate.Query hibQuery = getFullTextSession(session()).createFullTextQuery(query, ApplicationException.class);

        // execute search
        List result = hibQuery.list();
        assertFalse(result.isEmpty());
        assertTrue(result.contains(new ApplicationException(TO_SEARCH_FOR)));
    }

}