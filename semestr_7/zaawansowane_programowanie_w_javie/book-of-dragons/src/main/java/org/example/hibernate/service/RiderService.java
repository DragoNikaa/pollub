package org.example.hibernate.service;

import lombok.RequiredArgsConstructor;
import org.example.hibernate.entity.Rider;
import org.example.hibernate.repository.RiderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RiderService {
    private final RiderRepository repository;

    public List<Rider> findAll() {
        return repository.findAll();
    }

    public Optional<Rider> findById(Integer id) {
        return repository.findById(id);
    }

    public Rider save(Rider rider) {
        return repository.save(rider);
    }

    public Rider update(Integer id, Rider updated) {
        Rider existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Rider not found"));

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setVillage(updated.getVillage());
        existing.setAge(updated.getAge());
        existing.setFavoriteFood(updated.getFavoriteFood());

        return repository.save(existing);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}