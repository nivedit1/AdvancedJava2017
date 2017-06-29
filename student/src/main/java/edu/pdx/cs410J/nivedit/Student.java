package edu.pdx.cs410J.nivedit;

import edu.pdx.cs410J.lang.Human;

import java.util.ArrayList;

import java.util.Arrays;
                                                                                    
/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {                                                
                                                                                    
  /**                                                                               
   * Creates a new <code>Student</code>                                             
   *                                                                                
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male" or "female", case insensitive)             
   */
  public String name;
  public ArrayList classes;
  public double gpa;
  public String gender;
  public Student(String name, ArrayList classes, double gpa, String gender) {
    super(name);
    this.name = name;
    this.gpa = gpa;
    this.gender = gender;
    this.classes = (ArrayList<Object>) classes.clone();
  }

  /**                                                                               
   * All students say "This class is too much work"
   */
  @Override
  public String says() {
    String student_say;
    if(this.gender.equalsIgnoreCase("male")) {
    student_say = "He says ";
    }
    else if(this.gender.equalsIgnoreCase("female")) {
      student_say = "She says ";
      student_say = student_say + "\"This class is too much work\"" + ".";
    }
    else
      student_say = "This class is too much work.";
    return student_say;
    /*throw new UnsupportedOperationException("Not implemented yet");*/

  }
                                                                                    
  /**                                                                               
   * Returns a <code>String</code> that describes this                              
   * <code>Student</code>.                                                          
   */                                                                               
  public String toString() {
    /*System.out.println(this.classes.get(0));*/
    String print_stmt = this.name + " has a GPA of " + this.gpa + " and is taking " + this.classes.size()+ " classes: " ;
    for (int j = 0; j <this.classes.size(); j++)
    {
      print_stmt = print_stmt + this.classes.get(j) + " ";
    }
    print_stmt = print_stmt + ".";
    return print_stmt;
    /*throw new UnsupportedOperationException("Not implemented yet");*/
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    if (args.length > 3) {
      //System.out.println(Arrays.toString(args));
      ArrayList<String> input_classes = new ArrayList<String>();
      for (int i = 3; i < args.length; i++) {
        input_classes.add(args[i]);
      }
      double input_gpa = Double.parseDouble(args[2]);
      Student student = new Student(args[0], input_classes, input_gpa, args[1]);
      System.out.println(student.toString());
      System.out.println(student.says());
    }
  else
  {
    System.err.println("Missing command line arguments");
    System.exit(1);
  }
  }
}