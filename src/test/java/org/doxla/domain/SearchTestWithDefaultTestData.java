package org.doxla.domain;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
                "classpath:/spring/hibernate-context.xml",
                "classpath:/spring/transaction-context.xml"
})
@TransactionConfiguration
@Transactional
public class SearchTestWithDefaultTestData {
    protected static final String TO_SEARCH_FOR = "to search for";
    
    @Autowired
    private SessionFactory sessionFactory;

    protected FullTextSession fullTextSession;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Before
    public void createData() {

        fullTextSession = Search.getFullTextSession(session());

        ApplicationException toSave = new ApplicationException(SearchWithBuilderTest.TO_SEARCH_FOR);
        session().save(toSave);

        for(int i = 0; i < 100; i++) {
            toSave = new ApplicationException(RandomStringUtils.randomAlphabetic(i + 1));
            session().save(toSave);
        }
        session().flush();
        fullTextSession.flushToIndexes();
    }
}
