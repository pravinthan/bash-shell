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

/**
 * This class represents a generic command class
 *
 * @author Gi Tae Lim, Ao Gao
 */
public abstract class Command {


  /**
   * Return the string representation of this command
   *
   * @return This command's string representation
   */
  public abstract String toString();


  /**
   * Return this command's documentation
   *
   * @return Command documentation
   */
  public abstract String getDocumentation();


  /**
   * Check whether the given user input is a valid command includes the name of
   * command
   *
   * @param userInput The string input user entered into console
   * @return True if commandKeyWords is in correct syntax, else false
   */
  public abstract boolean areArgumentsValid(String userInput);


  /**
   * Execute this command
   *
   * @param userInput The string input user entered into console
   * @param jshell The jshell instance this command was executed in
   */
  public abstract String run(String userInput, JShell jshell);


  /**
   * Formats and converts the string user enters into an array of string
   * elements where each element each word in original string
   *
   * @param userInput The original user's input string
   * @return The array of strings with each element as a single word from user
   *         input
   */
  protected final String[] userInputStringToArray(String userInput) {
    // String array to return
    String[] result;

    // Split into array by each keyword
    result = split(userInput);

    // Return resulting array
    return result;
  }


  /**
   * split the given string into an array, each element a word, separated by
   * spaces, everything inside " counts as 1 element
   *
   * @param str - the string to split up into array
   * @return String array containing words
   */
  private String[] split(String str) {
    // first and last characters are not spaces
    ArrayList<String> list = new ArrayList<>();
    boolean skip = false;
    int startIndex = 0;

    // loop through each character in the string
    for (int i = 0; i < str.length(); i++) {

      // check whether to exit or enter skip state
      if (str.charAt(i) == '"') {
        if (!skip) {
          skip = true;
        } else {
          skip = false;
        }
      }

      // if not in skip stat and find space, add to list
      if (!skip && str.charAt(i) == ' ') {
        list.add(str.substring(startIndex, i));
        startIndex = i + 1;

        // if at the end of the string
      } else if (i == str.length() - 1) {
        // add the rest
        list.add(str.substring(startIndex));
      }
    }
    return list.toArray(new String[list.size()]);
  }

}
