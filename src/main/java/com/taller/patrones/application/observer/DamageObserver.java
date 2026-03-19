package com.taller.patrones.application.observer;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;

/**
 * Interfaz para los observadores de daño.
 */
public interface DamageObserver {
    void onDamage(Battle battle, Character attacker, Character defender, int damage, Attack attack);
}
