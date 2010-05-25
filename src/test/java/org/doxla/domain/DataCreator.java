package org.doxla.domain;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;

@Transactional
public class DataCreator implements TransactionCallback<Object> {

    public static final String TO_SEARCH_FOR = "to search for";
    public static final String EXCEPTION_TRACE = generateExceptionTrace();
    
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private PlatformTransactionManager transactionManager;


    @PostConstruct
    public void createData() {
        new TransactionTemplate(transactionManager).execute(this);
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

    public Object doInTransaction(TransactionStatus transactionStatus) {
        FullTextSession session = Search.getFullTextSession(sessionFactory.getCurrentSession());

        ApplicationException toSave = new ApplicationException(EXCEPTION_TRACE);
        session.save(toSave);
        session.save(new ApplicationException(TO_SEARCH_FOR));

        for(int i = 0; i < 100; i++) {
            toSave = new ApplicationException(RandomStringUtils.randomAlphabetic(i + 1));
            session.save(toSave);
        }
        session.flush();
        session.flushToIndexes();
        return this;
    }
}
