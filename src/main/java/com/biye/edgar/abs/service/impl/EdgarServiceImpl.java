package com.biye.edgar.abs.service.impl;

import com.biye.edgar.abs.model.Accession;
import com.biye.edgar.abs.model.Company;
import com.biye.edgar.abs.service.EdgarService;
import com.biye.edgar.abs.util.EsUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EdgarServiceImpl implements EdgarService {

    private RestTemplate restTemplate;

    public EdgarServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Company> searchIndex(String keysTyped) {
        var body = "{\"keysTyped\":\"%s\",\"narrow\":true}".formatted(keysTyped);
        var searchResult = restTemplate.postForObject("https://efts.sec.gov/LATEST/search-index",body, String.class);
        var response = EsUtil.getSearchResponseFromJson(searchResult);
        return Arrays.stream(response.getHits().getHits()).map(hit -> {
            var source = hit.getSourceAsMap();
            var entity = new Company(hit.getId(),String.valueOf(source.get("entity")));
            return entity;
        }).collect(Collectors.toList());
    }

    @Override
    public String getSubmissionByCik(Accession accession) {
        String longCik = String.format("%010d",accession.getCik());
        String url = "https://data.sec.gov/submissions/CIK" + longCik +".json";
        return requestGetTemplate(url);
    }

    @Override
    public String getFilesByAccessionNum(Accession accession) {
        String url = "https://www.sec.gov/Archives/edgar/data/"+accession.getCik()+"/"+accession.getAccessionNum()+"/index.json";
        return requestGetTemplate(url);
    }

    @Override
    public String getFilingFile(Accession accession) {
        var dataPath = Paths.get("D:\\data\\edgar\\example",String.valueOf(accession.getCik()),accession.getAccessionNum(),accession.getFileName());
        try {
            return Files.readString(dataPath);
        } catch (Exception e) {
            return "<div>Not Available</div>";
        }
    }

    public String requestGetTemplate(String url){
        var headers = getDefaultHeader();
        var entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }

    private HttpHeaders getDefaultHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent","biye.fu@outlook.com");
        headers.add("Host","www.sec.gov");
        return headers;
    }
}
