package com.taller.patrones.application;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;
import com.taller.patrones.infrastructure.combat.CombatEngine;
import com.taller.patrones.infrastructure.persistence.BattleRepository;
import com.taller.patrones.application.observer.DamageObserver;
import com.taller.patrones.application.observer.AnalyticsDamageObserver;
import com.taller.patrones.application.command.Command;
import com.taller.patrones.application.command.AttackCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Caso de uso: gestionar batallas.
 * <p>
 * Nota: Crea sus propias dependencias con new. Cada vez que necesitamos
 * un CombatEngine o BattleRepository, hacemos new aquí.
 */
public class BattleService {

    private final CombatEngine combatEngine = new CombatEngine();
    private final BattleRepository battleRepository = BattleRepository.getInstance();
    private final List<DamageObserver> observers = new ArrayList<>();

    public BattleService() {
        observers.add(new AnalyticsDamageObserver());
    }

    public void addObserver(DamageObserver observer) {
        observers.add(observer);
    }

    public static final List<String> PLAYER_ATTACKS = List.of("TACKLE", "SLASH", "FIREBALL", "ICE_BEAM", "POISON_STING",
            "THUNDER");
    public static final List<String> ENEMY_ATTACKS = List.of("TACKLE", "SLASH", "FIREBALL");

    public BattleStartResult startBattle(Character player, Character enemy) {
        Battle battle = new Battle(player, enemy);
        String battleId = UUID.randomUUID().toString();
        battleRepository.save(battleId, battle);
        return new BattleStartResult(battleId, battle);
    }

    public BattleStartResult startBattle(String playerName, String enemyName) {
        Character player = new Character.Builder(playerName != null ? playerName : "Héroe")
                .withHp(150)
                .withAttack(25)
                .withDefense(15)
                .withSpeed(20)
                .withClass("Guerrero")
                .withEquipment("Espada Corta")
                .build();

        Character enemy = new Character.Builder(enemyName != null ? enemyName : "Dragón")
                .withHp(120)
                .withAttack(30)
                .withDefense(10)
                .withSpeed(15)
                .withClass("Monstruo")
                .build();

        return startBattle(player, enemy);
    }

    public Battle getBattle(String battleId) {
        return battleRepository.findById(battleId);
    }

    public void executePlayerAttack(String battleId, String attackName) {
        Battle battle = battleRepository.findById(battleId);
        if (battle == null || battle.isFinished() || !battle.isPlayerTurn())
            return;

        Attack attack = combatEngine.createAttack(attackName);
        int damage = combatEngine.calculateDamage(battle.getPlayer(), battle.getEnemy(), attack);

        Command cmd = new AttackCommand(battle, battle.getPlayer(), battle.getEnemy(), damage, attack, observers);
        battle.executeCommand(cmd);
    }

    public void executeEnemyAttack(String battleId, String attackName) {
        Battle battle = battleRepository.findById(battleId);
        if (battle == null || battle.isFinished() || battle.isPlayerTurn())
            return;

        Attack attack = combatEngine.createAttack(attackName != null ? attackName : "TACKLE");
        int damage = combatEngine.calculateDamage(battle.getEnemy(), battle.getPlayer(), attack);

        Command cmd = new AttackCommand(battle, battle.getEnemy(), battle.getPlayer(), damage, attack, observers);
        battle.executeCommand(cmd);
    }

    public void undoLastAttack(String battleId) {
        Battle battle = battleRepository.findById(battleId);
        if (battle != null) {
            battle.undoCommand();
        }
    }

    public record BattleStartResult(String battleId, Battle battle) {
    }
}
