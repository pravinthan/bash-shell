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
 * This class represents the pushd command for jshell
 *
 * @author Gi Tae Lim
 */
public class PushD extends Command {


  /**
   * Returns a string representation of the pushd class
   *
   * @return String that defines the pushd class
   */
  @Override
  public String toString() {
    String strRepr = "This command saves the current directory"
        + "the user is in and changes to the given directory";

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

    // valid if first key word is "pushd" and is followed by 1 param
    boolean valid = true;

    // If not 2 arguments invalid
    if (userInputArray.length != 2) {
      valid = false;
    }

    // return if valid
    return valid;
  }

  /**
   * Save the current working directory into an accessible history structure
   * and change the current working directory to the given directory
   *
   * @param userInput The string input user entered into console
   * @param jshell The jshell instance this command was executed ins
   * @throws NoSuchDirectoryException When the given path to change directory
   *         is not found in the directory structure
   */
  @Override
  public String run(String userInput, JShell jshell) {
    // String user input parsed into array of string
    String[] userInputArray = userInputStringToArray(userInput);

    // Get jshell's file system ref
    FileSystem fs = jshell.getFileSystem();

    // The cd command to use to change directories
    Cd cd = new Cd();

    // Get the string path to the new current directory
    String newCurrDirPath = userInputArray[1];

    try {
      // Get the current directory obj
      Directory newCurrDir = fs.getDirectory(newCurrDirPath);

      // Save current directory into stack
      fs.saveCurrDir();

      // cd to new curr working dir
      cd.run(newCurrDir, jshell);
    } catch (NoSuchDirectoryException e) {
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
    return "Usage: pushd DIR [>/>> OUTFILE] \nSaves the current directory by "
        + "pushing it onto the directory stack and then changes the current "
        + "directory to DIR \n[>/>> OUTFILE] represents an argument to "
        + "redirect the output to OUTFILE";
  }

}
