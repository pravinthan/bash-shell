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
 * Ls is a class to hold all the methods required for the ls command to work
 *
 * @author Pravinthan Prabagaran
 */
public class Ls extends Command {

  @Override
  public String toString() {
    return "Lists contents of given paths or current path";
  }

  @Override
  public boolean areArgumentsValid(String input) {
    return true;
  }

  /**
   * Return the string of directory and file names in given directories
   * 
   * @param input String input form user
   * @param jshell The jshell instance that this command is executing in
   */
  @Override
  public String run(String input, JShell jShell) {
    // Parse the input into an array and remove the command name from array
    String[] inputArray = userInputStringToArray(input);
    inputArray = Arrays.copyOfRange(inputArray, 1, inputArray.length);

    // Get file system reference
    FileSystem fileSys = jShell.getFileSystem();

    String[] pathArguments = inputArray;

    // If the user wants to list recursively
    boolean isRecursive = false;
    if (pathArguments.length - 1 >= 0 && pathArguments[0].equals("-R")) {
      isRecursive = true;

      // Remove the recursive part from array
      pathArguments =
          Arrays.copyOfRange(pathArguments, 1, pathArguments.length);
    }

    // Runs the helper method to print the contents
    String contents = "";
    // If there are no paths to run
    if (pathArguments.length == 0) {
      contents = runHelper(fileSys.getCurrentDirectory().getPath(), isRecursive,
          isRecursive, "", jShell);
    } else {
      for (String path : pathArguments) {
        contents += runHelper(path, isRecursive, true, "", jShell);
      }
    }

    // Delete an extra newline
    if (contents.endsWith("\n\n")) {
      contents = contents.substring(0, contents.length() - 2);
    } else if (contents.endsWith("\n")) {
      contents = contents.substring(0, contents.length() - 1);
    }

    return contents;
  }


  /**
   * Add a directory's children (files/directories) to a given string
   *
   * @param path is the path of the directory
   * @param isRecursive is True iff the user wants a recursive listing
   * @param requiresHeader is True iff there are more than 0 directories to
   *        output
   * @param contents is the string to add the contents to
   * @param jShell is used to output errors to the shell
   * @return the string with added contents
   */
  private String runHelper(String path, boolean isRecursive,
      boolean requiresHeader, String contents, JShell jShell) {
    // The directory at the path given
    Directory directoryAtPath = null;
    try {
      directoryAtPath = jShell.getFileSystem().getDirectory(path);
    } catch (NoSuchDirectoryException e) {
      jShell.output(path + " does not exist" + "\n");
    }

    // Add names of this directory's child directories and files to contents
    if (directoryAtPath != null) {
      // Output the header directory
      if (requiresHeader) {
        if (!path.endsWith("/")) {
          path += "/";
        }
        contents += path + ":\n";
      }

      // Add child files' names
      for (File childFile : directoryAtPath.getFiles()) {
        contents += childFile.getName() + "\n";
      }

      // Add child directories' names
      for (Directory childDir : directoryAtPath.getChildrenDirectories()) {
        contents += childDir.getName() + "\n";
      }

      contents += "\n"; // Add a newline to separate this directory and next

      // If the user wants to recursively list, run this method on the
      // subdirectories
      if (isRecursive) {
        // Delete the / so we dont have both
        if (path.endsWith("/")) {
          path = path.substring(0, path.length() - 1);
        }

        // If this directory exists, run this method on each
        for (Directory childDir : directoryAtPath.getChildrenDirectories()) {
          contents = runHelper(path + "/" + childDir.getName() + "/",
              isRecursive, requiresHeader, contents, jShell);
        }
      }
    }

    return contents;
  }

  @Override
  public String getDocumentation() {
    return "Usage: ls [-R] [PATH ...] [>/>> OUTFILE] \n[-R] represents a"
        + " flag to "
        + "recursively"
        + " output the directories \n[PATH ...] represents optional"
        + " arguments "
        + "\n... represents a list \nIf [PATH ...] is not provided, output"
        + " the "
        + "contents of the current directory \nIf [PATH ...] is provided:"
        + " If " + "PATH specifies a file, output PATH; If PATH specifies a"
        + " directory, "
        + "output the contents of the directory PATH \n\nExample: \nls . "
        + "hello \n.: \nex \n\nhello: \nworld \n[>/>> OUTFILE] represents"
        + " an " + "argument to redirect the output to OUTFILE";
  }
}
