// This class makes a high-level abstraction of the database operations needed for the project

package gyst;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Business
{
    private Connection businessConnection; // The connection with the database
    private String databaseURL; // The URL of the database
    
    public Business(String databaseURL) throws InstantiationException, SQLException
    // PRE: databaseURL must be initialized
    // POST: a new Business object is created with this.databaseURL = databaseURL
    //       and a new connection is stabilished with the database if all goes right
    {
        this.databaseURL = databaseURL;
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            this.businessConnection = 
                    DriverManager.getConnection(this.databaseURL, "root", "root");
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(Business.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(ClassNotFoundException ex)
        {
        }
    }
    
    public void insertCourse(String className, String subject, int courseNumber)
            throws SQLException
    // PRE: className and subject must be initialized. courseNumber > 0;
    // POST: a new course is inserted into the database table COURSES
    {
        String sqlcmd = "INSERT INTO COURSES (CLASS_NAME,SUBJECT,COURSE_NUMBER)"
                + " VALUES ('" + className + "', '" + subject + "', " + courseNumber + ");";
        Statement stmt = this.businessConnection.createStatement();
        stmt.execute(sqlcmd);
        stmt.close();
        this.businessConnection.close();
    }
    
    public void insertAssignment(String assignName, Date dueDate, int assignID)
            throws SQLException
    // PRE: assignName and dueDate must be initialized. assignID > 0;
    // POST: a new assignment is inserted into the database table ASSIGNMENTS
    {
        String sqlcmd = "INSERT INTO ASSIGNMENTS (ASSN_NAME,DUE_DATE,ID)"
                + " VALUES ('" + assignName + "', '" + dueDate.toString()
                + "', " + assignID + ");";
        Statement stmt = this.businessConnection.createStatement();
        stmt.execute(sqlcmd);
        stmt.close();
        this.businessConnection.close();
    }
    
    public void deleteCourse(int courseID) throws SQLException
    // PRE: courseID > 0
    // POST: the course with id = courseID is delete from the table COURSES
    {
        String sqlcmd = "DELETE FROM COURSES WHERE ID = " + courseID + ";";
        Statement stmt = this.businessConnection.createStatement();
        stmt.execute(sqlcmd);
        stmt.close();
        this.businessConnection.close();
    }
    
    public void deleteAssignment(int courseID, String assignName) throws SQLException
    // PRE: assignID > 0
    // POST: The assignment with id = assignID is delete from the table ASSIGNMENTS
    {
        String sqlcmd = "DELETE FROM ASSIGNMENTS WHERE ID = " + courseID +
                "AND ASSN_NAME = '" + assignName + "';";
        Statement stmt = this.businessConnection.createStatement();
        stmt.execute(sqlcmd);
        stmt.close();
        this.businessConnection.close();
    }
    
    public void updateGrade(int assignID, double newGrade) throws SQLException
    // PRE: assignID > 0, newGrade > 0.0
    // POST: The assignment with id = assignID has its grade updated
    {
        String sqlcmd = "UPDATE ASSIGNMENTS SET GRADE = " + newGrade +
                " WHERE A_ID = " + assignID + ";";
        Statement stmt = this.businessConnection.createStatement();
        stmt.execute(sqlcmd);
        stmt.close();
        this.businessConnection.close();
    }
    
    public void updateGrade(int courseID, String assignName, double newGrade)
            throws SQLException
    // PRE: courseID > 0, newGrade > 0.0, and assignName is initialized
    // POST: The assignment with course_id = courseID and name = assignName has
    //       its grade updated
    {
        String sqlcmd = "UPDATE ASSIGNMENTS SET GRADE = " + newGrade +
                " WHERE ASSN_NAME = '" + assignName + "' AND ID = " + courseID + ";";
        Statement stmt = this.businessConnection.createStatement();
        stmt.execute(sqlcmd);
        stmt.close();
        this.businessConnection.close();
    }
    
    public void updateDueDate(int courseID, String assignName, Date newDueDate)
            throws SQLException
    // PRE: courseID > 0, newDueDate and assignName are initialized
    // POST: The assignment with course_id = courseID and name = assignName
    //       has its due date updated
    {
        String sqlcmd = "UPDATE ASSIGNMENTS SET GRADE = " + newDueDate.toString() +
                " WHERE ASSN_NAME = '" + assignName + "' AND ID = " + courseID + ";";
        Statement stmt = this.businessConnection.createStatement();
        stmt.execute(sqlcmd);
        stmt.close();
        this.businessConnection.close();
    }
    
    public double getAverageGrade(int courseID) throws SQLException
    // PRE: courseID > 0
    // POST: FCTVAL == the average grade for the course with id = courseID
    {
        String sqlcmd = "SELECT AVG(GRADE) AS Average FROM ASSIGNMENTS WHERE ID = "
                + courseID + ";";
        Statement stmt = this.businessConnection.createStatement();
        ResultSet result = stmt.executeQuery(sqlcmd);
        stmt.close();
        this.businessConnection.close();
        return result.getDouble("Average");
    }
    
    public ArrayList<Assignment> getPastDueNonGradedAssignments() throws SQLException
    // POST: FCTVAL == a ArrayList of assignments that are past due and aren't graded
    {
        ArrayList<Assignment> assigns = new ArrayList<Assignment>();
        Assignment assign;
        String sqlcmd = "SELECT * FROM ASSIGNMENTS WHERE"
                + " DUE_DATE <= CURRENT DATE AND GRADE IS NULL;";
        Statement stmt = this.businessConnection.createStatement();
        ResultSet result = stmt.executeQuery(sqlcmd);
        stmt.close();
        this.businessConnection.close();
        
        while(result.next())
        {
            assign = new Assignment(result.getInt("A_ID"),
                                    result.getString("ASSN_NAME"),
                                    result.getDate("DUE_DATE"),
                                    result.getInt("ID"));
            assigns.add(assign);
        }
        
        return assigns;
    }
    
    public ArrayList<Assignment> getUpcomingAssignments(Date upperLimitDate)
            throws SQLException
    // PRE: upperLimitDate must be initialized
    // POST: FCTVAL == a ArrayList of assignments such that their due dates are upcoming
    {
        ArrayList<Assignment> assigns = new ArrayList<Assignment>();
        Assignment assign;
        String sqlcmd = "SELECT * FROM ASSIGNMENTS WHERE"
                + " DUE_DATE >= CURRENT DATE AND DUE_DATE <= "
                + upperLimitDate.toString() + ";";
        Statement stmt = this.businessConnection.createStatement();
        ResultSet result = stmt.executeQuery(sqlcmd);
        stmt.close();
        this.businessConnection.close();
        
        while(result.next())
        {
            assign = new Assignment(result.getInt("A_ID"),
                                    result.getString("ASSN_NAME"),
                                    result.getDate("DUE_DATE"),
                                    result.getInt("ID"));
            assigns.add(assign);
        }
        
        return assigns;
    }
}
