/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject;


public class Team {
    
    private int team_ID;
    private String team_name;
    private String team_location;
    private String team_stadium;
    
    public Team() {
        team_ID = 0;
        team_name = "N/A";
        team_location = "N/A";
        team_stadium = "N/A";
    }
    
    public Team(int team_ID, String team_name, String team_location, String team_stadium) {
        this.team_ID = team_ID;
        this.team_name = team_name;
        this.team_location = team_location;
        this.team_stadium = team_stadium;
    }

    public int getTeam_ID() {
        return team_ID;
    }

    public void setTeam_ID(int team_ID) {
        this.team_ID = team_ID;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_location() {
        return team_location;
    }

    public void setTeam_location(String team_location) {
        this.team_location = team_location;
    }

    public String getTeam_stadium() {
        return team_stadium;
    }

    public void setTeam_stadium(String team_stadium) {
        this.team_stadium = team_stadium;
    }

    @Override
    public String toString() {
        return "Team{" + "team_ID=" + team_ID + ", team_name=" + team_name + ", team_location=" + team_location + ", team_stadium=" + team_stadium + '}';
    }
}
