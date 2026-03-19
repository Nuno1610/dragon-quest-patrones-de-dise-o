package com.taller.patrones.application.facade;

import com.taller.patrones.application.BattleService;
import com.taller.patrones.domain.Battle;

/**
 * Patrón Facade: Proporciona una interfaz simplificada para clientes que
 * solo quieren "jugar" sin conocer BattleRepository, CombatEngine, etc.
 */
public class CombatFacade {

    private final BattleService battleService = new BattleService();

    public BattleService.BattleStartResult startSimpleBattle() {
        return battleService.startBattle("Jugador", "Enemigo");
    }

    public void executeTurn(String battleId, String playerAttackName) {
        battleService.executePlayerAttack(battleId, playerAttackName);

        Battle battle = battleService.getBattle(battleId);
        if (battle != null && !battle.isFinished() && !battle.isPlayerTurn()) {
            // El enemigo responde automáticamente
            battleService.executeEnemyAttack(battleId, "FIREBALL");
        }
    }

    public Battle getBattleState(String battleId) {
        return battleService.getBattle(battleId);
    }
}
