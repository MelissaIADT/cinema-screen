package cinemaapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null;
    
    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    
    private List<Screen> screendb;
    private List<Movie> movies;
    ScreenTableGateway gateway;
    MovieTableGateway movieGateway;

    private Model() {

        try {
            Connection conn = DBConnection.getInstance();
            this.gateway = new ScreenTableGateway(conn);
            this.movieGateway = new MovieTableGateway(conn);
            
            this.screendb = this.gateway.getScreendb();
            this.movies = this.movieGateway.getMovies();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Screen> getScreendb(){
        return this.screendb;
    }
    
    public boolean addScreen(Screen s){
        boolean result = false;
        try{
            int id = this.gateway.insertScreen(s.getName(), s.getNumSeats(), s.getNumExits(), s.getDateNextInspection(), s.getProjectorType(), s.getMovieId());
            if(id != -1)
            {
                s.setId(id);
                this.screendb.add(s);
                result = true;
            }
        }
        catch (SQLException ex){
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result; 
    }
    
    public boolean addMovie(Movie m){
        boolean result = false;
        try {
            int m = this.movieGateway.insertScreen(m.getTitle(), m.getYearReleased(), m.getRunTime(), mId.getClassification(), m.getDirector());
            if(m != -1){
                m.setId(id);
                this.movies.add(m);
                result = true;
            }
        }
        catch (SQLException ex){
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
    }
        return result;
    }
            
    
   public boolean removeScreen(Screen s){
       boolean removed = false;
       
       try {
           removed = this.gateway.deleteScreen(s.getId());
           if (removed){
               removed = this.screendb.remove(s);
           }
       }
       catch (SQLException ex){
           Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return removed;
   }
    
    public boolean removeScreendb(Screen s){
        return this.screendb.remove(s);
    }
    
    public Screen findScreenByScreenId(int screenID){
        Screen s = null;
        int i = 0;
        boolean found = false;
        while(i < this.screendb.size() && !found){
            s = this.screendb.get(i);
            if(s.getId() == screenID){
                found = true;
            } else {
                i++;
            }
        }
        if(!found){
            s = null;
        }
        return s;
    }

    boolean updateScreen(Screen s) {
        boolean updated = false;
        
        try {
            updated = this.gateway.updateScreen(s);
        }
        catch (SQLException ex){
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);   
        }
        
        return updated;
    }
}
