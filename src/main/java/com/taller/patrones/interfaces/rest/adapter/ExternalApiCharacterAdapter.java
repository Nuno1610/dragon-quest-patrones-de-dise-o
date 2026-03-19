package com.taller.patrones.interfaces.rest.adapter;

import com.taller.patrones.domain.Character;
import java.util.Map;

/**
 * Patrón Adapter: Convierte un payload genérico (de una API externa)
 * a nuestro objeto de dominio interno (Character).
 */
public class ExternalApiCharacterAdapter {

    public static Character adapt(Map<String, Object> body, int number) {
        String prefix = "fighter" + number + "_";
        String name = (String) body.getOrDefault(prefix + "name", number == 1 ? "Héroe" : "Dragón");
        int hp = ((Number) body.getOrDefault(prefix + "hp", number == 1 ? 150 : 120)).intValue();
        int atk = ((Number) body.getOrDefault(prefix + "atk", number == 1 ? 25 : 30)).intValue();

        return new Character.Builder(name)
                .withHp(hp)
                .withAttack(atk)
                .withDefense(10)
                .withSpeed(10)
                .build();
    }
}
