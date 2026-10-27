package org.example.hibernate.repository;

import org.example.hibernate.entity.Dragon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DragonRepository extends JpaRepository<Dragon, Integer> {
    List<Dragon> findByRiderId(Integer riderId);
}