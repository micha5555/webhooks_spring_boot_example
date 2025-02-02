package org.example.demo.repository;

import org.example.demo.model.SchoolData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolDataRepository extends JpaRepository<SchoolData, Integer>{
}
