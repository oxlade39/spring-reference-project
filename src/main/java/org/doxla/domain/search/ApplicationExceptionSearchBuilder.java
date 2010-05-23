package org.doxla.domain.search;

import com.sun.org.apache.bcel.internal.util.InstructionFinder;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.doxla.domain.ApplicationException;
import org.hibernate.search.FullTextSession;

import java.util.ArrayList;
import java.util.List;

public class ApplicationExceptionSearchBuilder {
    private final FullTextSession fullTextSession;

    private List<String> searchFields = new ArrayList<String>();

    public ApplicationExceptionSearchBuilder(FullTextSession fullTextSession) {
        this.fullTextSession = fullTextSession;
    }

    public ApplicationExceptionSearchBuilder exception() {
        searchFields.add("exceptionTrace");
        return this;
    }

    public List<ApplicationException> search(String search) {
        MultiFieldQueryParser parser = new MultiFieldQueryParser(
                searchFields.toArray(new String[searchFields.size()]),
                new StandardAnalyzer());
        try {
            org.apache.lucene.search.Query query = parser.parse( search );
            org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, ApplicationException.class);
            return hibQuery.list();
        } catch (ParseException e) {
            throw new RuntimeException("ParseException with query: "+search, e);
        }
    }
}
