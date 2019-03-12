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
 * This class represents the RepeatCmd command for JShell
 *
 * @author Thomas Lo
 */
public class RepeatCmd extends Command {

  /**
   * Creates an instance of RepeatCmd
   */
  public RepeatCmd() {

  }

  /**
   * Returns a string representation of the RepeatCmd class
   *
   * @return String that defines the RepeatCmd class
   */
  @Override
  public String toString() {
    String s = "This command is the Repeat Command command. Given an '!' and"
        + " a non-negative integer representing how many commands ago it was"
        + " called, runs the same command again";

    return s;
  }

  /**
   * Return the documentation of this command
   *
   * @return This command's documentation
   */
  @Override
  public String getDocumentation() {

    return "Usage: !# [>/>> OUTFILE] \n# is an integer that represents the "
        + "#th previous command \n[>/>> OUTFILE] represents an argument to "
        + "redirect the output to OUTFILE";
  }

  /**
   * Validates a given String input command and returns true if it is formatted
   * correctly command is valid iff it takes the form "![int]"
   *
   * @param command The String input command that needs validation
   * @return boolean True if command is valid, else false
   */
  @Override
  public boolean areArgumentsValid(String command) {
    // Boolean var default to true
    boolean valid = true;

    // false if more than length 1, if the first character is not !
    if (command.length() < 2) {
      valid = false;
    } else {
      // Checks if rest of the command is an integer
      if (!command.substring(1).matches("\\d+")) {
        valid = false;
      }
    }

    return valid;
  }

  /**
   * Runs a previous command given an integer
   *
   * @param userInput command inputed by the user
   * @param jshell instance of JShell
   * @return null
   */
  @Override
  public String run(String userInput, JShell jshell) {
    String output = null;
    String historyOutput;
    // Instance of History
    History history = (History) jshell.getHashTableOfCommands().get("history");
    // Number that the user entered
    int commandSelect = Integer.parseInt(userInput.substring(1)) - 1;
    // Removes this command from history
    history.getHistory().remove(history.getHistory().size() - 1);
    // Gets the command from the history list
    // If commandSelect is too big, changes it to the oldest command index
    if (history.getHistory().size() == 0) {
      output = "History list of commands is empty";
    } else if (commandSelect >= history.getHistory().size()
        || commandSelect == -1) {
      output = "Index given is out of bounds of the History list of commands";
    } else {
      historyOutput = history.getHistory().get(commandSelect);

      // Runs the command
      jshell.runCommand(historyOutput);
    }
    return output;

  }

}
