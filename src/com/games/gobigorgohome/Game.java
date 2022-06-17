package com.games.gobigorgohome;

import com.apps.util.Console;
import com.apps.util.Prompter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Game {

    boolean isGameOver = false;
    private final Gym gym = Gym.getInstance();
    private Player player = new Player();
    private int energy = player.getEnergy();
    private int currentEnergy = player.getEnergy();
    private String playerName = player.getName();
    private String currentRoomName = gym.getStarterRoomName();
    private JSONObject currentRoom = gym.getStarterRoom();
    private JSONObject rooms = gym.getRooms();
    private Prompter prompter;
    private SplashPage page = new SplashPage();
    private ParseJSON jsonParser = new ParseJSON();
    private Double playerHeight = player.getHeight();
    private Double playerWeight= player.getWeight();
    private int playerAge = player.getAge();

    public Game(Prompter prompter) throws IOException, ParseException {
        this.prompter = prompter;
    }


    //    collects current input from user to update their avatar
    private void getNewPlayerInfo(){
//        TODO: validate user input
        validName();
        validDouble("Height", "What is your height? ", "height", "inches");
        validDouble("Weight","What is your weight? ", "weight", "pounds");
        validInt("Age", "What is your age? ", "age", "years");
        createPlayer(playerName, playerAge, playerHeight, playerWeight);
    }

    private void createPlayer(String playerName, int playerAge, double playerHeight, double playerWeight) {
        player.setName(playerName);
        player.setAge(playerAge);
        player.setHeight(playerHeight);
        player.setWeight(playerWeight);
    }

    //    updates player with current game status e.g. player inventory, current room etc.
    private void gameStatus(){
        System.out.println("------------------------------");
        System.out.println("Player: " + player.getName());
        System.out.println("You are in the " + currentRoomName);
        System.out.println(player.getName() + ", you have " + player.getInventory() + " in your gym bag.");
        System.out.println("Your current energy level is " + getCurrentEnergy() + " out of " + getEnergy());
        System.out.println("------------------------------");
    }

    //    main function running the game, here we call all other functions necessary to run the game
    public void playGame() throws IOException {
        page.instructions();
        getNewPlayerInfo();
        // runs a while loop
        while (!isGameOver()) {
            gameStatus();
            Console.clear();
            String[] command = promptForPlayerInput();
            parsingThroughStringValues(command);
            if (checkGameStatus()){
                break;
            }
            updateGame();
        }
        gameResult();
//        playAgain();
    }

    private boolean checkGameStatus() {
        return player.isWorkoutComplete();
    }

    private void playAgain() {
    }

    private void gameResult() {
        Console.clear();
        System.out.println("Game over");
    }

    //this method will handle the user's input for the action
//    and the according methods called for whatever action
    private void updateGame() {
    }


    private void validateInput() {
    }

    public String[] returningInputFromStringAsSplitArray(String command){
        // String s2 = in.nextLine();
        return command.split(" ");
    }

    public String[] promptForPlayerInput(){
        String command = prompter.prompt("(Hit Q to quit) What is your move? ");
        String[] commandArr = returningInputFromStringAsSplitArray(command);
        quit(command);
//        code to be tested
//        commandArr = validatePLayerBeginningCommand(commandArr);
        return commandArr;
    }

//    private String[] validatePLayerBeginningCommand(String[] userCommand) {
//        String[] validWords = {"go", "use", "consume", "inspect", "get"};
////        we're converiting the array to a list, a using the contains method to see if the submitted usercommand is inside of it.
//        boolean contains = Arrays.asList(validWords).contains(userCommand[0]);
////        if contains IS false THEN we just call the validate method.
//        if(!contains){
//            System.out.println(userCommand[0] + " was sadly and invalid answer. \n please use one of the following: " + Arrays.toString(validWords));
//            promptForPlayerInput();
//        }
//        return userCommand;
//    }

    public void parsingThroughStringValues(String[] action){

        List<String> actionList = Arrays.asList(action);
//        String actionPrefix = actionList.get(0);
//        String playerAction = actionList.get(1);
        String actionPrefix = "";
        String playerAction = "";

        if(actionList.size() >= 1) {
            actionPrefix = actionList.get(0);
        }

        if(actionList.size() == 2){
            playerAction = actionList.get(1);
        } else if(actionList.size() == 3){
            playerAction = (actionList.get(1) + " " + actionList.get(2)) ;
        }

        System.out.println(playerAction + "DEBUG");

        if (actionPrefix.equals("get")){
            grabItem(playerAction);

        }else if(actionPrefix.equals("go")){
            System.out.println("you're going here: "+ playerAction);
            currentRoomName = playerAction;
            setCurrentRoom(rooms.get(playerAction));

        }else if(actionPrefix.equals("workout")){
            playerUseMachine(playerAction);

        }else if(actionPrefix.equals("use")) {
            if (player.useItem(playerAction, currentRoom)) {
                player.removeItemFromInventory(playerAction);
            }

        } else if(actionPrefix.equals("consume")){
            if (player.consumeItem(playerAction)) {
                player.removeItemFromInventory(playerAction);
            }

        } else if(actionPrefix.equals("inspect")){
            inspectRoom();
        } else {
//            TODO: add array with possible values for commands
            System.out.println(actionPrefix + " was sadly and invalid answer. \n please use one of the following: " );
//            TODO: fix bug caused by pressing enter where prompt for player does not work and calls inspect
            promptForPlayerInput();
        }
    }

    private void inspectRoom() {
        // room name
        System.out.println("You are in " + currentRoomName);

        // exercises
        JSONObject exercises = jsonParser.getJSONObjectFromJSONObject(currentRoom, "exercises");
        System.out.println("Exercises available are: " + exercises.keySet());

        // items
        JSONArray items = jsonParser.getJSONArrayFromJSONObject(currentRoom, "items");
        System.out.println("You see: " + items);

        // TODO: NPCs

        // TODO: Possible Directions
        JSONArray directions = jsonParser.getJSONArrayFromJSONObject(currentRoom, "directions");
        System.out.println("You can go to " + directions);

        // TODO: add information about individual machines
    }

    private void setCurrentRoom(Object currentRoom) {

        this.currentRoom = (JSONObject) currentRoom;
    }

    private void playerUseMachine(String playerExcerciseInput) {
        System.out.println("you're using the: "+ playerExcerciseInput);
        JSONObject exercises = jsonParser.getJSONObjectFromJSONObject(currentRoom, "exercises");
        JSONObject exercise = jsonParser.getJSONObjectFromJSONObject(exercises, playerExcerciseInput);
        JSONArray targetMuscle = jsonParser.getJSONArrayFromJSONObject(exercise, "target muscles");
        String exerciseStatus = (String) exercise.get("status");
//        Integer energyCost = (Integer) exercise.get("energyCost");
        int energyCost = 5;
        if ("fixed".equals(exerciseStatus)) {
            player.workout(targetMuscle, energyCost);
        }

        //        TODO: validate for broken machines -> when player tries to use machine they are prompted to use wrench from inventory
        boolean isMachineBroken = true;

//        Map<String, Object> bodyAndMachineEnergy = currentRoom.get("exersices");

//        String muscleGroup = exercises.get("target muscle")[0];
//        int energyCost = Integer.parseInt(exercises.get("energy cost"));
//        String machineStatus = exercises.get("status");
//
//        player.useMachine(muscleGroup, machineStatus, energyCost);
    }

    private void grabItem(String playerAction) {
        System.out.println("you got the :"+ playerAction);
        player.getInventory().add(playerAction);
    }

    //    returns whether game is over or not.
    private void isGameOver(boolean currentGameStatus){
            setGameOver(currentGameStatus);
    }

    //    gives player ability to quit
    private void quit(String command){
        if(command.equalsIgnoreCase("q")){
            Console.clear();
            System.out.println("You are a quitter!...GAME OVER");
            System.exit(0);
        }
    }

    // validates name requesting one and rejecting empty space(s).
    private void validName(){
        playerName = prompter.prompt("What is your name? ");
        if( playerName.isBlank() || playerName.isEmpty() || playerName.length()>16) {
            try {
                System.out.println("You need to type your name or it exceeds 16 characters: ");
                validName();
            } catch (NullPointerException e) {
                System.out.println("You need to type your name: ");
                validName();
            }
        }
        else {
            System.out.println("Hello " + playerName +" let's get more about you...");
        }
    }


    // validates height and weight taking integers or doubles only
    private void validDouble(String measure, String msg, String measureName, String unit) {
        measure = prompter.prompt(msg);

        if(measure.isBlank() || measure.isEmpty() || measure.length()>=1) {
            try {
                Double measureNum = Double.parseDouble(measure);
                //validDouble(measure, "you need to type your " + measureName + " in " + unit + ": ", measureName, unit);
            } catch (NumberFormatException e) {
                validDouble(measure, "You need to type your " + measureName + " using numbers (" + unit + "): ", measureName, unit);
            } catch (NullPointerException e) {
                validDouble(measure, "You need to type your " + measureName + " using numbers (" + unit + "): ", measureName, unit);
            }
        }
        else{
                Double measureNum = Double.parseDouble(measure);
                System.out.println("ok, almost there " + playerName);
            }
        }


    // validates age taking only an integer
    private void validInt(String measure, String msg, String measureName, String unit) {
        measure = prompter.prompt(msg);

        if(measure.isBlank() || measure.isEmpty() || measure.length()>=1){
            try {
                Integer measureNum = Integer.parseInt(measure);
                //validInt(measure, "you need to type your "+ measureName+" in " + unit + " or you aren't an adult: ", measureName, unit);
            }
            catch (NumberFormatException e) {
                validInt(measure, "You need to type your "+ measureName + " using numbers integers ("+unit+"): ",measureName,unit);
            }
        }else {
            System.out.println("thanks, now... lets start "+ playerName);
        }

    }



    public JSONObject getCurrentRoom() {
        return currentRoom;
    }


    // accessor methods
    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }


    public boolean isGameOver() {
        return isGameOver;
    }

    public int getEnergy() {
        return energy;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Prompter getPrompter() {
        return prompter;
    }
}


