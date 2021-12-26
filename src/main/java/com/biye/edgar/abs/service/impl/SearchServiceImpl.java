package com.biye.edgar.abs.service.impl;

import com.biye.edgar.abs.model.SearchRequest;
import com.biye.edgar.abs.model.SearchResult;
import com.biye.edgar.abs.model.SearchScope;
import com.biye.edgar.abs.service.EdgarService;
import com.biye.edgar.abs.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private EdgarService edgarService;

    public SearchServiceImpl(EdgarService edgarService) {
        this.edgarService = edgarService;
    }

    @Override
    public List<SearchResult<?>> search(SearchRequest request) {
        if(SearchScope.COMPANY.equals(request.getScope())){
            var entities = this.edgarService.searchIndex(request.getWd());
            return entities.stream().map(SearchResult::new).collect(Collectors.toList());
        }
        return null;
    }
}
