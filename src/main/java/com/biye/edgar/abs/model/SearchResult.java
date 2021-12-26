package com.biye.edgar.abs.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchResult<T>{
    private T record;
    private String type;

    public SearchResult(T record){
        this.record = record;
        this.type = record.getClass().getSimpleName();
    }
}
