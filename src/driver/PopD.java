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
 * This class represents the popd command for jshell
 *
 * @author Gi Tae Lim
 */
public class PopD extends Command {


  /**
   * Returns a string representation of the popd class
   *
   * @return String that defines the popd class
   */
  @Override
  public String toString() {
    String strRepr = "This command removes the most recent current directory"
        + "the user was in and changes to that directory";

    return strRepr;
  }


  /**
   * Check whether the given user input is a valid command
   *
   * @param userInput The string input user entered into console
   * @return boolean True if userInput is valid, else false
   */
  @Override
  public boolean areArgumentsValid(String userInput) {
    // String user input parsed into array of string
    String[] userInputArray = userInputStringToArray(userInput);

    // valid if first key word is "popd" and is the only keyworkd
    boolean valid = true;

    // If not 1 argument invalid
    if (userInputArray.length != 1) {
      valid = false;
    }

    // return if valid
    return valid;
  }


  /**
   * Change the current working directory to the most recently saved one
   *
   * @param userInput The string input user entered into console
   * @param jshell The jshell instance this command was executed ins
   * @throws NoSavedDirectoryInStackException If trying to popd when no
   *         directory has been pushd
   */
  @Override
  public String run(String userInput, JShell jshell) {
    // Get the jshell's file system ref
    FileSystem fs = jshell.getFileSystem();

    // Cd command
    Cd cd = new Cd();

    try {
      // Get most recent curr working dir (remove as well)
      Directory prevCurrDir = fs.getPrevCurrDir();

      // cd to it
      cd.run(prevCurrDir, jshell);
    } catch (NoSavedWorkingDirectoryException e) {
      // Output exception message
      jshell.output(e.getMessage() + "\n");
    }

    // Return null
    return null;
  }


  /**
   * Return the documentation of this command
   *
   * @return This command's documentation
   */
  @Override
  public String getDocumentation() {
    return "Usage: popd [>/>> OUTFILE] \nRemove the top most directory from "
        + "the directory stack and makes it the current directory\n"
        + "[>/>> OUTFILE] represents an argument to redirect the output to "
        + "OUTFILE";
  }

}
