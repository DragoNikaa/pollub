package org.example.spring.dragon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DragonRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Dragon> getAll() {
        return jdbcTemplate.query(
                "SELECT id, riderId, name, species, color, birthDate, flying FROM dragons",
                BeanPropertyRowMapper.newInstance(Dragon.class)
        );
    }

    public Dragon getById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, riderId, name, species, color, birthDate, flying FROM dragons WHERE id = ?",
                BeanPropertyRowMapper.newInstance(Dragon.class),
                id
        );
    }

    public int save(List<Dragon> dragons) {
        int saved = 0;

        for (Dragon dragon : dragons) {
            saved += jdbcTemplate.update(
                    "INSERT INTO dragons (riderId, name, species, color, birthDate, flying) VALUES (?, ?, ?, ?, ?, ?)",
                    dragon.getRiderId(),
                    dragon.getName(),
                    dragon.getSpecies(),
                    dragon.getColor(),
                    dragon.getBirthDate(),
                    dragon.getFlying()
            );
        }
        return saved;
    }

    public int update(Dragon dragon) {
        return jdbcTemplate.update(
                "UPDATE dragons SET riderId = ?, name = ?, species = ?, color = ?, birthDate = ?, flying = ? WHERE id = ?",
                dragon.getRiderId(),
                dragon.getName(),
                dragon.getSpecies(),
                dragon.getColor(),
                dragon.getBirthDate(),
                dragon.getFlying(),
                dragon.getId()
        );
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM dragons WHERE id = ?", id);
    }
}