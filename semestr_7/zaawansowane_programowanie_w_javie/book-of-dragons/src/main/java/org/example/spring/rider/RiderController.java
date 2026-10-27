package org.example.spring.rider;

import org.example.spring.dragon.Dragon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/riders")
@RestController
public class RiderController {
    @Autowired
    RiderRepository repository;

    @GetMapping
    public List<Rider> getAll() {
        return repository.getAll();
    }

    @GetMapping("/{id}")
    public Rider getById(@PathVariable("id") int id) {
        return repository.getById(id);
    }

    @PostMapping
    public int add(@RequestBody List<Rider> riders) {
        return repository.save(riders);
    }

    @PutMapping("/{id}")
    public int update(@PathVariable("id") int id, @RequestBody Rider updated) {
        Rider existing = repository.getById(id);

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setVillage(updated.getVillage());
        existing.setAge(updated.getAge());
        existing.setFavoriteFood(updated.getFavoriteFood());

        return repository.update(existing);
    }

    @PatchMapping("/{id}")
    public int partiallyUpdate(@PathVariable("id") int id, @RequestBody Rider updated) {
        Rider existing = repository.getById(id);

        if (updated.getFirstName() != null) existing.setFirstName(updated.getFirstName());
        if (updated.getLastName() != null) existing.setLastName(updated.getLastName());
        if (updated.getVillage() != null) existing.setVillage(updated.getVillage());
        if (updated.getAge() != null) existing.setAge(updated.getAge());
        if (updated.getFavoriteFood() != null) existing.setFavoriteFood(updated.getFavoriteFood());

        return repository.update(existing);
    }

    @PatchMapping("/{id}/deactivate")
    public int deactivate(@PathVariable("id") int id) {
        return repository.deactivate(id);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return repository.delete(id);
    }

    @GetMapping("/{id}/dragons")
    public List<Dragon> getDragons(@PathVariable("id") int id) {
        return repository.getDragons(id);
    }
}