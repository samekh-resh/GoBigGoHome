package com.games.gobigorgohome;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
//    idea- creating a class that's parsing through the json? and putting certain information into lists mainly lists of the muscles and the exercises that work them out.
//    static variables
    public final static int BASE_ENERGY = 50; //example not set in stone


//    fields
    private int age;
    private String name;
    private int energy =100;
    private double weight;
    private double height;
    private List<String> inventory = new ArrayList<>();
//    just realize that the boolean values are named the same for the getters, idk why but they did it when I did it with the intellij autgenerated ones
    private boolean isChestWorked = false;
    private boolean isTricepsWorked = false;
    private boolean isLegsWorked = false;
    private boolean isBackWorked = false;
    private boolean isCoreWorked = false;
    private boolean isShoulderWorked = false;
    private boolean isBicepsWorked = false;
    private boolean isSteroidsUsed = false;

//    constructors
    public Player(){
    }

    public Player( String name, int age, double weight, double height) {
        this.age = age;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.energy = BASE_ENERGY;
    }

    public Player(String name, int age,  double weight, double height, int energyInput) {
        this( name,age, weight, height);
        this.energy = energyInput;
    }

    //    business methods

//    we need to think about what values are being played around with or manipulated that can be tested?
    public void workout(JSONArray muscleGroup, int energyCost){
        String targetMuscle = (String) muscleGroup.get(0);
        if(Objects.equals(targetMuscle, "chest")) {
            setChestWorked(true);
        } else if (Objects.equals(targetMuscle, "back")) {
            setBackWorked(true);
        } else if (Objects.equals(targetMuscle, "core")) {
            setCoreWorked(true);
        } else if (Objects.equals(targetMuscle, "triceps")) {
            setTricepsWorked(true);
        } else if (Objects.equals(targetMuscle, "legs")) {
            setLegsWorked(true);
        } else if (Objects.equals(targetMuscle, "biceps")) {
            setBicepsWorked(true);
        } else if (Objects.equals(targetMuscle, "shoulders")) {
            setShoulderWorked(true);
        }
        subtractFromPlayerEnergy(energyCost);
    }

    private void setBicepsWorked(boolean bicepsWorked) {
        this.isBicepsWorked = bicepsWorked;
    }

    public boolean isWorkoutComplete() {
        return isChestWorked && isCoreWorked && isLegsWorked && isTricepsWorked && isBackWorked;
    }
//    i think this needs to be in the game class or the gym class because it relates to the item being returned in response to the rooms too
    public String moveRooms(String action){
        String newRoom = "default";
        return newRoom;
    }

    public String addItemToInventory(String item) {

//        mayybe this contains method could be a method itself.
        if(isItemInInventory(item) == false){
            getInventory().add(item);
        }
        return item;
    }

    private boolean isItemInInventory(String item){
        return getInventory().contains(item);
    }

    public Boolean useItem(String item, JSONObject room ) {
        Boolean isItemConsumed = false;
        if(isItemInInventory(item)){
            boolean isThisItemRequired = isItemRequired( item, room );
            if(item.equals("key") && isThisItemRequired){
                isItemConsumed = true;
                removeItemFromInventory(item);
            }else if(item.equals("wrench") && isThisItemRequired){
                isItemConsumed = true;
                removeItemFromInventory(item);
            }
        }else{
            System.out.println("oi mate! that's not in your inventory");
        }
        //the idea is to return the string for validation purposes
        return isItemConsumed;
    }

    public boolean isItemRequired(String item, JSONObject room){
        boolean result = false;
        JSONArray required_items= (JSONArray) room.get("required items");

        if(required_items.contains(item)){
            result = true;
        }
        return result;
    }
    public String removeItemFromInventory(String item){
        getInventory().remove(item);
        return item;
    }
    public void talkTo() {
        // talk to npc
    }
//    there's a bit of code redundancy here so there could possible be a way to morph these together
    public int addToPlayerEnergy(int energyToGive) {
        int num = getEnergy() + energyToGive;
        setEnergy(num);
        return getEnergy();
    }

    public int subtractFromPlayerEnergy(int energyToTake) {
        int num = getEnergy() + energyToTake;
        setEnergy(num);
        return getEnergy();
    }

    public boolean consumeItem(String item ) {

        boolean couldYouConsume = false;
//        this could be replaced with a try catch but it would have to have an exception in it.

            if(item.equals("energy drink")){
                System.out.println("Ahh yeah man more energy to work out!!");
                addToPlayerEnergy(5); // hard coded value that we can talk about later
                couldYouConsume = true;
            }else if (item.equals("steroids")){
                hasPlayerUsedSteroids(true);
                System.out.println("GAME OVER");
                couldYouConsume= true;
            }else {
                System.out.println("oi mate! that's not in your inventory");
            }

        return couldYouConsume;

    }



    public boolean hasPlayerUsedSteroids(boolean value){
//        if this value is true, then we need to set the steroid to the value and then return sterod
        boolean returnValue = false;
        //if the value is true, which means player used steroids
        if(value){
//           call the setter
            setSteroidsUsed(true);
            returnValue = isSteroidsUsed();
        }
        return  returnValue;
    }


//    accessor methods

    public boolean isSteroidsUsed() {
        return isSteroidsUsed;
    }

    public void setSteroidsUsed(boolean steroidsUsed) {
        isSteroidsUsed = steroidsUsed;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }

    public boolean isChestWorked() {
        return isChestWorked;
    }

    public void setChestWorked(boolean chestWorked) {
        isChestWorked = chestWorked;
    }

    public boolean isTricepsWorked() {
        return isTricepsWorked;
    }

    public void setTricepsWorked(boolean tricepsWorked) {
        isTricepsWorked = tricepsWorked;
    }

    public boolean isLegsWorked() {
        return isLegsWorked;
    }

    public void setLegsWorked(boolean legsWorked) {
        isLegsWorked = legsWorked;
    }

    public boolean isBackWorked() {
        return isBackWorked;
    }

    public void setBackWorked(boolean backWorked) {
        isBackWorked = backWorked;
    }

    public boolean isCoreWorked() {
        return isCoreWorked;
    }

    public void setCoreWorked(boolean coreWorked) {
        isCoreWorked = coreWorked;
    }

    public boolean isShoulderWorked() {
        return isShoulderWorked;
    }

    public void setShoulderWorked(boolean shoulderWorked) {
        isShoulderWorked = shoulderWorked;
    }


//    toString


    @Override
    public String toString() {
        return "Player{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", energy=" + energy +
                ", weight=" + weight +
                ", inventory=" + inventory +
                ", isChestWorked=" + isChestWorked +
                ", isTricepsWorked=" + isTricepsWorked +
                ", isLegsWorked=" + isLegsWorked +
                ", isBackWorked=" + isBackWorked +
                ", isCoreWorked=" + isCoreWorked +
                ", isShoulderWorked=" + isShoulderWorked +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Player player = (Player) o;
//
//        if (age != player.age) return false;
//        if (energy != player.energy) return false;
//        if (Double.compare(player.weight, weight) != 0) return false;
//        if (isChestWorked != player.isChestWorked) return false;
//        if (isTricepsWorked != player.isTricepsWorked) return false;
//        if (isLegsWorked != player.isLegsWorked) return false;
//        if (isBackWorked != player.isBackWorked) return false;
//        if (isCoreWorked != player.isCoreWorked) return false;
//        if (isShoulderWorked != player.isShoulderWorked) return false;
//        if (!name.equals(player.name)) return false;
//        return inventory.equals(player.inventory);
//    }
//
//    @Override
//    public int hashCode() {
//        int result;
//        long temp;
//        result = age;
//        result = 31 * result + name.hashCode();
//        result = 31 * result + energy;
//        temp = Double.doubleToLongBits(weight);
//        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        result = 31 * result + inventory.hashCode();
//        result = 31 * result + (isChestWorked ? 1 : 0);
//        result = 31 * result + (isTricepsWorked ? 1 : 0);
//        result = 31 * result + (isLegsWorked ? 1 : 0);
//        result = 31 * result + (isBackWorked ? 1 : 0);
//        result = 31 * result + (isCoreWorked ? 1 : 0);
//        result = 31 * result + (isShoulderWorked ? 1 : 0);
//        return result;
//    }
}