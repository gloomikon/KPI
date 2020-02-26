package com.example.lab02.controller;
import com.example.lab02.entity.Plane;
import com.example.lab02.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlaneController {
	@Autowired
	public PlaneService planeService;

	@GetMapping("/plane/{id}")
	public Plane getPlane(@PathVariable String id) {
		return planeService.readRow(id);
	}

	@PostMapping("/plane")
	public Plane addPlane(@RequestBody
								@RequestParam(value = "id", required = false) String id,
								@RequestParam(value = "name", required = false) String name,
								@RequestParam(value = "capacity", required = false) Integer capacity) {
		Plane plane = new Plane();
		plane.setId(id);
		plane.setName(name);
		plane.setCapacity(capacity);
		planeService.addRow(plane);
		return plane;
	}

	@PutMapping("/plane/{id}")
	public Plane updatePlane(@PathVariable String id,
								@RequestParam (value = "name", required = false) String name,
								@RequestParam (value = "capacity", required = false) Integer capacity) {
		Plane plane = planeService.readRow(id);
		plane.setName(name);
		plane.setCapacity(capacity);
		planeService.updateRow(plane);
		return plane;
	}

	@DeleteMapping("/plane/{id}")
	public Plane deletePlane(@PathVariable String id) {
		Plane plane = planeService.readRow(id);
		planeService.deleteRow(id);
		return plane;
	}
}
