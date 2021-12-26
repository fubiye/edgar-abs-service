package com.biye.edgar.abs.model;

import lombok.Data;

@Data
public class SearchRequest {
    private String wd;
    private SearchScope scope;
}
