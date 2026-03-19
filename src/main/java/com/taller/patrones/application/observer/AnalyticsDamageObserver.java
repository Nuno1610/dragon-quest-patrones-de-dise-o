package com.taller.patrones.application.observer;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;

public class AnalyticsDamageObserver implements DamageObserver {
    @Override
    public void onDamage(Battle battle, Character attacker, Character defender, int damage, Attack attack) {
        System.out.println("[Analytics] Evento de daño registrado: " + attacker.getName() + " -> " + defender.getName()
                + " daño: " + damage);
    }
}
