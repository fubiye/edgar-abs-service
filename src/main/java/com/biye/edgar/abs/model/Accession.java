package com.biye.edgar.abs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Accession {
    private int cik;
    private String accessionNum;
    private String fileName;
}
