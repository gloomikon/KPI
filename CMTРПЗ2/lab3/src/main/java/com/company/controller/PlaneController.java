package com.company.controller;
import com.company.config.Config;
import com.company.entity.Plane;
import com.company.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/planes")
public class PlaneController {
    @Autowired
    PlaneRepository planeRepository;

    PlaneController(){
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        planeRepository = (PlaneRepository) context.getBean("PlaneRepository");
    }

    @GetMapping("")
    public List<Plane> getPlaneById(@RequestParam(value = "id", required = false) Integer id) {

        List<Plane> resultList = new LinkedList<>();
        if (id == null) {
            resultList = planeRepository.findAll();
        }
        else {
            Plane plane = planeRepository.findById(id);
            resultList.add(plane);
        }

        return resultList;
    }
}