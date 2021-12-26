package com.biye.edgar.abs.controller;

import com.biye.edgar.abs.model.Accession;
import com.biye.edgar.abs.service.EdgarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filing")
public class FilingController {
    @Autowired
    private EdgarService edgarService;

    @GetMapping(value = "/{cik}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getFilingByCik(@PathVariable("cik") int cik){
        var accession = Accession.builder().cik(cik).build();
        return edgarService.getSubmissionByCik(accession);
    }

    @GetMapping(value = "/{cik}/{accessionNum}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getFilingByAccessionNumber(@PathVariable("cik") int cik, @PathVariable("accessionNum") String accessionNum){
        var accession = Accession.builder().cik(cik).accessionNum(accessionNum).build();
        return edgarService.getFilesByAccessionNum(accession);
    }

    @GetMapping(value = "/{cik}/{accessionNum}/{fileName}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getFilingFile(@PathVariable("cik") int cik, @PathVariable("accessionNum") String accessionNum,@PathVariable("fileName") String fileName){
        var accession = Accession.builder().cik(cik).accessionNum(accessionNum).fileName(fileName).build();
        return edgarService.getFilingFile(accession);
    }
}
