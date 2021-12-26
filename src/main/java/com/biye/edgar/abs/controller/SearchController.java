package com.biye.edgar.abs.controller;

import com.biye.edgar.abs.model.SearchRequest;
import com.biye.edgar.abs.model.SearchResult;
import com.biye.edgar.abs.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/s")
public class SearchController {
    @Autowired
    private SearchService searchService;
    @GetMapping
    List<SearchResult<?>> search(SearchRequest request){
        return searchService.search(request);
    }
}
