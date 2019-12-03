package com.company.controller;
import com.company.entity.Place;
import com.company.repository.PlaceRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/places")
public class PlaceController {
    @Autowired
    PlaceRepository placeRepository;

    @GetMapping("")
    public String getPlaceByPlaneId(@RequestParam(value = "plane_id", required = false) Integer plane_id) {

        List<Place> resultList = placeRepository.findByPlaneId(plane_id);
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(resultList);
        return prettyJson;
    }
}
