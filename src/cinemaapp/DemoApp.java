package cinemaapp;

import java.util.List;
import java.util.Scanner;

public class DemoApp {
    
    private static final int NAME_ORDER = 1;
    private static final int YEAR_ORDER = 2;
    
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        
        Model model = Model.getInstance();
        
        Screen s;
        
        int opt;
        do {
            try{
                model = Model.getInstance();
                System.out.println("1. Create new Screen");
                System.out.println("2. Delete exisiting Screen");
                System.out.println("3. View all Screens");
                System.out.println("4. View single Screen");
                System.out.println("5. Edit exisiting screens");
                System.out.println();
                System.out.println("6. Create new Movie");
                System.out.println("7. Delete exisiting Movie");
                System.out.println("8. View all Movies");
                System.out.println("9. View single Movie");
                System.out.println("10. Edit exisiting Movie");
                System.out.println();
                System.out.println("11. Exit");
                System.out.println();

                opt = getInt(keyboard, "Enter option: ", 10);
                
                System.out.println("You chose option " + opt);
                String line = keyboard.nextLine();
                opt = Integer.parseInt(line);

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
                        viewScreens(model, NAME_ORDER);
                        break;
                    }
                    
                    case 4: {
                        System.out.println("Viewing single screen");
                        viewScreen(keyboard, model);
                        break;
                    }

                    case 5: {
                        System.out.println("Editing screen..");
                        editScreen(keyboard, model);
                        break;
                    }
                    
                    case 6: {
                        System.out.println("Creating movie..");
                        createMovie(keyboard, model);
                        break;
                    }
                    
                    case 7: {
                        System.out.println("Deleting movie..");
                        deleteMovie(keyboard, model);
                        break;
                    }
                    
                    case 8: {
                        System.out.println("Viewing movies..");
                        viewMovies(model, YEAR_ORDER);
                        break;
                    }
                    
                    case 9: {
                        System.out.println("Viewing single movie..");
                        viewMovie(keyboard, model);
                        break;
                    }
                    
                    case 10: {
                        System.out.println("Editing movie..");
                        editMovie(keyboard, model);
                        break;
                    }
                }
            }
            catch (DataAccessException e){
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
        while (opt != 11);
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
                            ss.getDateNextInspection(),
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
            dateNextInspection = getString(keyboard, "Enter date of next inspection[" + s.getDateNextInspection() + "]: ");
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
                s.setDateNextInspection(dateNextInspection);
            }
            if(projectorType.length() !=0){
                s.setProjectorType(projectorType);
            }
        }

    private static int getInt(Scanner keyboard, String enter_option_, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void viewScreens(Model model, int NAME_ORDER) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void viewScreen(Scanner keyboard, Model model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }
