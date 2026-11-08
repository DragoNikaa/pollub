package org.example.hibernate.controller;

import org.example.hibernate.entity.Dragon;
import org.example.hibernate.entity.Rider;
import org.example.hibernate.service.DragonService;
import org.example.hibernate.service.RiderService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RiderControllerTest {
    @Mock
    private RiderService riderService;

    @Mock
    private DragonService dragonService;

    @InjectMocks
    private RiderController controller;

    @Nested
    class FindAll {
        @Test
        void shouldReturnOkWithAllRiders_whenRidersExist() {
            List<Rider> riders = List.of(new Rider(), new Rider());
            when(riderService.findAll()).thenReturn(riders);

            ResponseEntity<List<Rider>> response = controller.findAll();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertSame(riders, response.getBody());
            verify(riderService).findAll();
        }

        @Test
        void shouldReturnOkWithEmptyList_whenRidersNotExist() {
            when(riderService.findAll()).thenReturn(List.of());

            ResponseEntity<List<Rider>> response = controller.findAll();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().isEmpty());
            verify(riderService).findAll();
        }
    }

    @Nested
    class FindById {
        @Test
        void shouldReturnOkWithRider_whenRiderExists() {
            int id = 1;
            Rider rider = new Rider();
            when(riderService.findById(id)).thenReturn(Optional.of(rider));

            ResponseEntity<Rider> response = controller.findById(id);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertSame(rider, response.getBody());
            verify(riderService).findById(id);
        }

        @Test
        void shouldReturnNotFound_whenRiderNotExists() {
            int id = 1;
            when(riderService.findById(id)).thenReturn(Optional.empty());

            ResponseEntity<Rider> response = controller.findById(id);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(riderService).findById(id);
        }
    }

    @Nested
    class Save {
        @Test
        void shouldReturnCreatedWithRider() {
            Rider rider = new Rider();
            when(riderService.save(rider)).thenReturn(rider);

            ResponseEntity<Rider> response = controller.save(rider);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertSame(rider, response.getBody());
            verify(riderService).save(rider);
        }
    }

    @Nested
    class Update {
        @Test
        void shouldReturnOkWithRider_whenRiderExists() {
            int id = 1;
            Rider rider = new Rider();
            when(riderService.update(id, rider)).thenReturn(rider);

            ResponseEntity<Rider> response = controller.update(id, rider);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertSame(rider, response.getBody());
            verify(riderService).update(id, rider);
        }

        @Test
        void shouldReturnNotFound_whenRiderNotExists() {
            int id = 1;
            Rider rider = new Rider();
            when(riderService.update(id, rider)).thenThrow(new RuntimeException("Rider not found"));

            ResponseEntity<Rider> response = controller.update(id, rider);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(riderService).update(id, rider);
        }
    }

    @Nested
    class DeleteById {
        @Test
        void shouldReturnNoContent() {
            int id = 1;

            ResponseEntity<Void> response = controller.deleteById(id);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(riderService).deleteById(id);
        }
    }

    @Nested
    class FindDragons {
        @Test
        void shouldReturnOkWithDragons_whenRiderExists() {
            int id = 1;
            Rider rider = new Rider();
            List<Dragon> dragons = List.of(new Dragon(), new Dragon());
            when(riderService.findById(id)).thenReturn(Optional.of(rider));
            when(dragonService.findByRiderId(id)).thenReturn(dragons);

            ResponseEntity<List<Dragon>> response = controller.findDragons(id);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertSame(dragons, response.getBody());
            verify(riderService).findById(id);
            verify(dragonService).findByRiderId(id);
        }

        @Test
        void shouldReturnNotFound_whenRiderNotExists() {
            int id = 1;
            when(riderService.findById(id)).thenReturn(Optional.empty());

            ResponseEntity<List<Dragon>> response = controller.findDragons(id);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(riderService).findById(id);
            verifyNoInteractions(dragonService);
        }
    }
}