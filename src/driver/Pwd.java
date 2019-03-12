// **********************************************************
// Assignment2:
// Student1: Ao Gao
// UTORID user_name: gaoao1
// UT Student #: 1004077321
// Author: Ao Gao
//
// Student2: Pravinthan Prabagaran
// UTORID user_name: prabaga2
// UT Student #: 1004353503
// Author: Pravinthan Prabagaran
//
// Student3: Thomas Lo
// UTORID user_name: lothoma2
// UT Student #: 1004172619
// Author: Thomas Lo
//
// Student4: Gi Tae Lim
// UTORID user_name: limgi
// UT Student #: 1004097684
// Author: Gi Tae Lim
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

/**
 * This class represents the pwd command for JShell
 * 
 * @author Thomas Lo
 */
public class Pwd extends Command {

  /**
   * Creates an instance of Pwd
   */
  public Pwd() {
    super();
  }

  /**
   * Returns a string representation of the pwd class
   * 
   * @return String that defines the pwd class
   */
  @Override
  public String toString() {
    String s = "This command is the print working directory command. Prints"
        + " the absolute path name for the current working directory.";

    return s;
  }


  /**
   * Validates a given String input command and returns true if it is formatted
   * correctly command is valid iff it takes the form "pwd"
   * 
   * @param command The String input command that needs validation
   * @return boolean True if command is valid, else false
   */
  @Override
  public boolean areArgumentsValid(String command) {
    // Boolean var default to true
    boolean valid = true;

    // Get command name
    String commandName = this.getClass().getSimpleName().toLowerCase();

    if (!command.equals(commandName)) {
      valid = false;
    }
    return valid;
  }

  /**
   * Runs the pwd command to print the current working directory, adds a new
   * line after the output
   * 
   * @param commandKeyWords array of strings containing the additional keywords
   * @param jshell is an instance of the JShell
   */
  @Override
  public String run(String commandKeyWords, JShell jshell) {
    // Gets the current directory's absolute path name and returns it
    return run(jshell);
  }


  /**
   * Overloaded method which performs the pwd command functionality but does
   * not add a new line after invoked
   * 
   * @param jshell is an instance of the JShell
   */
  public String run(JShell jshell) {
    // Gets the current directory's absolute path name and returns it
    JShellFileSystem fs = (JShellFileSystem) jshell.getFileSystem();
    return fs.getCurrentDirectory().getPath();
  }

  /**
   * Return the documentation of this command
   * 
   * @return This command's documentation
   */
  @Override
  public String getDocumentation() {
    return "Usage: pwd [>/>> OUTFILE] \nOutput the absolute path of the "
        + "current directory \n\nExample: \npwd \n/hello/world/ \n"
        + "[>/>> OUTFILE] represents an argument to redirect the output to "
        + "OUTFILE";
  }
}


