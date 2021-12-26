package com.biye.edgar.abs.service;

import com.biye.edgar.abs.model.SearchRequest;
import com.biye.edgar.abs.model.SearchResult;

import java.util.List;

public interface SearchService {
    List<SearchResult<?>> search(SearchRequest request);
}
