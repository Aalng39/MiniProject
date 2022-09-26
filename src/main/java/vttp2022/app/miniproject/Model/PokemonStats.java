package vttp2022.app.miniproject.Model;

import java.io.Serializable;

public class PokemonStats implements Serializable {
    private String hp;
    private String attack;
    private String defense;
    private String specialAttack;
    private String specialDefense;
    private String speed;

    public String getHp() {
        return hp;
    }
    public void setHp(String hp) {
        this.hp = hp;
    }
    public String getAttack() {
        return attack;
    }
    public void setAttack(String attack) {
        this.attack = attack;
    }
    public String getDefense() {
        return defense;
    }
    public void setDefense(String defense) {
        this.defense = defense;
    }
    public String getSpecialAttack() {
        return specialAttack;
    }
    public void setSpecialAttack(String specialAttack) {
        this.specialAttack = specialAttack;
    }
    public String getSpecialDefense() {
        return specialDefense;
    }
    public void setSpecialDefense(String specialDefense) {
        this.specialDefense = specialDefense;
    }
    public String getSpeed() {
        return speed;
    }
    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
