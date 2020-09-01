package com.example.armoryandmachine;

public class Enemy {

    int totalHealth;
    int currentHealth;
    boolean isResistantToBackend;
    boolean isVulnerableToBackend;
    boolean isResistantToFrontend;
    boolean isVulnerableToFrontend;
    boolean isResistantToDesign;
    boolean isVulnerableToDesign;
    boolean isResistantToSales;
    boolean isVulnerableToSales;
    int moveCounter = -1;
    int numberOfMoves;

    public Enemy(int position) {
        /*this.totalHealth = determineTotalHealth(position);
        this.currentHealth = totalHealth;
        this.isResistantToBackend = determineIsResistantToBackend();
        this.isVulnerableToBackend = determineIsVulnerableToBackend();
        this.isResistantToFrontend = determineIsResistantToFrontend();
        this.isVulnerableToFrontend = determineIsVulnerableToFrontend();
        this.isResistantToDesign = determineIsResistantToDesign();
        this.isVulnerableToDesign = determineIsVulnerableToDesign();
        this.isResistantToSales = determineIsResistantToSales();
        this.isVulnerableToSales = determineIsVulnerableToSales();*/
        totalHealth = 100;
        currentHealth = 100;
        numberOfMoves = 3;
    }

    public boolean isAlive(){
        if(currentHealth <= 0){
            return false;
        } else {
            return true;
        }
    }

    public int getCurrentMoveDamage() {
        return 5;
    }

    public synchronized void heal(int damage){
        currentHealth += damage;
    }

    public synchronized void hurt(int damage){
        currentHealth -= damage;
    }

    public String getNextMoveName(int position){
        moveCounter++;
        int moveCounterMod = moveCounter % numberOfMoves;
        switch(position) {
            case 0: switch(moveCounterMod) {
                case 0: return "move 1";
                case 1: return "move 2";
                case 2: return "move 3";
            }
            default: return "default move";
        }
    }

    public int getNextMoveTime(int position) {
        return 1000;
    }
}
