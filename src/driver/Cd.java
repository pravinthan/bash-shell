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

import java.util.Arrays;

/**
 * This class represents the cd command for jshell
 *
 * @author Gi Tae Lim
 */
public class Cd extends Command {


  /**
   * Returns a string representation of the cd class
   *
   * @return String that defines the cd class
   */
  @Override
  public String toString() {
    String strRepr = "This command is the change directory command. Given a"
        + "directory's absolute or relative path, change the current directory"
        + "to it if it exists";

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

    // Bool var defaulted to true
    boolean valid = true;

    // Syntax is invalid if not 2 arguments
    if (userInputArray.length != 2) {
      valid = false;
    }

    // Return if valid command
    return valid;
  }


  /**
   * Runs the cd command to change directory to the given path
   *
   * @param userInput The string input user entered into console
   * @return null
   * @throws NoSuchDirectoryException When the given path to change directory
   *         to is not found in the directory structure
   */
  @Override
  public String run(String userInput, JShell jshell) {
    // String user input parsed into array of string
    String[] userInputArray = userInputStringToArray(userInput);

    // Take just the directory in given input
    String[] cdArguments = Arrays.copyOfRange(userInputArray, 1, 2);

    // Get file system of jshell
    FileSystem fs = jshell.getFileSystem();
    // Get the directory to change to from array of command key words
    String newDirectoryName = cdArguments[0];

    // The new directory to change to
    Directory newDirectory;

    // Try to get the directory with the above name
    // If exception thrown, output the exception message
    try {
      newDirectory = fs.getDirectory(newDirectoryName);
      // Change the current directory in the file system to the Directory
      run(newDirectory, jshell);
    } catch (NoSuchDirectoryException e) {
      // Output exception message
      jshell.output(e.getMessage() + "\n");;
    }

    // Return null
    return null;
  }


  /**
   * Changes the current directory to the given current directory object
   *
   * @param newDir The new current working directory object to change to
   */
  public void run(Directory newDir, JShell jshell) {
    // Get file system of jshell
    FileSystem fs = jshell.getFileSystem();

    fs.setCurrentDirectory(newDir);
  }


  /**
   * Return the documentation of this command
   *
   * @return This command's documentation
   */
  @Override
  public String getDocumentation() {
    return "Usage: cd DIR [>/>> OUTFILE] \nChange the current directory to "
        + "DIR \nDIR can be a relative path or absolute path \nDIR can "
        + "contain .., representing the parent directory of the current "
        + "directory. DIR can contain ., representing the current directory "
        + "\n[>/>> OUTFILE] represents an argument to redirect the output to "
        + "OUTFILE";
  }

}
