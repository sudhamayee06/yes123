package com.pagination.controllers;

import com.pagination.entities.Photo;
import com.pagination.service.PhotoService;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/photoByOwner")
    @ResponseBody
    public String photosByTitle() {

        HashMap<String, Long> p = photoService.getPhotosByOwner();
        JSONObject obj = new JSONObject();
        for(String owner:p.keySet()) {
            obj.put(owner, p.get(owner));
        }
        return obj.toString();
    }

    @GetMapping("/mostActive")
    @ResponseBody
    public String mostActive() {

        HashMap<String, Long> p = photoService.mostActive();
        JSONObject obj = new JSONObject();
        for(String owner:p.keySet()) {
            obj.put(owner, p.get(owner));
        }
        return obj.toString();
    }

}

