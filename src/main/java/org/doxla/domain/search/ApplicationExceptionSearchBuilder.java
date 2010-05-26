package org.doxla.domain.search;

import org.apache.lucene.queryParser.ParseException;
import org.doxla.domain.ApplicationException;
import org.hibernate.search.FullTextSession;

import java.util.List;

import static org.doxla.domain.search.DomainFieldSearchBuilder.domainFieldSearchBuilder;

public class ApplicationExceptionSearchBuilder implements DomainSearch<ApplicationException>{
    private final DomainFieldSearchBuilder<ApplicationException> domainFieldSearchBuilder;

    public ApplicationExceptionSearchBuilder(FullTextSession fullTextSession) {
        this.domainFieldSearchBuilder = domainFieldSearchBuilder(fullTextSession, ApplicationException.class);
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
