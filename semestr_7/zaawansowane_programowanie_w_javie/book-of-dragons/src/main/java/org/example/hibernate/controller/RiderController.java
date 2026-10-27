package org.example.hibernate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hibernate.entity.Dragon;
import org.example.hibernate.entity.Rider;
import org.example.hibernate.service.DragonService;
import org.example.hibernate.service.RiderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/riders")
@RestController
@RequiredArgsConstructor
public class RiderController {
    private final RiderService riderService;
    private final DragonService dragonService;

    @GetMapping
    public ResponseEntity<List<Rider>> findAll() {
        return ResponseEntity.ok(riderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rider> findById(@PathVariable Integer id) {
        return riderService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rider> save(@Valid @RequestBody Rider rider) {
        Rider saved = riderService.save(rider);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rider> update(@PathVariable Integer id, @Valid @RequestBody Rider rider) {
        try {
            Rider updated = riderService.update(id, rider);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        riderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/dragons")
    public ResponseEntity<List<Dragon>> findDragons(@PathVariable Integer id) {
        if (riderService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dragonService.findByRiderId(id));
    }
}