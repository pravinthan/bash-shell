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
import java.util.Scanner;

/**
 * This class represents the jshell program
 * 
 * @author Gi Tae Lim
 */
public class JShell {

  // Flag for running main loop
  private boolean exitCond;

  // Hashtable for commands
  private Hashtable<String, Command> commandHashTable;

  // File system instance
  private FileSystem fs;


  // Redirectoin object
  private Redirection redirection;

  // Constant for invalid command
  private String INVALID_COMMAND = "Please enter a valid command" + "\n";


  /**
   * Return string representation of jshell. Includes the commands that jshell
   * can run
   * 
   * @return The string representation
   */
  public String toString() {
    String result = "This is a JShell console that can run unix commands. "
        + "The commands that can be run in this JShell are:\n";

    // Iterate through keys of hashtable and add command names to resulting
    // string
    for (String command : commandHashTable.keySet()) {
      result += command + " ";
    }

    // Return string repr
    return result;
  }


  /**
   * Creates new instance of jshell program
   * 
   * @param fs The file system to inject
   */
  public JShell(FileSystem fs) {
    // Flag for running main loop
    exitCond = false;

    // Hashtable of commands
    commandHashTable = new Hashtable<>();
    // Initialize hashtable
    initializeCommandHashTable(commandHashTable);
    // Inject file system to the given file system
    this.fs = fs;

    // Redirectoin
    redirection = new Redirection();
  }


  /**
   * Returns this jshell's reference to file system
   *
   * @return the reference to file system
   */
  public FileSystem getFileSystem() {
    return fs;
  }


  /**
   * Returns the hash table of commands in this jshell
   * 
   * @return the HashTable<string, string> object
   */
  protected Hashtable<String, Command> getHashTableOfCommands() {
    // Return the hash table
    return commandHashTable;
  }

  /**
   * Initializes the hash table so that the key is what user enters to use a
   * cmd and the value is the command's class name
   * 
   * @param hashtable The hash table to store keys and values into
   */
  private void initializeCommandHashTable(
      Hashtable<String, Command> hashtable) {
    // Add keys and values for all commands
    hashtable.put("cat", new Cat());
    hashtable.put("cd", new Cd());
    hashtable.put("cp", new Cp());
    hashtable.put("curl", new Curl());
    hashtable.put("echo", new Echo());
    hashtable.put("exit", new Exit());
    hashtable.put("find", new Find());
    hashtable.put("grep", new Grep());
    hashtable.put("history", new History());
    hashtable.put("ls", new Ls());
    hashtable.put("man", new Man());
    hashtable.put("mkdir", new Mkdir());
    hashtable.put("mv", new Mv());
    hashtable.put("popd", new PopD());
    hashtable.put("pushd", new PushD());
    hashtable.put("pwd", new Pwd());
    hashtable.put("!", new RepeatCmd());
    hashtable.put("tree", new Tree());
  }


  /**
   * Output the given string using jshell's output (Does not move cursor to
   * next line)
   * 
   * @param outputStr
   */
  public void output(String outputStr) {
    OutputToJShell jshellOutput = new OutputToJShell();
    jshellOutput.output(outputStr);
  }


  /**
   * This method is the main logic method of jshell program
   */
  public void start() {
    // Variable for input command
    String input;
    // // Input command in string array w/ each element a keyword
    // String[] inputArr;

    // Scanner for getting input
    Scanner sc = new Scanner(System.in);

    // Continuously prompt for user input
    while (!exitCond) {
      // input check and adjust spaces
      input = "";
      while (input.length() == 0) {

        // invokes pwd before each input
        Pwd pwd = new Pwd();
        output(pwd.run(this));
        output(" # ");
        input = sc.nextLine();


      }

      // Remove extraneous spaces
      input = input.trim();
      input = replaceSpaces(input);

      // Run the command user entered
      runCommand(input);
    }
    sc.close();
  }


  /**
   * Given the input of user runs the user's command and redirects if needed
   * 
   * @param input User's string input
   */
  protected void runCommand(String input) {

    // Get history object from hashtable and store the input
    try {
      History history = (History) commandHashTable.get("history");
      history.store(input);
    } catch (NullPointerException e) {
    }


    try {
      // Command name string
      String commandName;

      // If user entered command with a space, everything up until space
      // is the command name
      if (input.indexOf(" ") >= 0) {
        commandName = input.substring(0, input.indexOf(" "));
      } else {
        // Else its the entire thing
        commandName = input;
      }

      // Special case for if command name is !
      if (commandName.charAt(0) == '!') {
        commandName = commandName.substring(0, 1);
      }


      // Get class name from hashtable
      Command commandInstance = commandHashTable.get(commandName);

      // Split command args and redirect args
      String[] inputTwoParts =
          redirection.isolateRedirectionArguments(input, commandName);
      String commandPart = inputTwoParts[0];
      String redirectionPart = inputTwoParts[1];

      // If syntax correct to run
      if (commandInstance.areArgumentsValid(commandPart)) {
        String commandExecuteResult = commandInstance.run(commandPart, this);

        // If redirecting
        if (!redirectionPart.equals("")) {
          // Get the redirection option and outifle from the redirection
          // part of user input
          String[] redirectionArgumentsArr =
              redirection.redirectionPartToArray(redirectionPart);
          String option = redirectionArgumentsArr[0];
          String outifle = redirectionArgumentsArr[1];

          // If command result is null
          if (commandExecuteResult == null) {
            // Else command result is null
            // Redirect with contents ""
            redirection.redirectOutput("", option, outifle, this);
          } else {
            // Else result is not null
            // Redirect with contents being command result
            redirection.redirectOutput(commandExecuteResult, option, outifle,
                this);
          }
        } else {
          // Else not redirecting
          // Output result of command to console if command has an output
          if (commandExecuteResult != null) {
            output(commandExecuteResult + "\n");
          }
        }
      } else {
        // Else not valid syntax output invalid command
        output(INVALID_COMMAND);
      }



    } catch (NullPointerException e) {
      // Catch
      // Output invalid command
      output(INVALID_COMMAND);
    }
  }


  /**
   * if there are more than 2 spaces in a row in the string, to combine them
   * into just 1 space. unless the spaces are inside brackets, then it is
   * ignored
   *
   * @param str - the string to replace spaces
   * @return new string with extra spaces replaced
   */
  private String replaceSpaces(String str) {
    String newStr = "";
    boolean skip = false;

    // if the first character is a space, then start skipping
    if (str.length() > 0) {
      if (str.charAt(0) == '"') {
        skip = true;
      }
      // add the first char which is skipped in loop
      newStr += str.charAt(0);
    }

    // loop through the rest of the string and remove spaces if they are not
    // surrounded by "
    for (int i = 1; i < str.length(); i++) {
      if (str.charAt(i) == '"') {
        skip = !skip;
      }
      if (skip) {
        newStr += str.charAt(i);
      } else if (!(str.charAt(i - 1) == ' ' && str.charAt(i) == ' ')) {
        newStr += str.charAt(i);
      }
    }
    return newStr;
  }


  /**
   * Terminates the jshell program
   */
  protected void exitJShell() {
    this.exitCond = true;
  }

}

