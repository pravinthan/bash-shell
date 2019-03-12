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
 * responsible for exiting the JShell
 *
 * @author Ao
 */
public class Exit extends Command {


  /**
   * Returns a string representation of the exit class
   *
   * @return String that defines the exit class
   */
  public String toString() {
    String strRepr = "Exit command exits the jshell";

    return strRepr;
  }


  /**
   * Validates a given String input command user entered and returns true if it
   * is formatted correctly commandArguments is valid
   *
   * @param userInput String of user input
   * @return boolean True if commandArguments is valid, else false
   */
  @Override
  public boolean areArgumentsValid(String userInput) {
    // valid if first key word is "exit" and is followed by 0 param
    boolean valid = true;

    // If any other param is inputted invalid
    if (userInput.length() != 0) {
      valid = false;
    }

    // return if valid
    return valid;
  }


  /**
   * runs the exit command to terminate jshell
   * 
   * @param userInput The string of user input
   * @param jshell the jshell reference that this command was called in
   * @throws NoSuchDirectoryException When the given path to change directory
   *         is not found in the directory structure
   */
  @Override
  public String run(String userInput, JShell jshell) {
    // Call method to terminate jshell
    jshell.exitJShell();

    // Return
    return null;
  }


  /**
   * Return the documentation of this command
   *
   * @return This command's documentation
   */
  @Override
  public String getDocumentation() {
    return "Usage: exit \nClose the shell";
  }

}
