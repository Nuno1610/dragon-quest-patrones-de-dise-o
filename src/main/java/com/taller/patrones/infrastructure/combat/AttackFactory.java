package com.taller.patrones.infrastructure.combat;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.ComboAttack;

import java.util.HashMap;
import java.util.Map;

public class AttackFactory {
    private static final Map<String, Attack> REGISTRY = new HashMap<>();

    static {
        register("TACKLE", new Attack("Tackle", 40, Attack.AttackType.NORMAL));
        register("SLASH", new Attack("Slash", 55, Attack.AttackType.NORMAL));
        register("FIREBALL", new Attack("Fireball", 80, Attack.AttackType.SPECIAL));
        register("ICE_BEAM", new Attack("Ice Beam", 70, Attack.AttackType.SPECIAL));
        register("POISON_STING", new Attack("Poison Sting", 20, Attack.AttackType.STATUS));
        register("THUNDER", new Attack("Thunder", 90, Attack.AttackType.SPECIAL));
        // Ejercicio 1: Añadir el ataque Meteoro
        register("METEORO", new Attack("Meteoro", 120, Attack.AttackType.SPECIAL));

        // Ejercicio 9: Composite (Combo Triple)
        ComboAttack combo = new ComboAttack("Combo Triple");
        combo.addAttack(new Attack("Tackle", 40, Attack.AttackType.NORMAL));
        combo.addAttack(new Attack("Slash", 55, Attack.AttackType.NORMAL));
        combo.addAttack(new Attack("Fireball", 80, Attack.AttackType.SPECIAL));
        register("COMBO_TRIPLE", combo);
    }

    public static void register(String name, Attack attack) {
        REGISTRY.put(name.toUpperCase(), attack);
    }

    public static Attack createAttack(String name) {
        String n = name != null ? name.toUpperCase() : "";
        return REGISTRY.getOrDefault(n, new Attack("Golpe", 30, Attack.AttackType.NORMAL));
    }
}
