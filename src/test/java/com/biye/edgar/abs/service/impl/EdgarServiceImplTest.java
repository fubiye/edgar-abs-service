package com.biye.edgar.abs.service.impl;

import com.biye.edgar.abs.model.Accession;
import com.biye.edgar.abs.service.EdgarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.Snippet;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static com.biye.edgar.abs.util.EsUtil.getSearchResponseFromJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EdgarServiceImplTest {
    static final String RESULT = """
            {"took":0,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":{"value":6,"relation":"eq"},"max_score":26.527145,"hits":[{"_index":"edgar_entity_20211217_040318","_type":"_doc","_id":"1399326","_score":26.527145,"_source":{"entity":"DBJ Value Up Fund","entity_words":"DBJ Value Up Fund","rank":2136}},{"_index":"edgar_entity_20211217_040318","_type":"_doc","_id":"1563863","_score":26.527145,"_source":{"entity":"DBJ Holdings, LLC","entity_words":"DBJ Holdings, LLC","rank":6551624}},{"_index":"edgar_entity_20211217_040318","_type":"_doc","_id":"1668738","_score":21.504097,"_source":{"entity":"DBJPM 2016-C1 Mortgage Trust","entity_words":"DBJPM 2016-C1 Mortgage Trust","rank":34003631}},{"_index":"edgar_entity_20211217_040318","_type":"_doc","_id":"1677390","_score":21.504097,"_source":{"entity":"DBJPM 2016-C3 Mortgage Trust","entity_words":"DBJPM 2016-C3 Mortgage Trust","rank":29074967}},{"_index":"edgar_entity_20211217_040318","_type":"_doc","_id":"1706403","_score":21.504097,"_source":{"entity":"DBJPM 2017-C6 Mortgage Trust","entity_words":"DBJPM 2017-C6 Mortgage Trust","rank":29274669}},{"_index":"edgar_entity_20211217_040318","_type":"_doc","_id":"1818254","_score":21.504097,"_source":{"entity":"DBJPM 2020-C9 Mortgage Trust","entity_words":"DBJPM 2020-C9 Mortgage Trust","rank":55000836}}]},"query":{"query":{"bool":{"should":[{"match":{"entity":{"query":"DBJ","operator":"and"}}},{"match":{"tickers":{"query":"DBJ","operator":"and","boost":100}}},{"exists":{"field":"tickers","boost":10}}],"must":[{"bool":{"should":[{"match":{"entity":{"query":"DBJ","operator":"and"}}},{"match":{"_id":{"query":"DBJ"}}}]}}]}},"from":0,"size":10}}
            """;
    @Mock
    private RestTemplate restTemplate;
    private EdgarService edgarService;

    @BeforeEach
    public void setup(){
        edgarService = new EdgarServiceImpl(restTemplate);
    }

    @Test
    public void testSearch(){
        when(restTemplate.postForObject(anyString(), anyString(), any())).thenReturn(RESULT);
//        var hits =edgarService.searchIndex("DBJ")
        assertEquals(edgarService.searchIndex("DBJ"), RESULT);
    }

    @Test
    public void testHits() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        var response = getSearchResponseFromJson(RESULT);
        System.out.println(response.getHits().getHits()[0].getSourceAsString());
    }

    @Test
    public void testGetSubmissionByCik(){
        final String[] url = new String[1];
        doAnswer((invocation -> {
            url[0] = invocation.getArgument(0);
            return null;
        })).when(restTemplate).getForObject(anyString(),any());
        edgarService.getSubmissionByCik(Accession.builder().cik(1706303).build());
        assertEquals("https://data.sec.gov/submissions/CIK0001706303.json",url[0]);
    }

}