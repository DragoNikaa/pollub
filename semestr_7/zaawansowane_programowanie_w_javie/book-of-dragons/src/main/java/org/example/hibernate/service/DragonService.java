package org.example.hibernate.service;

import lombok.RequiredArgsConstructor;
import org.example.hibernate.entity.Dragon;
import org.example.hibernate.entity.Rider;
import org.example.hibernate.repository.DragonRepository;
import org.example.hibernate.repository.RiderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DragonService {
    private final DragonRepository dragonRepository;
    private final RiderRepository riderRepository;

    public List<Dragon> findAll() {
        return dragonRepository.findAll();
    }

    public Optional<Dragon> findById(Integer id) {
        return dragonRepository.findById(id);
    }

    public Dragon save(Dragon dragon) {
        return dragonRepository.save(dragon);
    }

    public Dragon update(Integer id, Dragon updated) {
        Dragon existing = dragonRepository.findById(id).orElseThrow(() -> new RuntimeException("Dragon not found"));

        existing.setName(updated.getName());
        existing.setSpecies(updated.getSpecies());
        existing.setColor(updated.getColor());
        existing.setBirthDate(updated.getBirthDate());
        existing.setFlying(updated.getFlying());

        if (updated.getRider() != null) {
            Rider rider = riderRepository
                    .findById(updated.getRider().getId())
                    .orElseThrow(() -> new RuntimeException("Rider not found"));
            existing.setRider(rider);
        }

        return dragonRepository.save(existing);
    }

    public void deleteById(Integer id) {
        dragonRepository.deleteById(id);
    }

    public List<Dragon> findByRiderId(Integer riderId) {
        return dragonRepository.findByRiderId(riderId);
    }
}