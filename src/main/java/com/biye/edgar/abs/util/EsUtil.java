package com.biye.edgar.abs.util;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.DeprecationHandler.IGNORE_DEPRECATIONS;
import static org.springframework.data.elasticsearch.client.util.NamedXContents.getDefaultNamedXContents;

public class EsUtil {
    public static SearchResponse getSearchResponseFromJson(String jsonResponse){
        try {
            NamedXContentRegistry registry = new NamedXContentRegistry(getDefaultNamedXContents());

            XContentParser parser = JsonXContent.jsonXContent.createParser(registry,IGNORE_DEPRECATIONS, jsonResponse);
            return SearchResponse.fromXContent(parser);
        } catch (IOException e) {
            System.out.println("exception " + e);
        }catch (Exception e){
            System.out.println("exception " + e);
        }
        return null;
    }
}
