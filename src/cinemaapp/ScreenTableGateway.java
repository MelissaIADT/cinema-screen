package cinemaapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ScreenTableGateway {
    private static final String TABLE_NAME = "screendb";
    private static final String COLUMN_SCREENID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUM_SEATS = "numSeats";
    private static final String COLUMN_NUM_EXITS = "numExits";
    private static final String COLUMN_DATE_NEXT_INSPECTION = "dateNextInspection";
    private static final String COLUMN_PROJECTOR_TYPE="projectorType";
    
    private Connection mConnection;
    private int screenID;
    

    public ScreenTableGateway(Connection connection) {
        mConnection = connection;
    }
    
    //Inserting a screen
    public int insertScreen(String n, int ns, int ne, String dni, String pt) throws SQLException{
    String query;
    PreparedStatement stmt;
    int numRowsAffected;
    int id = -1;
    
    query = "INSERT INTO " + TABLE_NAME + " (" +
            COLUMN_NAME + ", "+
            COLUMN_NUM_SEATS + ", "+
            COLUMN_NUM_EXITS + ", "+
            COLUMN_DATE_NEXT_INSPECTION + ", "+
            COLUMN_PROJECTOR_TYPE +
            ") VALUES (?, ?, ?, ?, ?)";
    
    stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    stmt.setString(1, n);
    stmt.setInt(2, ns);
    stmt.setInt(3, ne);
    stmt.setString(4, dni);
    stmt.setString(5, pt);
            
    numRowsAffected = stmt.executeUpdate();
        if(numRowsAffected == 1) {
            //if one row was inserted, retrieve the id assigned to that row
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();  
            
            screenID = keys.getInt(1);
        }
        
        return screenID;
    }
    
    //Delete
    public boolean deleteScreen(int id)throws SQLException{
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_SCREENID + " = ?";
        
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);
        
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected == 1);
    }
   
         
    public List<Screen> getScreendb() throws SQLException {
        String query;
        Statement stmt;
        ResultSet rs;
        
        List<Screen> screendb;
        
        int id;
        String name, dateNextInspection, projectorType;
        int numSeats, numExits;
        Screen s;
        
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);
        
        screendb = new ArrayList<Screen>();
        while (rs.next()){
            id = rs.getInt(COLUMN_SCREENID);
            name = rs.getString(COLUMN_NAME);
            numSeats = rs.getInt(COLUMN_NUM_SEATS);
            numExits = rs.getInt(COLUMN_NUM_EXITS);
            dateNextInspection = rs.getString(COLUMN_DATE_NEXT_INSPECTION);
            projectorType = rs.getString(COLUMN_PROJECTOR_TYPE);
            
            s = new Screen(id, name, numSeats, numExits, dateNextInspection, projectorType);
            screendb.add(s);
        }
        return screendb;
    }

    //Update 
    boolean updateScreen(Screen s) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
    
        query = "UPDATE " + TABLE_NAME + " SET " +
            COLUMN_NAME + " = ?, " +
            COLUMN_NUM_SEATS + " = ?, " +
            COLUMN_NUM_EXITS + " = ?, " +
            COLUMN_DATE_NEXT_INSPECTION + " = ?, " +
            COLUMN_PROJECTOR_TYPE + " = ? " +
            " WHERE " + COLUMN_SCREENID + " = ?" ;
    
    stmt = mConnection.prepareStatement(query);
    stmt.setString(1, s.getName());
    stmt.setInt(2, s.getNumSeats());
    stmt.setInt(3, s.getNumExits());
    stmt.setString(4, s.getdateNextInspection());
    stmt.setString(5, s.getProjectorType());
    stmt.setInt(6, s.getId());
            
    numRowsAffected = stmt.executeUpdate();
  
    return (numRowsAffected == 1);
    }
}
