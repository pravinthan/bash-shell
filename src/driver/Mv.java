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
 * Mv is a class to hold all the methods required for the mv command to work
 * 
 * @author Pravinthan Prabagaran
 */
public class Mv extends Command {

  /**
   * Returns a string representation of the cp class
   *
   * @return String that defines the cd class
   */
  @Override
  public String toString() {
    return "Moves a file or directory to another file or directory";
  }

  /**
   * checks whether the user input is valid
   * @param input is the user input
   * @return whether the user input is valid
   */
  @Override
  public boolean areArgumentsValid(String input) {
    // Parse the input into an array
    String[] inputArray = userInputStringToArray(input);

    // Checks if there aren't 3 args
    boolean isValid = false;
    if (inputArray.length == 3) {
      isValid = true;
    } else if (inputArray.length == 5
        && (inputArray[inputArray.length - 2].equals(">>")
            || inputArray[inputArray.length - 2].equals(">"))) {
      isValid = true;
    }

    return isValid;
  }


  /**
   * Given the user's string input with oldpath and newpath, move the directory
   * or file at oldpath to newpath
   *
   * @param input String of user's input
   * @jshell The jshell instance this command is executing in
   */
  @Override
  public String run(String input, JShell jShell) {
    String[] inputArray = userInputStringToArray(input);
    inputArray = Arrays.copyOfRange(inputArray, 1, inputArray.length);

    // Get the references to file system and output
    FileSystem fileSys = jShell.getFileSystem();

    // Find out if the first argument is a file or a directory
    File file = null;
    Directory directory = null;
    try {
      file = fileSys.getFile(inputArray[0]);
    } catch (NoSuchFileException e) {
      file = null;
    }

    try {
      directory = fileSys.getDirectory(inputArray[0]);
    } catch (NoSuchDirectoryException e) {
      directory = null;
    }

    // Run the copy command
    input = "cp " + input.substring(3);
    new Cp().run(input, jShell);

    // Delete the original directory
    try {
      if (directory != null) {
        directory.getParent().removeChildDirectory(directory);
      }
    } catch (NoSuchDirectoryException e) {
    }
    directory = null;

    // Delete the original file
    try {
      if (file != null) {
        file.getParent().deleteFile(file.getName());
      }
    } catch (NoSuchFileException e) {
    }
    file = null;

    return null;
  }

  @Override
  public String getDocumentation() {
    return "Usage: mv PATH1 PATH2 [>/>> OUTFILE] \nIf PATH1 and PATH2 are "
        + "directories, then "
        + "move contents of directory PATH1 to PATH2 and delete directory "
        + "PATH1 \nIf PATH1 is a directory and PATH2 doesn't exist, rename "
        + "directory PATH1 to directory PATH2 \nIf PATH1 is a file and PATH2 "
        + "is a file, overwrite file PATH2's contents with file PATH1 and "
        + "delete file PATH1 \nIf PATH1 is a file and PATH2 is a directory, "
        + "move file PATH1 to directory PATH2 \nIf PATH1 is a file and PATH2 "
        + "does not exist, rename file PATH1 to PATH2 \n[>/>> OUTFILE] "
        + "represents an argument to redirect the output to OUTFILE";
  }

}
