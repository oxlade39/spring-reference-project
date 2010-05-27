package org.doxla.domain.search;

import org.doxla.domain.ApplicationException;
import org.hibernate.search.FullTextSession;

import java.util.List;

import static org.doxla.domain.search.DomainFieldSearchBuilder.domainFieldSearchBuilder;
import static org.doxla.lucene.MySearchMapping.EXCEPTION_ANALYZER_NAME;

public class ApplicationExceptionSearchBuilder implements DomainSearch<ApplicationException> {
    private final DomainFieldSearchBuilder<ApplicationException> domainFieldSearchBuilder;

    public ApplicationExceptionSearchBuilder(FullTextSession fullTextSession) {
        domainFieldSearchBuilder = domainFieldSearchBuilder(fullTextSession, ApplicationException.class);
        domainFieldSearchBuilder.withAnalyzerName(EXCEPTION_ANALYZER_NAME);
    }

    public ApplicationExceptionSearchBuilder exception() {
        domainFieldSearchBuilder.forSearchField("exceptionTrace");
        return this;
    }

    public ApplicationExceptionSearchBuilder checksum() {
        domainFieldSearchBuilder.forSearchField("checksum");
        return this;
    }

    public List<ApplicationException> executeSearch(String search) {
        return domainFieldSearchBuilder.executeSearch(search);
    }
}
