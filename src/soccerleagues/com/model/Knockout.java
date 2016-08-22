package soccerleagues.com.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import soccerleagues.com.control.PlayGame;

public class Knockout {

    private final LinkedHashMap<String, Team> mTeamsByName;        //get the team based on its name by the order in which they were put in
    private int mAgainstEach;
    private String mHome, mAway;
    private final char mFin, mAwayGoal;

    public Knockout(LinkedHashMap<String, Team> teamsByName, int againstEach, char fin, char awayGoal) {
        mTeamsByName = teamsByName;
        mAgainstEach = againstEach;
        mFin = fin;
        mAwayGoal = awayGoal;
    }

    public void play() {
        int size = mTeamsByName.size();
        int originalHome = 0, originalAway = 0, hAwayGoals = 0, aAwayGoals = 0, tie;
        int rounds = size/2;
       // int[] goals;
        
        LinkedHashSet<Integer> Goals;
        String advance = "";

        String[] teams = new String[size];
        int m = 0;
        for (String name : mTeamsByName.keySet()) {
            teams[m] = name;
            m++;
        }

        for (int j = 0; j < rounds; j++) {                // this is how many rounds there will be (ex. quarterfinals(3), semifinals(2))
            int count = size-1;
            if(mFin != 'y' && j == rounds-1){             //make the final only one game if the user wanted to.
                mAgainstEach = 1;
            }
            for (int i = 0; i < size/2; i++) {              // how many games each round
                mHome = teams[i];
                mAway = teams[count];

                for (int k = 0; k < mAgainstEach; k++) {
                    if (k%2 != 0)  swap(mHome, mAway); 
                    PlayGame knockoutGame = new PlayGame(mTeamsByName, mHome, mAway, "knockout");
                    Goals = knockoutGame.Play();
                    Iterator<Integer> itr = Goals.iterator();
                    
                   if (k%2 == 0) {                              //when k is odd the original home team plays at home
                       if(Goals.size() == 1) {                  //when size is 1 then it was a tie.
                           tie = itr.next();
                           originalHome += tie;
                           originalAway += aAwayGoals = tie;
                       } else{
                            originalHome += itr.next();
                            originalAway += aAwayGoals = itr.next();
                       }
                   } else{
                       if(Goals.size() == 1) {
                           tie = itr.next();
                           originalHome += tie;
                           originalAway += hAwayGoals = tie;
                        } else {
                           originalAway += itr.next();                  //the original away tema is now the home team
                           originalHome += hAwayGoals = itr.next();
                       }
                   }
                    if (k % 2 != 0) swap(mHome, mAway);
                    Goals.clear();
                    tie = 0;
                }

                if (originalHome > originalAway) {
                    advance = mHome;
                }
                else if(originalHome < originalAway){
                    advance = mAway;                    
                }
                else{
                    if(mAwayGoal == 'y' && hAwayGoals > aAwayGoals) advance = mHome;
                    else if(mAwayGoal == 'y' && aAwayGoals > hAwayGoals) advance = mAway;
                    else advance = penalties(mHome, mAway);
                }

                teams[i] = advance;
                originalHome = 0;
                originalAway = 0;
                count--;
            }
            size /= 2;    //half the teams will be have been eliminated
        }
        System.out.printf("Champion! %s\n", teams[0]);
    }

    public String penalties(String home, String away) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        String winner;

        System.out.printf("How many penalties did %s score: ", home);
        int hPenalties = reader.nextInt();
        System.out.printf("How many penalties did %s score: ", away);
        int aPenalties = reader.nextInt();

        while (hPenalties == aPenalties) {
            System.out.println("The teams cannot have the same amount of penalties scored. There must be a winner\n");
            System.out.printf("How many penalties did %s score: ", away);
            aPenalties = reader.nextInt();
        }
        if (hPenalties > aPenalties) {
            winner = home;
        } else {
            winner = away;
        }

        return winner;
    }

    public void swap(String home, String away) {
        String temp;
        temp = home;
        mHome = away;
        mAway = temp;
    }

}
