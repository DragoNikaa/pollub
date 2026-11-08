package org.example.hibernate.service;

import org.example.hibernate.entity.Rider;
import org.example.hibernate.repository.RiderRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RiderServiceTest {
    @Mock
    private RiderRepository repository;

    @InjectMocks
    private RiderService service;

    @Nested
    class FindAll {
        @Test
        void shouldReturnAllRiders_whenRidersExist() {
            List<Rider> riders = List.of(new Rider(), new Rider());
            when(repository.findAll()).thenReturn(riders);

            List<Rider> result = service.findAll();

            assertSame(riders, result);
            verify(repository).findAll();
        }

        @Test
        void shouldReturnEmptyList_whenRidersNotExist() {
            when(repository.findAll()).thenReturn(List.of());

            List<Rider> result = service.findAll();

            assertTrue(result.isEmpty());
            verify(repository).findAll();
        }
    }

    @Nested
    class FindById {
        @Test
        void shouldReturnRider_whenRiderExists() {
            int id = 1;
            Rider rider = new Rider();
            when(repository.findById(id)).thenReturn(Optional.of(rider));

            Optional<Rider> result = service.findById(id);

            assertTrue(result.isPresent());
            assertSame(rider, result.get());
            verify(repository).findById(id);
        }

        @Test
        void shouldReturnEmptyOptional_whenRiderNotExist() {
            int id = 1;
            when(repository.findById(id)).thenReturn(Optional.empty());

            Optional<Rider> result = service.findById(id);

            assertTrue(result.isEmpty());
            verify(repository).findById(id);
        }
    }

    @Nested
    class Save {
        @Test
        void shouldReturnRider() {
            Rider rider = new Rider();
            when(repository.save(rider)).thenReturn(rider);

            Rider result = service.save(rider);

            assertSame(rider, result);
            verify(repository).save(rider);
        }
    }

    @Nested
    class Update {
        @Test
        void shouldUpdateRider_whenRiderExists() {
            int id = 1;

            Rider existing = new Rider();
            existing.setFirstName("Old Astrid");

            Rider updated = new Rider();
            updated.setFirstName("New Astrid");
            updated.setLastName("Hofferson");
            updated.setVillage("Berk");
            updated.setAge(22);
            updated.setFavoriteFood("Yaknog");

            when(repository.findById(id)).thenReturn(Optional.of(existing));
            when(repository.save(existing)).thenReturn(existing);

            Rider result = service.update(id, updated);

            assertEquals(updated.getFirstName(), result.getFirstName());
            assertEquals(updated.getLastName(), result.getLastName());
            assertEquals(updated.getVillage(), result.getVillage());
            assertEquals(updated.getAge(), result.getAge());
            assertEquals(updated.getFavoriteFood(), result.getFavoriteFood());

            verify(repository).findById(id);
            verify(repository).save(existing);
        }

        @Test
        void shouldThrowException_whenRiderNotExist() {
            int id = 1;
            Rider updated = new Rider();
            when(repository.findById(id)).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(RuntimeException.class, () -> service.update(id, updated));

            assertEquals("Rider not found", exception.getMessage());
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
}