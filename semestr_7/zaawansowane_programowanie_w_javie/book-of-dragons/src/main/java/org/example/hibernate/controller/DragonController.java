package org.example.hibernate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hibernate.entity.Dragon;
import org.example.hibernate.service.DragonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/dragons")
@RestController
@RequiredArgsConstructor
public class DragonController {
    private final DragonService service;

    @GetMapping
    public ResponseEntity<List<Dragon>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dragon> findById(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Dragon> save(@Valid @RequestBody Dragon dragon) {
        Dragon saved = service.save(dragon);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dragon> update(@PathVariable Integer id, @Valid @RequestBody Dragon dragon) {
        try {
            Dragon updated = service.update(id, dragon);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}