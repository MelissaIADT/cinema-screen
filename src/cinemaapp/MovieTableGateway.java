package cinemaapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieTableGateway {
    
    private Connection mConnection;
    
    private static final String TABLE_NAME = "movie";
    private static final String COLUMN_ID = "movieID";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR_RELEASED = "yearReleased";
    private static final String COLUMN_RUN_TIME = "runTime";
    private static final String COLUMN_CLASSIFICATION = "classification";
    private static final String COLUMN_DIRECTOR = "director";

    public MovieTableGateway(Connection connection){
        mConnection = connection;
    }
    
    private int insertMovie(String t, int yr, double rt, String c, String d) throws SQLException{
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        int id = -1;
        
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_TITLE + ", " +
                COLUMN_YEAR_RELEASED + ", " +
                COLUMN_RUN_TIME + ", " +
                COLUMN_CLASSIFICATION + ", " +
                COLUMN_DIRECTOR + 
                ") VALUES (?, ?, ?, ?, ?)";
        
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, t);
        stmt.setInt(2, yr);
        stmt.setDouble(3, rt);
        stmt.setString(4, c);
        stmt.setString(5, d);
        
        numRowsAffected = stmt.executeUpdate();
        if(numRowsAffected == 1){
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            
            id = keys.getInt(1);
        }
        
        return id;
    }
    
    public boolean deleteMovie(int id) throws SQLException{
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);
        
        numRowsAffected = stmt.executeUpdate();
        
        return(numRowsAffected == 1);
    }
    
    public List<Movie> getMovies() throws SQLException{
        String query;
        Statement stmt;
        ResultSet rs;
        List<Movie> movies;
        
        String title, classification, director;
        int id, yearReleased;
        double runTime;
        Movie m;
        
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);
        
        movies = new ArrayList<Movie>();
        while(rs.next()){
            id = rs.getInt(COLUMN_ID);
            title = rs.getString(COLUMN_TITLE);
            yearReleased = rs.getInt(COLUMN_YEAR_RELEASED);
            runTime = rs.getDouble(COLUMN_RUN_TIME);
            classification = rs.getString(COLUMN_CLASSIFICATION);
            director = rs.getString(COLUMN_DIRECTOR);
            
            m = new Movie(id, title, yearReleased, runTime, director);
            movies.add(m);
        }
        
        return movies;
    }
    
    boolean updateMovie(Movie m) throws SQLException{
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        
        query = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_TITLE  + " = ?, " +
                COLUMN_YEAR_RELEASED + " = ?, " +
                COLUMN_RUN_TIME + " = ?, " +
                COLUMN_CLASSIFICATION + " = ?, " +
                COLUMN_DIRECTOR + " = ?, " +
                " WHERE " + COLUMN_ID + " = ?";
        
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, m.getTitle());
        stmt.setInt(2, m.getYearReleased());
        stmt.setDouble(3, m.getRunTime());
        stmt.setString(4, m.getClassification());
        stmt.setString(5, m.getDirector());
        
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected == 1);
    }
}