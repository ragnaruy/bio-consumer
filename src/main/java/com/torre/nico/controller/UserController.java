package com.torre.nico.controller;

import com.torre.nico.controller.response.UserPreviewResp;
import com.torre.nico.service.TorreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private TorreService torreService;

    @GetMapping("/search-developers")
    public ResponseEntity<List<UserPreviewResp>> searchDeveloper(@RequestParam(name = "searchName") String searchName) {
        return new ResponseEntity<>(torreService.search(searchName, 0), HttpStatus.OK);
    }
}
