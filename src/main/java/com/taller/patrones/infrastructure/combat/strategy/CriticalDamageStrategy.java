package com.taller.patrones.infrastructure.combat.strategy;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Character;

public class CriticalDamageStrategy implements DamageStrategy {
    @Override
    public int calculate(Character attacker, Character defender, Attack attack) {
        int raw = attacker.getAttack() * attack.getBasePower() / 100;
        int damage = Math.max(1, raw - defender.getDefense());

        // Probabilidad de 20%
        if (Math.random() < 0.2) {
            damage = (int) (damage * 1.5);
        }

        return damage;
    }
}
