package org.doxla.domain.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.doxla.lucene.MySearchMapping;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;

import java.util.ArrayList;
import java.util.List;

import static org.doxla.lucene.MySearchMapping.DEFAULT_ANALYZER_NAME;

public class DomainFieldSearchBuilder<T> implements DomainSearch<T> {
    private FullTextSession fullTextSession;
    private List<String> searchFields = new ArrayList<String>();
    private Class<T> criteriaClass;
    private String analyzerName = DEFAULT_ANALYZER_NAME;

    private DomainFieldSearchBuilder(FullTextSession fullTextSession, Class<T> domainClass) {
        this.fullTextSession = fullTextSession;
        this.criteriaClass = domainClass;
    }
    
    public static <A> DomainFieldSearchBuilder<A> domainFieldSearchBuilder(FullTextSession fullTextSession, Class<A> domainClass) {
        return new DomainFieldSearchBuilder<A>(fullTextSession, domainClass);
    }

    public DomainFieldSearchBuilder<T> forSearchField(String searchField) {
        searchFields.add(searchField);
        return this;
    }

    public DomainFieldSearchBuilder<T> withAnalyzerName(String analyzerName) {
        this.analyzerName = analyzerName;
        return this;
    }

    private Query createLuceneQuery(String search) throws ParseException {
        Analyzer analyzer = fullTextSession.getSearchFactory().getAnalyzer(analyzerName);
        MultiFieldQueryParser dotReplaceParser = new MultiFieldQueryParser(
                Version.LUCENE_29,
                searchFields.toArray(new String[searchFields.size()]),
                analyzer);
        return dotReplaceParser.parse(search);
    }

    public List<T> executeSearch(String searchText) {
        try {
            FullTextQuery textQuery = fullTextSession.createFullTextQuery(createLuceneQuery(searchText), criteriaClass);
            return textQuery.list();
        } catch (ParseException e) {
            throw new RuntimeException("Exception parsing query", e);
        }
    }

}
