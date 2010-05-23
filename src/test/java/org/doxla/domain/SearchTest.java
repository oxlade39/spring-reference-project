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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
                "classpath:/spring/hibernate-context.xml",
                "classpath:/spring/transaction-context.xml"
})
@TransactionConfiguration
@Transactional
public class SearchTest {

    private static final String TO_SEARCH_FOR = "to search for";

    @Autowired
    private SessionFactory sessionFactory;
    private Transaction transaction;

    private FullTextSession fullTextSession;
    private List<ApplicationException> added = new ArrayList<ApplicationException>();

    @Before
    public void createData() {

        fullTextSession = Search.getFullTextSession(session());

        ApplicationException toSave = new ApplicationException(TO_SEARCH_FOR);
        session().save(toSave);
        added.add(toSave);

        for(int i = 0; i < 100; i++) {
            toSave = new ApplicationException(RandomStringUtils.randomAlphabetic(i + 1));
            session().save(toSave);
            added.add(toSave);
        }
        session().flush();
        fullTextSession.flushToIndexes();
    }

    @After
    public void closeSearch() {
//        transaction.rollback();
    }

    @Test
    public void testSearch() throws Exception {
        // create native Lucene query
        String[] fields = new String[]{"exceptionTrace"};
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
        org.apache.lucene.search.Query query = parser.parse( "search" );

        // wrap Lucene query in a org.hibernate.Query
        org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, ApplicationException.class);

        // execute search
        List result = hibQuery.list();
        assertFalse(result.isEmpty());
        assertTrue(result.contains(new ApplicationException(TO_SEARCH_FOR)));
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

}