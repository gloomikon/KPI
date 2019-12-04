package com.company.controller;
import com.company.config.Config;
import com.company.entity.Place;
import com.company.repository.PlaceRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/places")
public class PlaceController {
    @Autowired
    PlaceRepository placeRepository;

    PlaceController(){
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        placeRepository = (PlaceRepository) context.getBean("PlaceRepository");
    }

    @GetMapping("")
    public String getPlaceByPlaneId(@RequestParam(value = "plane_id", required = true) Integer plane_id) {

        List<Place> resultList = placeRepository.findByPlaneId(plane_id);
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(resultList);
        return prettyJson;
    }

    @PostMapping("")
    public void bookPlaceById(@RequestParam(value = "id", required = true) Integer id,
                              @RequestParam(value = "customer_id", required = true) Long customer_id) {
        Place place = placeRepository.findById(id);
        place.setCustomerId(customer_id);
        placeRepository.save(place);
    }

}
