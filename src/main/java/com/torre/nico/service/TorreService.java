package com.torre.nico.service;

import com.torre.nico.controller.response.UserPreviewResp;
import com.torre.nico.util.JsonUtil;
import com.torre.nico.util.SearchBodySearchUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

@Service
public class TorreService {

    private final static String DEFAULT_LANG = "en";
    private final static Integer DEFAULT_PAGING_SIZE = 20;
    private final static Boolean DEFAULT_AGGREGATE = false;

    @Value("${torre-search-url}")
    private String torreSearchUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("addresses")
    @Scheduled(fixedDelay = 30000)
    public List<UserPreviewResp> search(String searchName, int page) {
        if (StringUtils.isEmpty(searchName)) {
            return new ArrayList<>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JsonObject body = SearchBodySearchUtil.searchCriteriaForDevelopers(searchName);

        HttpEntity<?> entity = new HttpEntity<>(body.toString(),headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(torreSearchUrl)
                .queryParam("page", page)
                .queryParam("lang", DEFAULT_LANG)
                .queryParam("size", DEFAULT_PAGING_SIZE)
                .queryParam("aggregate", DEFAULT_AGGREGATE);

        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                String.class);

        JsonArray jsonResponse = JsonUtil.convertFromString(response.getBody()).getJsonArray("results");

        return this.processTorreSearchJson(jsonResponse);
    }

    private List<UserPreviewResp> processTorreSearchJson(JsonArray jsonResponse) {
        List<UserPreviewResp> respList = new ArrayList<>();
        for (JsonObject userJson : jsonResponse.getValuesAs(JsonObject.class)) {
            UserPreviewResp userPreviewResp = new UserPreviewResp();
            userPreviewResp.setName(userJson.getString("name"));
            userPreviewResp.setUsername(userJson.getString("username"));
            userPreviewResp.setPictureUrl(JsonUtil.getString(userJson, "picture"));
            userPreviewResp.setJobTitle(userJson.getString("professionalHeadline"));
            userPreviewResp.setLocation((JsonUtil.getString(userJson, "locationName")));
            respList.add(userPreviewResp);
        }
        return respList;
    }
}
