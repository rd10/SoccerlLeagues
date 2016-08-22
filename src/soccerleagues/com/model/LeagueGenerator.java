package soccerleagues.com.model;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LeagueGenerator {  
  private final List<Team> mTeams = new ArrayList<>();
  private int mAmount;                                                    
  private int mRounds;                                                        
  private LinkedHashMap<String, Team> mTeamsByName = new LinkedHashMap<>();   //used to sort the teams in the Result class and display their information
                                                       
  public LeagueGenerator (LinkedHashMap<String, Team> teamsByName) {
    mTeamsByName = teamsByName;
  }
  
  //this method creates the schedule
  public Game[] matchTeams() {    
    mAmount = mTeamsByName.size();
    int matches = ((mAmount - 1) *  mAmount)/2;                        // (n*(n-1))/2   amount of matches
    Game[] mGames = new Game[matches];
    
    teamList(mTeamsByName);

    int count = mAmount - 1;                                                  // used to pair the teams
    int index = 0;                                                            // used to store the matches unto the games object array
    
    if(mAmount%2 == 0) mRounds = mAmount-1;                             //calculate the amount of rounds based on amount of teams
    else mRounds = mAmount;                                                   

      for(int j=0; j<mRounds; j++){
        for(int i=0; i<mAmount/2; i++) {                                      //mAmount/2 = amount of games per round
          Game game = new Game();
              
          if(i%2 != 0 || ( i==0 && j%2 != 0 && mAmount%2 == 0)){              // used so a team will play the same amount of games home and away
            game.setHomeTeam(mTeams.get(count).getName());
            game.setAwayTeam(mTeams.get(i).getName());
          }
          else{
            game.setHomeTeam(mTeams.get(i).getName());
            game.setAwayTeam(mTeams.get(count).getName());
          }

          mGames[index] = game;
        
          count --;
          index++;
        }        
        System.out.printf("\n");

        count = mAmount - 1;                    // count goes back to the last team in the array to begin matching teams again for the next round
        rotateTeams();                          // use the round robin method so every team will play a new team each round
      }
    
    return mGames;
  }
  
  //needed to implement an algorithim for a round robin tournament
  public void rotateTeams(){
    int rotateAt = 0;                          // if there is an odd number of teams rotate them all
    if(mAmount%2 == 0) rotateAt = 1;           // if there is an even numer of teams rotate all except the first one  
    
    Team temp;
    temp = mTeams.get(rotateAt);               // store the first team in a temporary location to no lose it when it's replaced
    for(int i=rotateAt; i<mAmount-1; i++) { 
      mTeams.set(i, mTeams.get(i+1));          // replace team at index k with team at index k+1
    }
    mTeams.set((mAmount-1), temp);
  }
  
    public void teamList(LinkedHashMap<String, Team> teamsByName){
    Team team;
    for(String name: teamsByName.keySet()){
      team = new Team(name);
      mTeams.add(team);
    }
  }
}