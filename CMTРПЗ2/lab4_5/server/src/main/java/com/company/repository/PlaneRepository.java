package com.company.repository;
import com.company.entity.Plane;
import org.springframework.data.repository.CrudRepository;

public interface PlaneRepository extends CrudRepository<Plane, Integer> {
}
