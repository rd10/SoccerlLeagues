
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

import soccerleagues.com.model.LeagueGenerator;
import soccerleagues.com.model.Game;
import soccerleagues.com.model.Knockout;
import soccerleagues.com.model.Team;
import soccerleagues.com.control.PlayLeague;

//package soccerleagues;
public class SoccerLeagues {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);    // Reading from System.in

        LinkedHashMap<String, Team> mTeamsByName;   //use to sort the teams in the Result class and display their information
        Game[] games;
        int againstEach;
        char fin = 'y', awayGoal = 'n';             //these might change to yes in a tournament with a knockout

        Map<Integer, String> mMenu = new HashMap<>();
        mMenu.put(0, "Quit the program");
        mMenu.put(1, "Play a league tournament");
        mMenu.put(2, "Play a Knockout tournament");
        mMenu.put(3, "Play a group knockout tournament");

        //print the menu
        for(Map.Entry<Integer, String> option : mMenu.entrySet()) {
            System.out.printf("%s - %s %n",
                    option.getKey(),
                    option.getValue());
        }

        System.out.println("What type of tournament do you want to play: ");
        int choice = reader.nextInt();
        do {
            switch (choice) {
                case 1:
                    mTeamsByName = setUp(2, 26, "league");
                    LeagueGenerator league = new LeagueGenerator(mTeamsByName);
                    games = league.matchTeams();
                    againstEach = promptForAgainstEach("league");
                    PlayLeague playLeague = new PlayLeague(mTeamsByName, games, againstEach);
                    playLeague.beginLeague();
                    break;
                case 2:
                    mTeamsByName = setUp(2, 32, "knockout");
                    againstEach = promptForAgainstEach("knockout");
                    if(againstEach > 1 ) {  
                        if(againstEach%2 == 0) awayGoal = promptForAwayGoal(); //if there is an even amount of matches prompt for awayGoal usage
                        if(mTeamsByName.size() > 2 )fin = promptForFinal(againstEach);
                    }
                    Knockout knockout = new Knockout(mTeamsByName, againstEach, fin, awayGoal);
                    knockout.play();
                    break;
                case 3: //TODO; create a group knockout tournament
                    // match = new Match(4,64, group);
                    break;
                case 0:
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.printf("Unknown choice '%s' . Try again. %n%%n%n", choice);
            }
        } while (choice != 0);

    }

    public static int promptForAgainstEach(String tournament) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int mAgainstEach = 1;
        boolean valid = false;
        
        while (!valid) {
            if (tournament.equals("knockout")) {
                System.out.print("How many games versus each team? (must be between 1 and 2): ");
                mAgainstEach = reader.nextInt();
                valid = isRangeValid(1, 2, mAgainstEach);
            } else {
                System.out.print("How many games versus each team? (must be between 1 and 4): ");
                mAgainstEach = reader.nextInt();
                valid = isRangeValid(1, 4, mAgainstEach);
            }
        }
        return mAgainstEach;
    }

    public static char promptForFinal(int mAgainstEach){
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.printf("Will the Final be %s matches? ", mAgainstEach);
        System.out.printf("Type 'y' to keep %s matches in the final or 'n' to have only one match in the final: ", mAgainstEach);
        char fin = validateGuess(reader.next().trim().charAt(0));    
        return fin;
    }
    
    public static char promptForAwayGoal(){
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Will away goal be used as a tiebreaker?");
        System.out.print("Type y to enable awayGoal as tiebreaker or n to go to penalties instead: ");
        char answer = validateGuess(reader.next().trim().charAt(0));    
        return answer;
    }
    
    public static LinkedHashMap<String, Team> setUp(int min, int max, String tournament) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        LinkedHashMap<String, Team> mTeamsByName = new LinkedHashMap<>();
        int amount = -1;
        boolean valid = false;

        //prompt for amount of teams
        while (!valid) {
            System.out.printf("How many teams? (must be between 2 and 32): ", min, max);
            amount = reader.nextInt();
            valid = isRangeValid(min, max, amount);

            if (tournament.equals("knockout")) {        //for a knockout tournament the amount of teams must be even and 2^n
                valid = false;
                for (int i = 0; i < 5; i++) {           //2^5 = 32
                    if (Math.pow(2, i + 1) == amount) {
                        valid = true;
                    }
                }
                if (valid == false) {
                    System.out.println("The number of teams must be 2^n for example: 2,4,8,16,32 \n");
                }
            }
        }
        
        //prompt for the name of teams    
        for (int i = 0; i < amount; i++) {
            String name = isNameValid(i);
            for (String takenName : mTeamsByName.keySet()) {       //check that the name ihas not been taken already
                while (takenName.equals(name)) {
                    System.out.println("A team already contains that name");
                    name = isNameValid(i);
                }
            }
            Team team = new Team(name);
            mTeamsByName.put(name, team);
        }
        return mTeamsByName;
    }

    public static boolean isRangeValid(int minimum, int maximum, int input) {
        boolean valid = true;
        if (input < minimum || maximum < input) {
            valid = false;
        }
        return valid;
    }

    public static String isNameValid(int i) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        boolean valid = false;
        String teamName = "";

        while (!valid) {
            System.out.printf("Input the name for team %s: ", i + 1);
            teamName = reader.next().trim();
            valid = isRangeValid(1, 25, teamName.length());    //check that the name length is valid
        }
        return teamName;
    }

    public static int isInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }

    private static char validateGuess(char letter) {
        if (!Character.isLetter(letter)) {
            throw new IllegalArgumentException("A letter is required");
        }
        letter = Character.toLowerCase(letter);
        if (letter != 'n' && letter != 'y') {
            throw new IllegalArgumentException(letter + " is an invalid letter");
        }
        return letter;
    }
}
