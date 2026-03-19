package com.taller.patrones.interfaces.rest.adapter;

import java.util.Map;

/**
 * Adaptador para transformar el payload del proveedor externo a la estructura
 * que requerimos
 */
public class ExternalFighterAdapter {
    private final Map<String, Object> body;
    private final String prefix;

    public ExternalFighterAdapter(Map<String, Object> body, int fighterNumber) {
        this.body = body;
        this.prefix = "fighter" + fighterNumber + "_";
    }

    public String getName(String defaultName) {
        return (String) body.getOrDefault(prefix + "name", defaultName);
    }

    public int getHp(int defaultHp) {
        return ((Number) body.getOrDefault(prefix + "hp", defaultHp)).intValue();
    }

    public int getAtk(int defaultAtk) {
        return ((Number) body.getOrDefault(prefix + "atk", defaultAtk)).intValue();
    }
}
