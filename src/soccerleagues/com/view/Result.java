package soccerleagues.com.view;

import java.util.LinkedHashMap;
import java.util.List;

import soccerleagues.com.model.Team;

public class Result {
  private final List<Team> mTeams;
  private Team team;
  private final LinkedHashMap<String, Team> mTeamsByName;  
  
  public Result(LinkedHashMap<String, Team> teamsByName, List<Team> teams) {
    mTeamsByName = teamsByName;
    mTeams = teams;
  }
  
  public void displayTable() {
    System.out.printf("\n%-20s %4s %4s %4s %4s %4s %4s %4s %4s \n", "Team", "G","W","D","L","Gf","Ga","Gd","Pts");
    System.out.println("--------------------------------------------------------\n");
    for(String name : mTeamsByName.keySet() ) {
      team = mTeamsByName.get(name);
      System.out.printf("%-20s %4s %4s %4s %4s %4s %4s %4s %4s \n", team.getName(), team.getGames(), team.getWins(), team.getDraws(), team.getLoses(), team.getGoalsFor(), team.getGoalsAgainst(), team.getGoalDifference(), team.getPoints());
    }
    System.out.println("\n");
  }
  
  //sort teams by points, goals and goal differential in decending order
  public void sortTable() {
      populateTeamList();
    for(int j=0; j<mTeams.size()-1; j++) {
      for(int i=0; i<mTeams.size()-1; i++){
        if((mTeams.get(i).getPoints() < mTeams.get(i+1).getPoints())
          ||
           ((mTeams.get(i).getPoints() == mTeams.get(i+1).getPoints()) &&     
            ((mTeams.get(i).getGoalDifference() < mTeams.get(i+1).getGoalDifference())          
            || ((mTeams.get(i).getGoalDifference() == mTeams.get(i+1).getGoalDifference()) &&    
                 (mTeams.get(i).getGoalsFor() < mTeams.get(i+1).getGoalsFor())
               )
            )
           )
          ){
          team = mTeams.get(i);
          mTeams.set(i, mTeams.get(i+1));
          mTeams.set(i+1, team);
        }
      }
    }
    System.out.println("made it here! too");

    //clear the linkedhashmap and add the teams in the array that are now sorted
    mTeamsByName.clear();
    for(Team team : mTeams){
      //System.out.printf("Name: %s\n", team.getName());
      mTeamsByName.put(team.getName(), team);
    } 
    displayTable();
  }
  
    public void win(String winner, String loser, int winnerGoals, int loserGoals){
    team = mTeamsByName.get(winner);
    team.setPoints(3);
      System.out.printf("%s points: %s \n", team.getName(), team.getPoints());
    team.setGames(1);
    team.setWins(1);
    team.setGoalsFor(winnerGoals);
    team.setGoalsAgainst(loserGoals);
    team.setGoalDifference(winnerGoals - loserGoals);
                            //System.out.printf("points earned by %s: %s\n", team.getName(), team.getPoints());

    team = mTeamsByName.get(loser);
    team.setGames(1);
    team.setLoses(1);
    team.setGoalsFor(loserGoals);
    team.setGoalsAgainst(winnerGoals);
    team.setGoalDifference(loserGoals - winnerGoals);
    sortTable();
  }
    
  public void draw(String home, String away, int tie){
    team = mTeamsByName.get(home);
    for(int i=0; i<2; i++) {  //update team statistics for both team
      team.setPoints(1);
      team.setGames(1);
      team.setDraws(1);
      team.setGoalsFor(tie);
      team.setGoalsAgainst(tie);
      team = mTeamsByName.get(away);
    }
    sortTable();
  }
  
  public void populateTeamList(){
    mTeams.clear();
    Team team;
    for(String name: mTeamsByName.keySet()){
      team = mTeamsByName.get(name);
      mTeams.add(team);
    }
  }
}