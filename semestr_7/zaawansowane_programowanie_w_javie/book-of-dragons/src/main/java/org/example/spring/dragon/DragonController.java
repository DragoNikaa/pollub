package org.example.spring.dragon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/dragons")
@RestController
public class DragonController {
    @Autowired
    DragonRepository repository;

    @GetMapping
    public List<Dragon> getAll() {
        return repository.getAll();
    }

    @GetMapping("/{id}")
    public Dragon getById(@PathVariable("id") int id) {
        return repository.getById(id);
    }

    @PostMapping
    public int add(@RequestBody List<Dragon> dragons) {
        return repository.save(dragons);
    }

    @PutMapping("/{id}")
    public int update(@PathVariable("id") int id, @RequestBody Dragon updated) {
        Dragon existing = repository.getById(id);

        existing.setRiderId(updated.getRiderId());
        existing.setName(updated.getName());
        existing.setSpecies(updated.getSpecies());
        existing.setColor(updated.getColor());
        existing.setBirthDate(updated.getBirthDate());
        existing.setFlying(updated.getFlying());

        return repository.update(existing);
    }

    @PatchMapping("/{id}")
    public int partiallyUpdate(@PathVariable("id") int id, @RequestBody Dragon updated) {
        Dragon existing = repository.getById(id);

        if (updated.getRiderId() != null) existing.setRiderId(updated.getRiderId());
        if (updated.getName() != null) existing.setName(updated.getName());
        if (updated.getSpecies() != null) existing.setSpecies(updated.getSpecies());
        if (updated.getColor() != null) existing.setColor(updated.getColor());
        if (updated.getBirthDate() != null) existing.setBirthDate(updated.getBirthDate());
        if (updated.getFlying() != null) existing.setFlying(updated.getFlying());

        return repository.update(existing);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return repository.delete(id);
    }
}