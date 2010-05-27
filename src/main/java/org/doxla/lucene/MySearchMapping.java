package org.doxla.lucene;

import org.apache.solr.analysis.*;
import org.apache.solr.client.solrj.SolrRequest;
import org.doxla.domain.ApplicationException;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.cfg.SearchMapping;

import java.lang.annotation.ElementType;

import static java.lang.annotation.ElementType.FIELD;

public class MySearchMapping extends SearchMapping {

    public static final String DEFAULT_ANALYZER_NAME = "default";
    public static final String EXCEPTION_ANALYZER_NAME = "dotReplaced";


    @Factory
    public SearchMapping create() {
        SearchMapping mapping = new SearchMapping();

    mapping
        .analyzerDef( DEFAULT_ANALYZER_NAME, StandardTokenizerFactory.class )
            .filter( LowerCaseFilterFactory.class )
        .analyzerDef( EXCEPTION_ANALYZER_NAME, PatternTokenizerFactory.class)
            .tokenizerParam("pattern", "(\\.)|(\\s+)")
            .filter( LowerCaseFilterFactory.class )
            .filter( PatternReplaceFilterFactory.class )
                .param( "pattern", ":" )
                .param( "replacement", "" )
                .param( "replace", "all" )

        .entity(ApplicationException.class)
            .indexed()
            .property("identity", FIELD)
                .documentId()
            .property("exceptionTrace", FIELD)
                .field()
                    .name("exceptionTrace")
                    .analyzer(EXCEPTION_ANALYZER_NAME)
            .property("checksum", FIELD)
                .field()
                    .name("checksum")
                    .analyzer(DEFAULT_ANALYZER_NAME);

        return mapping;
    }

}
