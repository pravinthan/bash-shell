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

import java.util.Hashtable;

/**
 * Man is a class to hold all the methods required for the man command to work
 *
 * @author Thomas Lo
 */
public class Man extends Command {

  /**
   * Returns the string representation of what this command does
   */
  @Override
  public String toString() {
    String s = "When executed, print the documentation of the desired command";
    return s;
  }

  /**
   * Returns true of given input is in the valid form for this command
   *
   * @param commandKeyWords The user command input split into array of string
   *        where each element is a keyword
   * @return True if valid else false
   */
  @Override
  public boolean areArgumentsValid(String commandKeyWords) {
    // bool var defaulted to true
    boolean valid = true;

    // Invalid input command if doesn't start with man
    // or not exactly 2 keywords
    String[] splitArguments = commandKeyWords.split("\\s+");
    if (splitArguments.length > 2)
      valid = false;

    // Return validity
    return valid;
  }

  /**
   * Output the documentation for given command name
   *
   * @param commandKeyWords Array of keywords of the user's command input where
   *        element at index 0 is the command to output documentation
   * @param jshell the jshell instance the command was executed in
   */
  @Override
  public String run(String commandKeyWords, JShell jshell) {
    String output;
    // Get command that matches to the desired one
    try {
      Command c = getMatchingCommand(commandKeyWords, jshell);
      // Get documentation for this command
      String documentation = c.getDocumentation();
      // Output the documentation
      output = documentation + "\n";
    } catch (NoSuchCommandException | ClassNotFoundException e) {
      // If given command is not valid output so
      output = e.getMessage() + "\n";
    }
    return output;
  }


  /**
   * Returns the command that matches the string at index 0 of commandKeyWords
   *
   * @param commandKeyWords The string array of keywords of this command where
   *        the element at index 0 is the desired command to output its
   *        documentation
   * @param jshell The jshell reference this command was executed
   * @return The command that corresponds to the string at commandKeyWords[0]
   */
  private Command getMatchingCommand(String commandKeyWords, JShell jshell)
      throws NoSuchCommandException, ClassNotFoundException {
    // Resulting command
    Command commandObj;

    // Hashtable of commands
    Hashtable<String, Command> commandTable = jshell.getHashTableOfCommands();

    commandObj = commandTable
        .get(commandKeyWords.substring(commandKeyWords.indexOf(" ") + 1));

    // Return the resulting command
    return commandObj;
  }


  /**
   * Return the documentation of this command
   *
   * @return This command's documentation
   */
  @Override
  public String getDocumentation() {
    return "Usage: man CMD [>/>> OUTFILE] \nOutput the documentation for "
        + "command CMD \n[>/>> OUTFILE] represents an argument to redirect "
        + "the output to OUTFILE";
  }

}
