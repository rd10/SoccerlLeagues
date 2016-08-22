package soccerleagues.com.model;

public class Team {
  private String mName;
  private int mPoints;
  private int mGames;
  private int mWins;
  private int mDraws;
  private int mLoses;
  private int mGoalsFor;
  private int mGoalsAgainst;
  private int mGoalDifference;
  
  public Team(String name) {
    mName = name;
    mPoints = 0;
    mGames = 0;
    mWins = 0;
    mDraws = 0;
    mLoses = 0;
    mGoalsFor = 0;
    mGoalsAgainst = 0;
    mGoalDifference = 0;
  }
  
  public void setName(String name) {
    mName = name;
  }
  
  public String getName(){
    return mName;
  }
  
  public void setPoints(int points) {
    mPoints += points;
  }
  
  public int getPoints() {
    return mPoints;
  }
  
  public void setGames(int games) {
    mGames += games;
  }
  
  public int getGames() {
    return mGames;
  }
  
  public void setWins(int wins) {
    mWins += wins;
  }
  
  public int getWins() {
    return mWins;
  }
  
  public void setDraws(int draws){
    mDraws += draws;
  }
  
  public int getDraws() {
    return mDraws;
  }
  
  public void setLoses(int loses) {
    mLoses += loses;
  }
  
  public int getLoses() {
    return mLoses;
  }
  
  public void setGoalsFor(int goalsFor){
    mGoalsFor += goalsFor;
  }
  
  public int getGoalsFor() {
    return mGoalsFor;
  }
  
  public void setGoalsAgainst(int goalsAgainst) {
    mGoalsAgainst += goalsAgainst;
  }
  
  public int getGoalsAgainst() {
    return mGoalsAgainst;
  }
  
  public void setGoalDifference(int goalDifference) {
    mGoalDifference += goalDifference;
  }
  
  public int getGoalDifference() {
    return mGoalDifference;
  }

}