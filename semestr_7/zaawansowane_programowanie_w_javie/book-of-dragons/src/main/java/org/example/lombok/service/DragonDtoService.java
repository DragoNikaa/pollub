package org.example.lombok.service;

import org.example.lombok.dto.DragonDto;
import org.example.lombok.entity.Dragon;

import java.time.LocalDate;

public class DragonDtoService {
    public static void main() {
        Dragon toothless = new Dragon("Toothless", "Night Fury", "black", LocalDate.of(1995, 2, 1));
        System.out.println("Initial state:\n" + toothless);

        DragonDto snapshot = createSnapshot(toothless);
        System.out.println("\nSaved snapshot:\n" + snapshot);

        toothless.setFlying(false);
        System.out.println("\nAfter update:\n" + toothless);

        revertChanges(toothless, snapshot);
        System.out.println("\nAfter reverting changes:\n" + toothless);
    }

    private static DragonDto createSnapshot(Dragon dragon) {
        return new DragonDto(
                dragon.getName(),
                dragon.getSpecies(),
                dragon.getColor(),
                dragon.getBirthDate(),
                dragon.isFlying()
        );
    }

    private static void revertChanges(Dragon dragon, DragonDto snapshot) {
        dragon.setName(snapshot.name());
        dragon.setSpecies(snapshot.species());
        dragon.setColor(snapshot.color());
        dragon.setBirthDate(snapshot.birthDate());
        dragon.setFlying(snapshot.isFlying());
    }
}