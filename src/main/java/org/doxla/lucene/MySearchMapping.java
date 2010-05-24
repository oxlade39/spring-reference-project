package org.doxla.lucene;

import org.apache.solr.analysis.*;
import org.apache.solr.client.solrj.SolrRequest;
import org.doxla.domain.ApplicationException;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.cfg.SearchMapping;

import java.lang.annotation.ElementType;

public class MySearchMapping extends SearchMapping {


    @Factory
    public SearchMapping create() {
        SearchMapping mapping = new SearchMapping();

    mapping
        .analyzerDef( "default", StandardTokenizerFactory.class )
            .filter( LowerCaseFilterFactory.class )
        .analyzerDef( "dotReplaced", PatternTokenizerFactory.class)
            .tokenizerParam("pattern", "(\\.)|(\\s+)")
            .filter( LowerCaseFilterFactory.class )
            .filter( PatternReplaceFilterFactory.class )
                .param( "pattern", ":" )
                .param( "replacement", "" )
                .param( "replace", "all" )

        .entity(ApplicationException.class)
            .indexed()
            .property("identity", ElementType.FIELD)
                .documentId()
            .property("exceptionTrace", ElementType.FIELD)
                .field()
                    .name("exceptionTrace")
                    .analyzer("dotReplaced")
            .property("checksum", ElementType.FIELD)
                .field()
                    .name("checksum")
                    .analyzer("default");

        return mapping;
    }

}
