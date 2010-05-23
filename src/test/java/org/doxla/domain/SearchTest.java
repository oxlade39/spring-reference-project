package org.doxla.domain;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchTest extends SearchTestWithDefaultTestData {

    @Test
    public void testSearch() throws Exception {
        // create native Lucene query
        String[] fields = new String[]{"exceptionTrace"};
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
        org.apache.lucene.search.Query query = parser.parse( "search" );

        // wrap Lucene query in a org.hibernate.Query
        org.hibernate.Query hibQuery = session().createFullTextQuery(query, ApplicationException.class);

        // execute search
        List result = hibQuery.list();
        assertFalse(result.isEmpty());
        assertTrue(result.contains(new ApplicationException(TO_SEARCH_FOR)));
    }

}