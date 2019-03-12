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
 * outputs the given string if no output file is give creates new file in
 * current dir if the output file does not exist. appends to file if it exists
 * and argument is >> overwrites the file if it exists and argument is >
 *
 * @author Ao
 */
public class Echo extends Command {

  /**
   * returns a string representation of the class
   *
   * @return a cleaver string
   */
  @Override
  public String toString() {
    return "echo...echo...echo...";
  }

  /**
   * checks if the syntax for echo command is valid
   *
   * @param userInput is keywords strings for command
   * @return true if the syntax is valid for echo, false o/w
   */
  @Override
  public boolean areArgumentsValid(String userInput) {
    String[] commandKeyWords = userInputStringToArray(userInput);

    boolean valid = false;

    // length has to be length 2
    // if there is a string arg then it has to be at least length 3
    if (commandKeyWords.length == 2) {
      if (commandKeyWords[1].length() > 1
          && startEnd(commandKeyWords[1], "\"")) {
        valid = true;
      }
    }
    return valid;
  }

  /**
   * outputs the given string if no output file is give creates new file in
   * current dir if the output file does not exist appends to file if it exists
   * and argument is >> overwrites the file if it exists and argument is >
   *
   * @param userInput is the string of user input
   * @param jShell the jshell instance the command was executed in
   */
  @Override
  public String run(String userInput, JShell jShell) {
    String[] commandKeyWords = userInputStringToArray(userInput);
    return commandKeyWords[1].substring(1, commandKeyWords[1].length() - 1);
  }

  /**
   * the documentation that describes the command
   *
   * @return how to use the command in string form
   */
  @Override
  public String getDocumentation() {
    return "Usage: echo STRING [> FILE] OR echo STRING [>> FILE] "
        + "\n[> FILE] and [>> FILE] represent optional arguments \nSTRING is "
        + "surrounded by double quotation marks \nIf [> FILE] nor [>> FILE] "
        + "is not provided, STRING will be printed to the shell \nIf "
        + "[> FILE] is provided, overwrite the current contents of FILE with "
        + "STRING \nIf [>> FILE] is provided, append STRING to FILE \nIf "
        + "FILE does not exist, FILE will be created with STRING as its "
        + "contents \n\nExample: \necho \"hello world\" \nhello world";
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

}
