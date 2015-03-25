package cinemaapp;

import java.util.List;
import java.util.Scanner;

public class DemoApp {
    private static int movieID;
    
    /*private static final int NAME_ORDER = 1;
    private static final int YEAR_ORDER = 2;*/
    
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        
        Model model = Model.getInstance();
        
        Screen s;
        
        int opt = 0;
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
                        viewScreens(model);
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
                        viewMovies(model);
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
         private static void createScreen(Scanner keyboard, Model model) throws DataAccessException{
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
         private static void deleteScreen(Scanner kb, Model m) throws DataAccessException {
             int id = getInt(keyboard, "Enter the ID of the Screen to delete:", -1);
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
         private static void editScreen(Scanner keyboard, Model model) throws DataAccessException {
             int id = getInt(keyboard, "Enter the ID of the screen to edit:", -1);
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
         
         private static void viewScreen(Scanner keyboard, Model model) throws DataAccessException{
             int id = getInt(keyboard, "Enter the ID of the screen to view:", -1);
             Screen s;
             
             s = model.findScreenByScreenId(id);
             System.out.println();
             if(s != null){
                 Movie m = model.findMovieById(s.getMovieID());
                 System.out.println("Name       :" + s.getName());
                 System.out.println("Number of Seats    :" + s.getNumSeats());
                 System.out.println("Number of Exits    :" + s.getNumExits());
                 System.out.println("Date of next Inspection    :" + s.getDateNextInspection());
                 System.out.println("Projector Type     :" + s.getProjectorType());
                 System.out.println("Movie              :" + ((m != null) ? m.getTitle() : ""));
             }
             else{
                 System.out.println("Screen not found");
             }
             System.out.println();
         }


         //Reads in the prompt for creating a new screen
         private static Screen readScreen(Scanner keyb){
             String name, dateNextInspection, projectorType;
             int numSeats, numExits;
             String line;

             name = getString(keyb, "Enter name:");
             numSeats = getInt(keyb, "Enter number of seats:" , 0);
             numExits = getInt(keyb, "Enter number of exits:" , 0);
             dateNextInspection = getString(keyb, "Enter the date of the next inspection:");
             projectorType = getString(keyb, "Enter the projector type:");
             movieID = getInt(keyb, "Enter movie id (enter -1 for no movie): ", -1);

             Screen s = 
                     new Screen(name, numSeats, numExits, dateNextInspection, projectorType, movieID);

             return s;
         }

         /*private static String getString(Scanner keyboard, String prompt){
             System.out.println(prompt);
             return keyboard.nextLine();
         }*/

        private static void editScreenDetails(Scanner keyboard, Screen s) {
            String name, dateNextInspection, projectorType;
            int numSeats, numExits;
            String line1, line2;

            name = getString(keyboard, "Enter name[" + s.getName() + "]: ");
            numSeats = getInt(keyboard, "Enter number of seats[" + s.getNumSeats() + "]: ", 0);
            numExits = getInt(keyboard, "Enter number of exits[" + s.getNumExits() + "]: ", 0);
            dateNextInspection = getString(keyboard, "Enter date of next inspection[" + s.getDateNextInspection() + "]: ");
            projectorType = getString(keyboard, "Enter projector type[" + s.getProjectorType() + "]: ");
            movieID = getInt(keyboard , "Enter movie id (enter -1 for no movie)[" + s.getMovieID() + "]: ", -1);

            if(name.length() !=0){
                s.setName(name);
            }
            if(numSeats.length()){
                s.setNumSeats(numSeats);
            }
            if(numExits.length()){
                s.setNumExits(numExits);
            }
            if(dateNextInspection.length() !=0){
                s.setDateNextInspection(dateNextInspection);
            }
            if(projectorType.length() !=0){
                s.setProjectorType(projectorType);
            }
            if(movieID != s.getMovieID()){
                s.setMovieID(movieID);
            }
        }

    private static void createMovie(Scanner keyboard, Model model) throws DataAccessException{
        Movie m = viewMovie(keyboard);
        if (model.addMovie(m)){
            System.out.println("Movie added to database.");
        }
        else{
            System.out.println("Movie not added to database.");
        }
        System.out.println();
    }

    private static void deleteMovie(Scanner keyboard, Model model) throws DataAccessException{
        int movieID = getInt(keyboard, "Enter the ID of the movie to delete:", -1);
        Movie m;
        
        m = model.findMovieById(movieID);
        if(m != null){
            if (model.deleteMovie(m)){
                System.out.println("Movie deleted");
            }
            else {
                System.out.println("Movie not deleted");
            }
        }
        else{
            System.out.println("Movie not found");
        }
    }

    private static void viewMovies(Model model) {
        List<Movie> movies = model.getMovies();
        System.out.println();
        if(movies.isEmpty()){
            System.out.println("There are no movies in the database.");
        }
        else{
            System.out.printf("%5s %20s %20s %15s\n",
                    "Id", "Title", )
        }
    }
    //CONTINUE CODE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
    private static void viewMovie(Scanner keyboard, Model model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void editMovie(Scanner keyboard, Model model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
