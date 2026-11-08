package org.example.hibernate.service;

import org.example.hibernate.entity.Dragon;
import org.example.hibernate.repository.DragonRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DragonServiceTest {
    @Mock
    private DragonRepository repository;

    @InjectMocks
    private DragonService service;

    @Nested
    class FindAll {
        @Test
        void shouldReturnAllDragons_whenDragonsExist() {
            List<Dragon> dragons = List.of(new Dragon(), new Dragon());
            when(repository.findAll()).thenReturn(dragons);

            List<Dragon> result = service.findAll();

            assertSame(dragons, result);
            verify(repository).findAll();
        }

        @Test
        void shouldReturnEmptyList_whenDragonsNotExist() {
            when(repository.findAll()).thenReturn(List.of());

            List<Dragon> result = service.findAll();

            assertTrue(result.isEmpty());
            verify(repository).findAll();
        }
    }

    @Nested
    class FindById {
        @Test
        void shouldReturnDragon_whenDragonExists() {
            int id = 1;
            Dragon dragon = new Dragon();
            when(repository.findById(id)).thenReturn(Optional.of(dragon));

            Optional<Dragon> result = service.findById(id);

            assertTrue(result.isPresent());
            assertSame(dragon, result.get());
            verify(repository).findById(id);
        }

        @Test
        void shouldReturnEmptyOptional_whenDragonNotExist() {
            int id = 1;
            when(repository.findById(id)).thenReturn(Optional.empty());

            Optional<Dragon> result = service.findById(id);

            assertTrue(result.isEmpty());
            verify(repository).findById(id);
        }
    }

    @Nested
    class Save {
        @Test
        void shouldReturnDragon() {
            Dragon dragon = new Dragon();
            when(repository.save(dragon)).thenReturn(dragon);

            Dragon result = service.save(dragon);

            assertSame(dragon, result);
            verify(repository).save(dragon);
        }
    }

    @Nested
    class Update {
        @Test
        void shouldUpdateDragon_whenDragonExists() {
            int id = 1;

            Dragon existing = new Dragon();
            existing.setName("Old Toothless");

            Dragon updated = new Dragon();
            updated.setName("New Toothless");
            updated.setSpecies("Night Fury");
            updated.setColor("black");
            updated.setBirthDate(LocalDate.of(1995, 2, 1));
            updated.setFlying(true);

            when(repository.findById(id)).thenReturn(Optional.of(existing));
            when(repository.save(existing)).thenReturn(existing);

            Dragon result = service.update(id, updated);

            assertEquals(updated.getName(), result.getName());
            assertEquals(updated.getSpecies(), result.getSpecies());
            assertEquals(updated.getColor(), result.getColor());
            assertEquals(updated.getBirthDate(), result.getBirthDate());
            assertEquals(updated.getFlying(), result.getFlying());

            verify(repository).findById(id);
            verify(repository).save(existing);
        }

        @Test
        void shouldThrowException_whenDragonNotExist() {
            int id = 1;
            Dragon updated = new Dragon();
            when(repository.findById(id)).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(RuntimeException.class, () -> service.update(id, updated));

            assertEquals("Dragon not found", exception.getMessage());
            verify(repository).findById(id);
            verify(repository, never()).save(any());
        }
    }

    @Nested
    class DeleteById {
        @Test
        void shouldCallRepository() {
            int id = 1;

            service.deleteById(id);

            verify(repository).deleteById(id);
        }
    }

    @Nested
    class FindByRiderId {
        @Test
        void shouldReturnDragons_whenRiderHasDragons() {
            int id = 1;
            List<Dragon> dragons = List.of(new Dragon(), new Dragon());
            when(repository.findByRiderId(id)).thenReturn(dragons);

            List<Dragon> result = service.findByRiderId(id);

            assertSame(dragons, result);
            verify(repository).findByRiderId(id);
        }

        @Test
        void shouldReturnEmptyList_whenRiderNotExist() {
            int id = 1;
            when(repository.findByRiderId(id)).thenReturn(List.of());

            List<Dragon> result = service.findByRiderId(id);

            assertTrue(result.isEmpty());
            verify(repository).findByRiderId(id);
        }
    }
}