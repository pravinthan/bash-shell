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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * find the file or directory given the path. Is not recursive
 *
 * @author Ao
 */
public class Find extends Command {

  /**
   * Returns a string representation of the find class
   *
   * @return String that defines the find class
   */
  public String toString() {
    return "Given one or more directories search these directories for"
        + "the given file or directory name";
  }

  /**
   * Validates a given String input command user entered and returns true if it
   * is formatted correctly commandKeyWords is valid 6 or 7 keywords last
   * keyword is surrounded by " second last is "-name" third last is "f" or "d"
   * 4th last is "-type"
   *
   * @param userInput string where each word is a keyword of the user's input
   *        command that needs validation
   * @return boolean True if commandKeyWords is valid, else false
   */
  @Override
  public boolean areArgumentsValid(String userInput) {
    String[] commandKeyWords = userInputStringToArray(userInput);
    boolean valid = true;

    String lookFor = commandKeyWords[commandKeyWords.length - 1];
    String nameKey = commandKeyWords[commandKeyWords.length - 2];
    String type = commandKeyWords[commandKeyWords.length - 3];
    String typeKey = commandKeyWords[commandKeyWords.length - 4];
    int commandsMinLength = 5;

    // there are not enough arguments
    if (!(commandKeyWords.length >= commandsMinLength)) {
      valid = false;

      // last is surrounded by " or string is empty ""
    } else if (!startEnd(lookFor, "\"") || lookFor.length() < 3) {
      valid = false;

      // second last is -name
    } else if (!nameKey.equals("-name")) {
      valid = false;

      // third last is "f" or "d"
    } else if (!(type.equals("f") || type.equals("d"))) {
      valid = false;

      // last is "-type"
    } else if (!typeKey.equals("-type")) {
      valid = false;
    }

    return valid;
  }

  /**
   * returns the location of where the file/director is found
   *
   * @param userInput is the string of the user inputs
   * @param jShell the jshell instance the command was executed in
   */
  @Override
  public String run(String userInput, JShell jShell) {
    String[] commandKeyWords = userInputStringToArray(userInput);
    commandKeyWords =
        Arrays.copyOfRange(commandKeyWords, 1, commandKeyWords.length);

    ArrayList<Directory> searchDirectories = new ArrayList<>();
    ArrayList<String> searchDirectoriesNames = new ArrayList<>();

    String retString = "";

    // get file system to search through
    FileSystem fs = jShell.getFileSystem();

    // search for all the paths given in commandKeywords, save them if they
    // exist, otherwise output an error message
    for (int i = 0; i < commandKeyWords.length - 4; i++) {
      try {
        String dirName = commandKeyWords[i];
        Directory dir = fs.getDirectory(dirName);

        searchDirectories.add(dir);
        searchDirectoriesNames.add(dirName);

      } catch (NoSuchDirectoryException nsde) {
        retString +=
            "Directory " + commandKeyWords[i] + " does not exist" + "\n";
      }
    }
    int index = 0;

    // for each path, search for the desired file or directory
    for (Directory e : searchDirectories) {

      // file or directory to search for
      String searchName = commandKeyWords[commandKeyWords.length - 1];
      // get rid of surrounding "
      searchName = searchName.substring(1, searchName.length() - 1);
      // if looking for directory
      if (commandKeyWords[commandKeyWords.length - 3].equals("d")) {

        // compare directory names in directory: e
        ArrayList<Directory> curr = e.getChildrenDirectories();

        boolean foundDir = false;
        for (Directory d : curr) {
          // if found
          if (d.getName().equals(searchName)) {
            retString += "Found 1 instance of " + searchName + " in "
                + searchDirectoriesNames.get(index) + "\n";
            foundDir = true;
          }
        }
        // if the directory is not found
        if (!foundDir) {
          retString += "There is no directory " + searchName + " in "
              + searchDirectoriesNames.get(index) + "\n";
        }

        // if looking for file
      } else {
        try {
          // if found
          String foundFile = e.getFile(searchName).getName();
          retString += "Found 1 instance of " + foundFile + " in "
              + searchDirectoriesNames.get(index) + "\n";
        } catch (NoSuchFileException nsfe) {
          // if not found
          retString += "File " + searchName + " does not exist" + " in "
              + e.toString() + "\n";
        }
      }
      index++;
    }
    return retString;
  }

  /**
   * checks whether the string s start and ends with given testFor
   *
   * @param s is the string to check if contains
   * @param testFor is the string to testfor
   * @return whether the string s starts and ends with string testFor
   */
  private boolean startEnd(String s, String testFor) {
    return s.startsWith(testFor) && s.endsWith(testFor);
  }

  /**
   * the documentation that describes the command
   *
   * @return how to use the command in string form
   */
  @Override
  public String getDocumentation() {
    return "Usage: find PATH1 [PATH2 ...] -type f -name NAME [>/>> OUTFILE] OR "
        + "find PATH1 "
        + "[PATH2 ...] -type d -name NAME [>/>> OUTFILE] \n[PATH2 ...] "
        + "represents optional "
        + "arguments \n... represents a list \nIf -type f is provided, "
        + "then "
        + "output all files' paths found in paths PATH1 [PATH2 ...] with "
        + "name "
        + "NAME \nIf -type d is provided, then output all directories' "
        + "paths "
        + "found in paths PATH1 [PATH2 ...] with name NAME \n\nExample: "
        + "\\nfind ex ex1 -type d -name \"hello\" \nex/hello \nex1/hello \n"
        + "[>/>> OUTFILE] represents an argument to redirect the output to "
        + "OUTFILE";
  }

}
