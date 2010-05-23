package org.doxla.domain;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.doxla.domain.search.ApplicationExceptionSearchBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
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

public class SearchWithBuilderTest extends SearchTestWithDefaultTestData {

    @Test
    public void testSearch() throws Exception {
        String searchText = TO_SEARCH_FOR.split(" ")[1];
        List<ApplicationException> result = new ApplicationExceptionSearchBuilder(fullTextSession)
                                                    .exception().search(searchText);
        assertFalse(result.isEmpty());
        assertTrue(result.contains(new ApplicationException(TO_SEARCH_FOR)));
    }

}