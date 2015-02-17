package cinemaapp;

import static jdk.nashorn.internal.runtime.Debug.id;

public class Screen {
    
    private int id;
    private String name;
    private int numSeats;
    private int numExits;
    private String dateNextInspection;
    private String projectorType;
    
    public Screen(int id, String n, int ne, int ns, String dni, String pt) {
        this.id = id;
        this.name = n;
        this.numSeats = ne; 
        this.numExits = ns;
        this.dateNextInspection = dni;
        this.projectorType = pt;
    }
    
    public Screen(String n, int ne, int ns, String dni, String pt) {
        this(-1, n, ne, ns, dni, pt);
    }
    
    //Get and set methods
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public int getNumSeats(){
        return numSeats;
    }
    
    public void setNumSeats(int numSeats){
        this.numSeats = numSeats;
    }
    
    public int getNumExits(){
        return numExits;
    }
    
    public void setNumExits(int numExits){
        this.numExits = numExits;
    }
    
    public String getdateNextInspection(){
        return dateNextInspection;
    }
    
    public void setDateNextInspection(String dateNextInspection){
        this.dateNextInspection = dateNextInspection;
    }
    
    public String getProjectorType(){
        return projectorType;
    }
    
    public void setProjectorType(String projectorType){
        this.projectorType = projectorType;
    }         

    void getdateNextInspection(String dateNextInspection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}