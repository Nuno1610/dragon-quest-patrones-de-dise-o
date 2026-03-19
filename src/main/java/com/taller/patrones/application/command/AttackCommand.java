package com.taller.patrones.application.command;

import com.taller.patrones.application.observer.DamageObserver;
import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;

import java.util.List;

public class AttackCommand implements Command {

    private final Battle battle;
    private final Character attacker;
    private final Character defender;
    private final int damage;
    private final Attack attack;
    private final List<DamageObserver> observers;

    private int previousHp;
    private String previousTurn;
    private boolean previousFinished;

    public AttackCommand(Battle battle, Character attacker, Character defender, int damage, Attack attack,
            List<DamageObserver> observers) {
        this.battle = battle;
        this.attacker = attacker;
        this.defender = defender;
        this.damage = damage;
        this.attack = attack;
        this.observers = observers;
    }

    @Override
    public void execute() {
        this.previousHp = defender.getCurrentHp();
        this.previousTurn = battle.getCurrentTurn();
        this.previousFinished = battle.isFinished();

        defender.takeDamage(damage);
        String target = defender == battle.getPlayer() ? "player" : "enemy";
        battle.setLastDamage(damage, target);
        battle.log(attacker.getName() + " usa " + attack.getName() + " y hace " + damage + " de daño a "
                + defender.getName());

        for (DamageObserver obs : observers) {
            obs.onDamage(battle, attacker, defender, damage, attack);
        }

        battle.switchTurn();
        if (!defender.isAlive()) {
            battle.finish(attacker.getName());
        }
    }

    @Override
    public void undo() {
        // Restaurar estado anterior (simplificado)
        defender.restoreHp(previousHp);
        battle.setCurrentTurn(previousTurn);
        battle.setFinished(previousFinished);
        battle.log("Se ha deshecho el ataque de " + attacker.getName());
    }
}
