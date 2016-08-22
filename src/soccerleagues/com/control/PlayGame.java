package soccerleagues.com.control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

import soccerleagues.com.model.Team;
import soccerleagues.com.view.Result;

public class PlayGame {
  private final LinkedHashMap<String, Team> mTeamsByName;   //   = new LinkedHashMap<>();  //used to get the teams that played and update their ifo   
  private final List<Team> mTeams = new ArrayList<>();
  private Result result;
  private final LinkedHashSet<Integer> setGoals = new LinkedHashSet<>();
  
  private final String mHome;
  private final String mAway;
  private final String mTournamentType;
  
  public PlayGame(LinkedHashMap<String, Team> teamsbyName, String home, String away, String tournamentType) {
    mTeamsByName = teamsbyName;
    mHome = home;
    mAway = away;
    mTournamentType = tournamentType;
  }
  
  public LinkedHashSet Play() {
    Scanner reader = new Scanner(System.in);  // Reading from System.in

    teamList();
    result = new Result(mTeamsByName, mTeams);

    System.out.printf("%s vs %s\n", mHome, mAway);
    System.out.printf("Input the goals for %s: ", mHome);
    int homeScore = reader.nextInt(); 
    System.out.printf("Input the goals for %s: ", mAway);
    int awayScore = reader.nextInt();
    
    while(homeScore < 0 || 40 < homeScore) {
        System.out.printf("Input the goals for %s: ", mHome);
        homeScore = reader.nextInt();
    }
    while(awayScore < 0 || 40 < awayScore) {
        System.out.printf("Input the goals for %s: ", mAway);
        awayScore = reader.nextInt();
    }
    
    setGoals.add(homeScore);
    setGoals.add(awayScore);
    if(homeScore > awayScore) {
      String winner = mHome;
      String loser = mAway;
      if(!mTournamentType.equals("knockout")) {
        result.win(winner, loser, homeScore, awayScore);
        return setGoals;
      }
      else return setGoals; 
    }
    else if(awayScore > homeScore) {
      String winner = mAway;
      String loser = mHome;
      if(!mTournamentType.equals("knockout")) {
        result.win(winner, loser, awayScore, homeScore);
        return setGoals;
      }
       else return setGoals;     
    }
    else{
      if(!mTournamentType.equals("knockout")) {
        result.draw(mHome, mAway, homeScore);
      }
      return setGoals;
    }   
  }
  
  public void teamList(){
    Team team;
    for(String name: mTeamsByName.keySet()){
      team = new Team(name);
      mTeams.add(team);
    }
  }
    
  public static int isInteger(String str) {
    try {
        return Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
        return -1;
    }
  }  
}