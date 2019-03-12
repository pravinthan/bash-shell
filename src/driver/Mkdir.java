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
 * This class represents the make directory command for jshell
 *
 * @author Gi Tae Lim
 */
public class Mkdir extends Command {


  /**
   * Returns a string representation of the mkdir class
   *
   * @return String that defines the mkdir class
   */
  @Override
  public String toString() {
    String strRepr =
        "This command is the make directory command. Given an absolute"
            + "or relative path, create a new directory at the given"
            + " directory";

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

    // If no path given
    if (userInputArray.length <= 1) {
      valid = false;
    }

    // Return if valid command
    return valid;
  }


  /**
   * Runs the mkdir command make a new directory in the given path
   *
   * @param userInput The string input user entered into console
   * @throws NoSuchDirectoryException When the path is not found in the
   *         directory structure
   * @throws InvalidNameException When trying to make directory with an invalid
   *         name
   */
  @Override
  public String run(String userInput, JShell jshell) {
    // Get file system of jshell
    FileSystem fs = jshell.getFileSystem();

    // String user input parsed into array of string
    String[] userInputArray = userInputStringToArray(userInput);

    String[] directories = getDirectoriesFromUserInputArray(userInputArray);

    // For each directory that needs to be made
    for (int i = 0; i < directories.length; i++) {
      // Get the given path from array of keywords
      String path = directories[i];

      Directory parentDir;
      // New Dir name
      String newDirName;

      try {
        // Get the parent path and new dir name from user input
        String[] newDirParsed =
            splitParentwithNewDirName(path, fs.getCurrentDirectory());

        // Get teh parent dir and new dir name from array we just got
        parentDir = fs.getDirectory(newDirParsed[0]);
        newDirName = newDirParsed[1];



        // Add the new dir we want to the system
        Directory.createDirectoryWithParent(newDirName, parentDir);
      } catch (NoSuchDirectoryException e) {
        jshell.output(e.getMessage() + "\n");
      } catch (InvalidNameException e) {
        jshell.output(e.getMessage() + "\n");
      }
    }

    // Return null
    return null;
  }


  /**
   * Given the path to the new dir to make, split this path into the parent dir
   * path and the new dir name
   * 
   * @param path The string path of the new dir
   * @param currDir The filesystem's current directory Directory
   * @return A String array where first element is the parent dir path, and the
   *         second the new dir name
   */
  private String[] splitParentwithNewDirName(String path, Directory currDir) {
    // Result
    String[] result = {"", ""};

    // Temp path
    String pathCopy = path;

    // To reduce possible path cases remove the last / if exists
    if (pathCopy.charAt(pathCopy.length() - 1) == '/') {
      pathCopy = pathCopy.substring(0, pathCopy.length() - 1);
    }

    // Last index of / in path
    int partitionIndex = pathCopy.lastIndexOf('/');


    // If index not found
    if (partitionIndex < 0) {
      // Then this is relative path where the entire string given is the new 
      // dir name and parent dir is the curr dir
      result[0] = currDir.getPath();
      result[1] = pathCopy;
    } else if (partitionIndex == 0) {
      // If index = 0
      // Then this is an absolute path
      // Parent dir path is the root
      result[0] = pathCopy.substring(0, partitionIndex + 1);
      // New dir name is everything after the /
      result[1] = pathCopy.substring(partitionIndex + 1);
    } else if (partitionIndex > 0 && pathCopy.charAt(0) == '/') {
      // If index > 0 and given path is relative path
      // Then the parent path is the curr dir path + the path up until last /
      result[0] = pathCopy.substring(0, partitionIndex + 1);
      // New dir name is the rest
      result[1] = path.substring(partitionIndex + 1);
    } else {
      // If index > 0 and given path is absolute path
      // Parent path is jsut everyhting before the index
      result[0] = currDir.getPath() + path.substring(0, partitionIndex + 1);
      result[1] = path.substring(partitionIndex + 1);
    }

    return result;

  }


  /**
   * Given an array of directories users wants to make, split each dir path
   * 
   * @param userInputArray The user's input split into an array of words that
   *        may have redirection included
   * @return The string array that only has directory paths in each element
   */
  private String[] getDirectoriesFromUserInputArray(String[] userInputArray) {
    // Result
    String[] result;

    // Get the index where the redirection type keyword is found
    int end_index = getRedirectionTypeIndex(userInputArray);

    // Return a copy of user input array starting from first index up to index
    // gotten from above
    // If index gotten is not -1
    if (end_index >= 0) {
      // Result is the copy of user input array from 1 to end index
      result = Arrays.copyOfRange(userInputArray, 1, end_index);
    } else {
      // Else result is the copy from 1 to end
      result = Arrays.copyOfRange(userInputArray, 1, userInputArray.length);
    }

    return result;
  }


  /**
   * Given the user's input as an array of words, return the index where the
   * redirection type is found
   * 
   * @param userInputArray userInputArray The user's input split into an array
   *        of words that may have redirection included
   * @return Index of wher redirection type keyword is found in the given array
   */
  private int getRedirectionTypeIndex(String[] userInputArray) {
    int result = -1;

    // Find index where element is either ">" or ">>"
    for (int i = 0; i < userInputArray.length; i++) {
      if (userInputArray[i].equals(">") || userInputArray[i].equals(">>")) {
        result = i;
      }
    }

    return result;
  }


  /**
   * Return the documentation of this command
   *
   * @return This command's documentation
   */
  @Override
  public String getDocumentation() {
    return "Usage: mkdir DIR1 [DIR2 ...] [>/>> OUTFILE] \n[DIR2 ...] "
        + "represents optional arguments \n... represents a list \nCreate "
        + "directories DIR1 [DIR2 ...] in their respective paths "
        + "\n[>/>> OUTFILE] represents an argument to redirect the output to "
        + "OUTFILE";
  }

}
