package soccerleagues.com.control;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import soccerleagues.com.model.Game;
import soccerleagues.com.model.Team;

public class PlayLeague {
  private final int mAgainstEach;                           //used to ask for how many times each team will play one another
  private final Game[] mGames;
  private final LinkedHashMap<String, Team> mTeamsByName;

  
  public PlayLeague (LinkedHashMap<String, Team> teamsByName, Game[] games, int againstEach) {
    mTeamsByName = teamsByName;
    mGames = games;
    mAgainstEach = againstEach;
  }
  
  public void beginLeague() { 
    String home, away;
    
    for(int k=0; k<mAgainstEach; k++){                    //how many times will each team play every other team
      for(int i=0; i<mGames.length; i++){    
        if(k%2 == 0){                                    //swap home and away teams once all teams have played each other when k mod 2 is 0
          home = mGames[i].getHomeTeam();
          away = mGames[i].getAwayTeam();
        }
        else {         
          home = mGames[i].getAwayTeam();
          away = mGames[i].getHomeTeam();
        }
        
        PlayGame nextGame = new PlayGame(mTeamsByName, home, away, "league"); 
        LinkedHashSet<Integer> unUsed = nextGame.Play();
      }
    }
  }
}