package com.biye.edgar.abs.model.edgar;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EdgarEntity {
    private String index;
    private String id;
    private String entity;

    public EdgarEntity(String index, String id) {
        this.index = index;
        this.id = id;
    }
}
