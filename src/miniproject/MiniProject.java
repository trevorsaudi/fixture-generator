/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject;



import static miniproject.FixtureGenerator.allFixturesLayout;
import static miniproject.FixtureGenerator.generateFixtures;
import static miniproject.FixtureGenerator.importFile;
import static miniproject.FixtureGenerator.generateWeekends;
import java.util.List;
import java.util.Scanner;


public class MiniProject {
    //CLI Code for user interacting with the app
    public static void main(String[] args) {
        
      start(); 
    }
    
    public static void start() {
       
        
        Scanner scan = new Scanner(System.in);
       
        String output1 = "enter the name of your csv file";
  
        System.out.println(output1);
        String filename = scan.nextLine();
        
        List<Team> teams_list = importFile(filename);
        List<String> all_fixtures = generateFixtures(teams_list);
        
        String output2 = "Please enter the number of matches per weekend:";
        System.out.println(output2);
        int matches = scan.nextInt();
        
        List<List<String>> weekends_list = generateWeekends(all_fixtures, matches);
        String output_file = allFixturesLayout(weekends_list, teams_list, matches, filename);
        
        String success = "Your fixtures have been generated.";
        System.out.println(success);
        System.out.println("Your output file is " + output_file);
      
    }
}