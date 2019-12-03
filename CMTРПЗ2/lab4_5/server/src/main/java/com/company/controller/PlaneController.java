package com.company.controller;
import com.company.entity.Place;
import com.company.entity.Plane;
import com.company.repository.PlaceRepository;
import com.company.repository.PlaneRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/planes")
public class PlaneController {
    @Autowired
    PlaneRepository planeRepository;
    @Autowired
    PlaceRepository placeRepository;

    private String randomString() {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 2; i++)
            sb.append(AB.charAt(rnd.nextInt(26) + 10));
        for (int i = 0; i < 4; i++)
            sb.append(AB.charAt(rnd.nextInt(10)));
        return sb.toString();
    }

    @GetMapping("")
    public String getPlaneById(@RequestParam(value = "id", required = false) Integer id) {

        List<Plane> resultList = new ArrayList<>();
        if (id == null) {
            Iterable<Plane> result = planeRepository.findAll();
            result.forEach(resultList::add);
        }
        else {
            Optional<Plane> plane = planeRepository.findById(id);
            if (plane.isPresent()) {
                resultList.add(plane.get());
            }
        }
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(resultList);
        return prettyJson;
    }

    @PostMapping("")
    public Boolean addPlane(@RequestBody
                            @RequestParam(value = "name", required = true) String name,
                            @RequestParam(value = "capacity", required = true) Integer capacity,
                            @RequestParam(value = "date", required = true) String date) throws ParseException {
        Plane plane = new Plane();
        plane.setName(name);
        plane.setCapacity(capacity);
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        plane.setDate(date1);
        planeRepository.save(plane);
        for (int i = 0; i < capacity; ++i) {
            Place place = new Place();
            place.setName(randomString());
            place.setPlaneId(plane.getId());
            placeRepository.save(place);
        }
        return true;
    }
}
