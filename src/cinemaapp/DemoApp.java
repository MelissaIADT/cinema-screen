package cinemaapp;

import java.util.List;
import java.util.Scanner;

public class DemoApp {
    
     public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        
        Model model = Model.getInstance();
        
        Screen s;
        
        int opt;
        do {
            System.out.println("1. Create new Screen");
            System.out.println("2. Delete exisiting Screen");
            System.out.println("3. View all Screens");
            System.out.println("4. Edit exisiting screens");
            System.out.println("5. Exit");
            System.out.println();
            
            System.out.println("Enter option: ");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);
            
            System.out.println("You chose option " + opt);
            switch (opt){
                case 1: {
                    System.out.println("Creating screen..");
                    createScreen(keyboard, model);
                    break;
                }
                
                case 2: {
                    System.out.println("Deleting screen..");
                    deleteScreen(keyboard, model);
                    break;
                }
                
                case 3: {
                    System.out.println("Viewing screens..");
                    viewScreens(model);
                    break;
                }
                
                case 4: {
                    System.out.println("Editing screen..");
                    editScreen(keyboard, model);
                    break;
                }
            }
        }
        while (opt != 5);
        System.out.println("Goodbye! :)");
     }
     
     //Create
     private static void createScreen(Scanner keyboard, Model model){
        Screen s = readScreen(keyboard);
            if(model.addScreen(s)) {
                System.out.println("Screen added to database.");
                }
                else {
                    System.out.println("Screen not added to database.");
                }
                System.out.println();
     }
     
     //Delete
     private static void deleteScreen(Scanner kb, Model m){
         System.out.println("Enter the ID of the Screen to delete:");
         int id = Integer.parseInt(kb.nextLine());
         Screen s;
         
         s = m.findScreenByScreenId(id);
         if (s != null){
             if(m.removeScreen(s)){
                 System.out.println("Screen deleted");
             }
             else{
                 System.out.println("Screen not deleted");
             }
         }
         else{
             System.out.println("Screen not found");
         }
     }
     
     //Edit
     private static void editScreen(Scanner keyboard, Model model) {
         System.out.print("Enter the ID of the screen to edit:");
         int id = Integer.parseInt(keyboard.nextLine());
         Screen s;
         
         s = model.findScreenByScreenId(id);
         if(s != null){
             editScreenDetails(keyboard, s);
             if(model.updateScreen(s)){
                 System.out.println("Screen updated!");
             }
             else{
                 System.out.println("Screen not updated.");
             }
         }
     }
     
     //View
     private static void viewScreens(Model model){
        List<Screen> screendb = model.getScreendb();
        System.out.println();
        if(screendb.isEmpty()){
            System.out.println("There are no screens in the database.");
        }
        else{
            System.out.printf("%5s %20s %15s %15s %20s %12s\n", "Id", "Name", "Number of seats", "Number of exits", "Date of next inspection", "Projector Type");
            for(Screen ss : screendb){
                System.out.printf("%5d %20s %15d %15d %20s %12s\n",
                        ss.getId(),
                        ss.getName(),
                        ss.getNumSeats(),
                        ss.getNumExits(),
                        ss.getdateNextInspection(),
                        ss.getProjectorType());
            }
        }
     }
     
     
     //Reads in the prompt for creating a new screen
     private static Screen readScreen(Scanner keyb){
         String name, dateNextInspection, projectorType;
         int numSeats, numExits;
         String line = null;
         
         name = getString(keyb, "Enter name:");
         numSeats = Integer.parseInt(getString(keyb, "Enter number of seats:"));
         numExits = Integer.parseInt(getString(keyb, "Enter number of exits:"));
         dateNextInspection = getString(keyb, "Enter the date of the next inspection:");
         projectorType = getString(keyb, "Enter the projector type:");
         
         Screen s = 
                 new Screen(name, numSeats, numExits, dateNextInspection, projectorType);
         
         return s;
     }
 
     private static String getString(Scanner keyboard, String prompt){
         System.out.println(prompt);
         return keyboard.nextLine();
     }

    private static void editScreenDetails(Scanner keyboard, Screen s) {
        String name, dateNextInspection, projectorType;
        int numSeats, numExits;
        String line1, line2;
        
        name = getString(keyboard, "Enter name[" + s.getName() + "]: ");
        line1 = getString(keyboard, "Enter number of seats[" + s.getNumSeats() + "]: ");
        line2 = getString(keyboard, "Enter number of exits[" + s.getNumExits() + "]: ");
        dateNextInspection = getString(keyboard, "Enter date of next inspection[" + s.getdateNextInspection() + "]: ");
        projectorType = getString(keyboard, "Enter projector type[" + s.getProjectorType() + "]: ");
        
        if(name.length() !=0){
            s.setName(name);
        }
        if(line1.length() !=0){
            numSeats = Integer.parseInt(line1);
            s.setNumSeats(numSeats);
        }
        if(line2.length() !=0){
            numExits = Integer.parseInt(line2);
            s.setNumExits(numExits);
        }
        if(dateNextInspection.length() !=0){
            s.getdateNextInspection(dateNextInspection);
        }
        if(projectorType.length() !=0){
            s.setProjectorType(projectorType);
        }
    }
}
