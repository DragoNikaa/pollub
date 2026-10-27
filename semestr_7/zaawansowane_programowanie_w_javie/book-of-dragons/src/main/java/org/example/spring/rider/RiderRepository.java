package org.example.spring.rider;

import org.example.spring.dragon.Dragon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RiderRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Rider> getAll() {
        return jdbcTemplate.query(
                "SELECT id, active, firstName, lastName, village, age, favoriteFood FROM riders",
                BeanPropertyRowMapper.newInstance(Rider.class)
        );
    }

    public Rider getById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, active, firstName, lastName, village, age, favoriteFood FROM riders WHERE id = ?",
                BeanPropertyRowMapper.newInstance(Rider.class),
                id
        );
    }

    public int save(List<Rider> riders) {
        int saved = 0;

        for (Rider rider : riders) {
            saved += jdbcTemplate.update(
                    "INSERT INTO riders (active, firstName, lastName, village, age, favoriteFood) VALUES (TRUE, ?, ?, ?, ?, ?)",
                    rider.getFirstName(),
                    rider.getLastName(),
                    rider.getVillage(),
                    rider.getAge(),
                    rider.getFavoriteFood()
            );
        }
        return saved;
    }

    public int update(Rider rider) {
        return jdbcTemplate.update(
                "UPDATE riders SET firstName = ?, lastName = ?, village = ?, age = ?, favoriteFood = ? WHERE id = ?",
                rider.getFirstName(),
                rider.getLastName(),
                rider.getVillage(),
                rider.getAge(),
                rider.getFavoriteFood(),
                rider.getId()
        );
    }

    public int deactivate(int id) {
        return jdbcTemplate.update("UPDATE riders SET active = FALSE WHERE id = ?", id);
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM riders WHERE id = ?", id);
    }

    public List<Dragon> getDragons(int id) {
        return jdbcTemplate.query(
                "SELECT id, riderId, name, species, color, birthDate, flying FROM dragons WHERE riderId = ?",
                BeanPropertyRowMapper.newInstance(Dragon.class),
                id
        );
    }
}