package com.mayihavek.autorespawn.data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MayIHaveK
 * @Date 2024/5/2 1:35
 */
public class DeathData {
    public static ConcurrentHashMap<String, DeathData> deathDataHashMap = new ConcurrentHashMap<String, DeathData>();
    private String playerName;
    public double posX;
    public double posY;
    public double posZ;
    public int dimensionId;

    public DeathData(String playerName, double posX, double posY, double posZ, int dimensionId) {
        this.playerName = playerName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.dimensionId = dimensionId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(int dimensionId) {
        this.dimensionId = dimensionId;
    }
}
