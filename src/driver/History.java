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
 * outputs all the previous inputs of the user if a number is given,
 * then output that number of most recent inputs
 *
 * @author Ao
 */
public class History extends Command {

  private ArrayList<String> list = new ArrayList<>(); // for storing input

  /**
   * returns all the items in history
   *
   * @return all items in history
   */
  @Override
  public String toString() {
    // return all the strings in the arraylist
    String ret = "";
    for (String e : list) {
      ret += e + "\n";
    }
    return ret;
  }

  /**
   * checks if the syntax of the commandKeyWords are valid for history
   *
   * @param userInput the string of the user input
   * @return whether the syntax is valid for history command
   */
  @Override
  public boolean areArgumentsValid(String userInput) {
    String[] commandKeyWords = userInputStringToArray(userInput);
    boolean valid = false;

    // second arg is int
    if (commandKeyWords.length == 2 && isInt(commandKeyWords[1])) {
      valid = true;
    } else if (commandKeyWords.length == 1) {
      valid = true;
    }
    return valid;
  }

  /**
   * outputs previous commands entered by the user
   *
   * @param userInput the string of the user input
   * @param jShell the jshell instance the command was executed in
   */
  @Override
  public String run(String userInput, JShell jShell) {
    String[] commandKeyWords = userInputStringToArray(userInput);

    String result = "";
    // if input is just history
    if (commandKeyWords.length == 1) {
      // output all the history
      int counter = 1;
      for (String e : list) {
        result += Integer.toString(counter) + ". " + e + "\n";
        counter++;
      }

    } else {
      // output only the given number of history
      int start = list.size() - Integer.parseInt(commandKeyWords[1]);

      // prevent start from being less than 0 or greater than list.size
      if (start < 0) {
        start = 0;
      } else if (start > list.size()) {
        start = list.size();
      }

      // output given number
      for (int i = start; i < list.size(); i++) {
        result += Integer.toString(i + 1) + ". " + list.get(i) + "\n";
      }
    }

    return result.substring(0, result.length()-1);
  }


  /**
   * save string to history
   *
   * @param s - string to add to history
   */
  public void store(String s) {
    list.add(s);
  }

  /**
   * checks if str is int
   *
   * @param str - string to check if there is an integer equivalent
   * @return whether there is int version of string
   */
  private boolean isInt(String str) {
    // if can't parse then str has no int equal
    try {
      int i = Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  /**
   * the documentation that describes the command
   *
   * @return how to use the command in string form
   */
  @Override
  public String getDocumentation() {
    return "Usage: history [number] [>/>> OUTFILE]"
        + " \n[number] represents an optional "
        + "argument, where number >= 0 \nIf [number] is not provided, output "
        + "all commands typed \nIf [number] is provided, output the [number] "
        + "most recent commands \n\nExample: \nhistory 2 \n1. cd .. \n2. "
        + "history \n[>/>> OUTFILE] represents an argument to redirect the "
        + "output to OUTFILE";
  }

  protected ArrayList<String> getHistory() {
    return list;
  }

}
