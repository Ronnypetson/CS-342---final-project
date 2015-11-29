// This class represents the course entity, that is present in the project logic

package gyst;

public class Course
{
    private int id; // The course identification number
    private String className; // The name of the class
    private String subject; // The subject of the course
    private int courseNumber; // The course identification number in its major
    
    public Course(int id, String className, String subject, int courseNumber)
    // PRE: id > 0, courseNumber > 0, className and subject must be initialized
    // POST: a new Course object is created with the class members assigned to
    //       the respective parameters.
    {
        this.id = id;
        this.className = className;
        this.subject = subject;
        this.courseNumber = courseNumber;
    }
    
    public int getId()
    // POST: FCTVAL == this.id
    {
        return this.id;
    }
    
    public String getClassName()
    // POST: FCTVAL == this.className
    {
        return this.className;
    }
    
    public String getSubject()
    // POST: FCTVAL == this.subject
    {
        return this.subject;
    }
    
    public int getCourseNumber()
    // POST: FCTVAL == this.courseNumber
    {
        return this.courseNumber;
    }
    
    public void setId(int id)
    // PRE: id > 0
    // POST: this.id is updated to id
    {
        this.id = id;
    }
    
    public void setClassName(String className)
    // PRE: className must be initialized
    // POST: this.className is updated to className
    {
        this.className = className;
    }
    
    public void setSubject(String subject)
    // PRE: subject must be initialized
    // POST: this.subject is updated to subject
    {
        this.subject = subject;
    }
    
    public void setCourseNumber(int courseNumber)
    // PRE: courseNumber > 0
    // POST: this.courseNumber is updated to courseNumber
    {
        this.courseNumber = courseNumber;
    }
}
