package org.doxla.domain.search;

import com.sun.org.apache.bcel.internal.util.InstructionFinder;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
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
        Analyzer analyzer = fullTextSession.getSearchFactory().getAnalyzer("default");
        MultiFieldQueryParser standard = new MultiFieldQueryParser(
                Version.LUCENE_29,
                searchFields.toArray(new String[searchFields.size()]),
                analyzer);

        Analyzer dotReplaceAnalyzer = fullTextSession.getSearchFactory().getAnalyzer("dotReplaced");
        MultiFieldQueryParser dotReplaceParser = new MultiFieldQueryParser(
                Version.LUCENE_29,
                searchFields.toArray(new String[searchFields.size()]),
                dotReplaceAnalyzer);        
        try {
            org.apache.lucene.search.Query query =
                    standard.parse( search ).combine(new Query[]{
                            dotReplaceParser.parse(search)
            });
            org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, ApplicationException.class);
            return hibQuery.list();
        } catch (ParseException e) {
            throw new RuntimeException("ParseException with query: "+search, e);
        }
    }
}
