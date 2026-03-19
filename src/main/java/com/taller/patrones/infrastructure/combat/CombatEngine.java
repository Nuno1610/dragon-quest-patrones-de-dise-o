package com.taller.patrones.infrastructure.combat;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Character;
import com.taller.patrones.infrastructure.combat.strategy.*;

import java.util.EnumMap;
import java.util.Map;

/**
 * Motor de combate. Calcula daño y crea ataques.
 */
public class CombatEngine {

    private final Map<Attack.AttackType, DamageStrategy> strategies;

    public CombatEngine() {
        strategies = new EnumMap<>(Attack.AttackType.class);
        strategies.put(Attack.AttackType.NORMAL, new NormalDamageStrategy());
        strategies.put(Attack.AttackType.SPECIAL, new SpecialDamageStrategy());
        strategies.put(Attack.AttackType.STATUS, new StatusDamageStrategy());
        strategies.put(Attack.AttackType.CRITICAL, new CriticalDamageStrategy());
    }

    /**
     * Crea un ataque a partir de su nombre usando el Factory.
     */
    public Attack createAttack(String name) {
        return AttackFactory.createAttack(name);
    }

    /**
     * Calcula el daño según la estrategia del tipo de ataque.
     */
    public int calculateDamage(Character attacker, Character defender, Attack attack) {
        DamageStrategy strategy = strategies.getOrDefault(attack.getType(), new NormalDamageStrategy());
        return strategy.calculate(attacker, defender, attack);
    }
}
