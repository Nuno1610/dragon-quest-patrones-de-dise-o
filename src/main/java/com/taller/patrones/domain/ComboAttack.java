package com.taller.patrones.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Composite: Representa un ataque compuesto que contiene hojas
 * u otros nodos (ataques individuales), tratándolos como si fueran un
 * solo objeto de tipo Attack.
 */
public class ComboAttack extends Attack {

    private final List<Attack> attacks = new ArrayList<>();

    public ComboAttack(String name) {
        super(name, 0, AttackType.NORMAL); // El poder y tipo lo definimos en tiempo real o sumado
    }

    public void addAttack(Attack attack) {
        attacks.add(attack);
    }

    public void removeAttack(Attack attack) {
        attacks.remove(attack);
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    @Override
    public int getBasePower() {
        return attacks.stream().mapToInt(Attack::getBasePower).sum();
    }
}
