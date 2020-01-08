
package miniproject;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class FixtureGenerator {
    
    //Importing csv file and saving its contents into an ArrayList
    public static List<Team> importFile(String filename) {
        List<Team> teams_list = new ArrayList<>();
    
        filename = filename.concat(".csv");
        Path filepath = Paths.get(System.getProperty("user.home"),"Desktop",filename);
        
        
        
       boolean filexist=Files.exists(filepath);
        if (filexist){
        int team_count = 0;
        
        try(BufferedReader br = Files.newBufferedReader(filepath, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while(line != null) {
                team_count++;
                String[] details = line.split(",");
                Team team = createTeam(details, team_count);
                teams_list.add(team);
                line = br.readLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        teams_list = sortTeams(teams_list);
        
        
        }else
        {
            System.out.println("file does not exist");
        }
        return teams_list;
    }
    
    //
    private static Team createTeam(String[] details, int team_ID) {
        String team_name = details[0];
        String team_location = details[1];
        String team_stadium = details[2];
        
        return new Team(team_ID, team_name, team_location, team_stadium);
    }
    
    public static List<Team> sortTeams(List<Team> teams_list) {
        for(int i = 0; i < teams_list.size(); i++) {
            for(int j = i + 1; j < teams_list.size(); j++) {
                if(teams_list.get(i).getTeam_location().equals(teams_list.get(j).getTeam_location())) {
                    int k = teams_list.size() - 1 - i;
                    int temp = teams_list.get(k).getTeam_ID();
                    int temp_two = teams_list.get(j).getTeam_ID();
                    teams_list.get(k).setTeam_ID(temp_two);
                    teams_list.get(j).setTeam_ID(temp);
                    Collections.swap(teams_list, k, j);
                    break;
                }
            }
        }
        
        return teams_list;
    }
    
    public static List<String> generateFixtures(List<Team> teams_list) {
        //this is the 'container' for all the fixtures generated
        List<String> all_fixtures = new ArrayList<>();
        
        //a container for the team IDs
        List<Integer> team_ID_list = new ArrayList<>();
        for(Team team : teams_list) {
            int team_ID = team.getTeam_ID();
            team_ID_list.add(team_ID);
        }
        Collections.sort(team_ID_list);
        
        //code to generate the fixtures
        //for n teams, where n is even, there are n-1 rounds; round-robin
        //we want 2(n-1) rounds as we have 2 legs, home and away
        for(int a = 1; a <= 2 * (team_ID_list.size() - 1); a++) {
            
            //shuffling our numbers
            for(int d = team_ID_list.size() - 1; d > 1; d--) {
                Collections.swap(team_ID_list, d, d-1);
            }
            
            //fixtures for each round generated here
            for(int b = 0; b < team_ID_list.size(); b++) {
                int temp = team_ID_list.get(b);
                for(int c = b + 1; c < team_ID_list.size(); c++) {
                    if(b + c == team_ID_list.size() - 1) {
                        String base = temp + " versus " + team_ID_list.get(c);
                        all_fixtures.add(base);
                    }
                }
            }
        }
        
        return all_fixtures;
        
    }
    
    public static List<List<String>> generateWeekends(List<String> all_fixtures, int matches) {
        if(all_fixtures.size() % matches != 0) {
            boolean looper = true;
            while(looper) {
                all_fixtures.add("Dummy fixture");
                looper = false;
            }
        }
        
        List<String> weekend_games = new ArrayList<>();
        List<List<String>> weekends_list = new ArrayList<>();
        
        int count = 0;
        for(String game : all_fixtures) {
            count++;
            weekend_games.add(game);
            if(count == matches) {
                weekends_list.add(weekend_games);
                weekend_games = new ArrayList<>();
                count = 0;
            }
        }
        
        return weekends_list;
    }
    
    //still thinking about what to do here
    public static void singleTeamFixtures() {
        
    }
    
    public static String allFixturesLayout(List<List<String>> weekends_list, List<Team> teams_list, int matches, String filename) {
        Team one = null, two = null;
        int game_no = 0;
        int gw_id = 0;
        
        //filename
        filename = filename.concat("_finalfixtures.csv");
        
        FileWriter output = null;
        
        try {
            File f = new File(new File("").getAbsolutePath(), filename);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("Weekend,Fixtures");
            writer.newLine();
            
            for(int i = 0; i < weekends_list.size(); i++) {
                System.out.println("Fixtures for weekend " + (i + 1) + ":");
                List<String> game_weekend = weekends_list.get(i);
                for(String game : game_weekend) {
                    gw_id++;
                    game_no++;
                    if("Dummy fixture".equals(game)) {
                        continue;
                    }
                    String[] teams_ID = game.split(" versus ");
                    int team_one = Integer.parseInt(teams_ID[0]);
                    int team_two = Integer.parseInt(teams_ID[1]);

                    for(Team t : teams_list) {
                        if(t.getTeam_ID() == team_one) {
                            one = t;
                        }
                        else if(t.getTeam_ID() == team_two) {
                            two = t;
                        }
                    }
                    if(game_no <= 45) {
                        String game_description = one.getTeam_name() + "(" + one.getTeam_ID() + ")" + " versus " + two.getTeam_name() + "(" + two.getTeam_ID() + ")" + " at " + one.getTeam_stadium();
                        System.out.println(game_description);
                        if(gw_id < matches) {
                            writer.write(i + "," +game_description);
                        }
                        else {
                            gw_id = 0;
                            writer.write("," + game_description);
                            writer.newLine();
                        }
                    } else {
                        String game_description = one.getTeam_name() + "(" + one.getTeam_ID() + ")" + " versus " + two.getTeam_name() + "(" + two.getTeam_ID() + ")" + " at " + two.getTeam_stadium();
                        System.out.println(game_description);
                        if(gw_id < matches) {
                            writer.write(i + "," + game_description);
                        } else {
                            gw_id = 0;
                            writer.write("," + game_description);
                            writer.newLine();
                        }
                    }
                    writer.newLine();
                }
                System.out.println();
            }
            
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return filename;
    }


}