package org.example.hibernate.controller;

import org.example.hibernate.entity.Dragon;
import org.example.hibernate.service.DragonService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DragonControllerTest {
    @Mock
    private DragonService service;

    @InjectMocks
    private DragonController controller;

    @Nested
    class FindAll {
        @Test
        void shouldReturnOkWithAllDragons_whenDragonsExist() {
            List<Dragon> dragons = List.of(new Dragon(), new Dragon());
            when(service.findAll()).thenReturn(dragons);

            ResponseEntity<List<Dragon>> response = controller.findAll();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertSame(dragons, response.getBody());
            verify(service).findAll();
        }

        @Test
        void shouldReturnOkWithEmptyList_whenDragonsNotExist() {
            when(service.findAll()).thenReturn(List.of());

            ResponseEntity<List<Dragon>> response = controller.findAll();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().isEmpty());
            verify(service).findAll();
        }
    }

    @Nested
    class FindById {
        @Test
        void shouldReturnOkWithDragon_whenDragonExists() {
            int id = 1;
            Dragon dragon = new Dragon();
            when(service.findById(id)).thenReturn(Optional.of(dragon));

            ResponseEntity<Dragon> response = controller.findById(id);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertSame(dragon, response.getBody());
            verify(service).findById(id);
        }

        @Test
        void shouldReturnNotFound_whenDragonNotExists() {
            int id = 1;
            when(service.findById(id)).thenReturn(Optional.empty());

            ResponseEntity<Dragon> response = controller.findById(id);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(service).findById(id);
        }
    }

    @Nested
    class Save {
        @Test
        void shouldReturnCreatedWithDragon() {
            Dragon dragon = new Dragon();
            when(service.save(dragon)).thenReturn(dragon);

            ResponseEntity<Dragon> response = controller.save(dragon);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertSame(dragon, response.getBody());
            verify(service).save(dragon);
        }
    }

    @Nested
    class Update {
        @Test
        void shouldReturnOkWithDragon_whenDragonExists() {
            int id = 1;
            Dragon dragon = new Dragon();
            when(service.update(id, dragon)).thenReturn(dragon);

            ResponseEntity<Dragon> response = controller.update(id, dragon);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertSame(dragon, response.getBody());
            verify(service).update(id, dragon);
        }

        @Test
        void shouldReturnNotFound_whenDragonNotExists() {
            int id = 1;
            Dragon dragon = new Dragon();
            when(service.update(id, dragon)).thenThrow(new RuntimeException("Dragon not found"));

            ResponseEntity<Dragon> response = controller.update(id, dragon);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(service).update(id, dragon);
        }
    }

    @Nested
    class DeleteById {
        @Test
        void shouldReturnNoContent() {
            int id = 1;

            ResponseEntity<Void> response = controller.deleteById(id);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(service).deleteById(id);
        }
    }
}