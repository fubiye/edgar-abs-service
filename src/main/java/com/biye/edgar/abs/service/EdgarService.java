package com.biye.edgar.abs.service;

import com.biye.edgar.abs.model.Accession;
import com.biye.edgar.abs.model.Company;

import java.util.List;

public interface EdgarService {
    List<Company> searchIndex(String keysTyped);
    String getSubmissionByCik(Accession accession);
    String getFilesByAccessionNum(Accession accession);
    String getFilingFile(Accession accession);
}
