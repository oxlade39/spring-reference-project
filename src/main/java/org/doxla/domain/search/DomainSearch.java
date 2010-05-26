package org.doxla.domain.search;

import java.util.List;

public interface DomainSearch<T> {

    public List<T> executeSearch(String searchText);

}
