package com.torre.nico.service;

import com.torre.nico.controller.response.UserPreviewResp;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TorreService {

    private RestTemplate restTemplate = new RestTemplate();


    public List<UserPreviewResp> search(String name) {

        return null;
    }
}
