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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Grep is a class to hold all the methods required for the grep command to
 * work
 *
 * @author Ao Gao
 * @author Pravinthan Prabagaran
 */
public class Grep extends Command {

  /**
   * Returns a string representation of the grep class
   *
   * @return String that defines the cd class
   */
  @Override
  public String toString() {
    return "Grep commmand is used to search for lines in files that match a "
        + "regular expression";
  }

  /**
   * checks the syntax of the user input
   *
   * @param userInput The string input user entered into console
   * @return whether the syntax was valid
   */
  @Override
  public boolean areArgumentsValid(String userInput) {
    String[] commandKeyWords = userInputStringToArray(userInput);

    boolean valid = false;

    // if recursive, check regex is surrounded by ", and has path
    if (commandKeyWords.length > 3 && commandKeyWords[1].equals("-R")) {
      if (startEnd(commandKeyWords[2], "\"")) {
        valid = true;
      }
      // if no recursive, check regex is surrounded by ", and has a path
    } else if (commandKeyWords.length > 2) {
      if (startEnd(commandKeyWords[1], "\"")) {
        valid = true;
      }
    }

    return valid;

  }

  /**
   * looks for all instances of the syntax in the given paths
   *
   * @param input is the user input
   * @param jShell the jshell instance the command was executed in
   * @return the paths with where the given regex was found
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

    // Get the regex from the input and then delete it from the array
    String regex = pathArguments[0].substring(1, pathArguments[0].length() - 1);

    pathArguments = Arrays.copyOfRange(pathArguments, 1, pathArguments.length);

    // Runs the helper method to print the contents
    String contents = "";
    for (String path : pathArguments) {
      boolean isFile = true;
      try {
        fileSys.getFile(path);
      } catch (NoSuchFileException e) {
        isFile = false;
      }

      contents += runHelper(path, regex, isFile, isRecursive, "", jShell);
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
   * Adds the lines containing regex to contents
   *
   * @param path is the path of the directory or file to search
   * @param regex is the regular expression to match to
   * @param isFile is true iff path is a file, false if not
   * @param isRecursive is true iff the user wants to search recursively, false
   * if not
   * @param contents is the concatenated string of matching strings
   * @param jShell is an instance of JShell used to output errors
   * @return the concatenated contents
   */
  private String runHelper(String path, String regex, boolean isFile,
      boolean isRecursive, String contents, JShell jShell) {
    // The directory at the path given
    Directory directoryAtPath = null;
    try {
      directoryAtPath = jShell.getFileSystem().getDirectory(path);
    } catch (NoSuchDirectoryException e) {
      if (!isFile) {
        jShell.output(path + " does not exist" + "\n");
      }
    }

    // Get the contents of the file
    if (isFile) {
      // Output the header
      contents += path + ":\n";

      // Get the file contents
      String fileContents = "";
      try {
        fileContents = jShell.getFileSystem().getFile(path).getContents();
      } catch (NoSuchFileException e) {
      }

      // Add the matched parts to contents
      contents += findRegex(regex, fileContents);

      contents += "\n"; // Add newline
    } else if (directoryAtPath != null) {
      // Delete the / so we dont have both
      if (path.endsWith("/")) {
        path = path.substring(0, path.length() - 1);
      }

      // Run this helper on each of the child files
      for (File childFile : directoryAtPath.getFiles()) {
        contents = runHelper(path + "/" + childFile.getName(), regex, true,
            isRecursive, contents, jShell);
      }

      // Run this helper on each of the child directories
      if (isRecursive) {
        for (Directory childDir : directoryAtPath.getChildrenDirectories()) {
          contents = runHelper(path + "/" + childDir.getName() + "/", regex,
              false, isRecursive, contents, jShell);
        }
      }
    }

    return contents;
  }

  /**
   * Finds strings matching to regex
   *
   * @param regex is the regular expression to match to
   * @param data is the string to search
   * @return the matched strings
   */
  private String findRegex(String regex, String data) {
    String retString = "";
    String[] contents = data.split("\n");

    // Build the pattern and matcher objects
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher;

    // Search for the string that matches the regex
    for (String s : contents) {
      matcher = pattern.matcher(s);
      if (matcher.find()) {
        retString += s + "\n";
      }
    }

    return retString;
  }

  /**
   * checks whether the string s start and ends with given testFor
   *
   * @param s is the string to check if contains
   * @param testFor is the string to test for
   * @return whether the string s starts and ends with string testFor
   */
  private boolean startEnd(String s, String testFor) {
    return s.startsWith(testFor) && s.endsWith(testFor);
  }

  /**
   * Returns the documentation of this command
   *
   * @return documentation as string
   */
  @Override
  public String getDocumentation() {
    return "Usage: grep [-R] REGEX PATH [...] [>/>> OUTFILE] \n[-R] and [...] "
        + "represent "
        + "optional arguments \n-R is given if the search is to be "
        + "recursive "
        + "\n... represents a list \nREGEX is a regular expression string, "
        + "surrounded by double quotation marks, to match strings in files "
        + "\nIf -R is not given, any lines containing REGEX in PATH, which "
        + "must be a file, is output \nIf -R is given, and PATH is a "
        + "directory, all lines that contain REGEX in all files "
        + "recursively "
        + "searched is output \n\nExample: \ngrep \"hello world!\" . "
        + "\n./file1: \nhello world! \n\n./file2: \nhi and hello world! \n"
        + "[>/>> OUTFILE] represents an argument to redirect the output to "
        + "OUTFILE";
  }

}
