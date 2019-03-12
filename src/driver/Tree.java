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
 * This class represents the tree command in JShell
 * 
 * @author Thomas Lo
 */
public class Tree extends Command {

  /**
   * Creates an instance of Tree
   */
  public Tree() {
    super();
  }


  /**
   * Returns a string representation of the tree class
   * 
   * @return String that defines the tree class
   * 
   */
  @Override
  public String toString() {
    String s = "This command is the tree command. When invoked, prints a "
        + "a visual representation of the hierarchy of Directories";

    return s;
  }


  /**
   * Validates a given String input command and returns true if it is formatted
   * correctly command is valid iff it takes the form "tree"
   * 
   * @param command The String input command that needs validation
   * @return boolean True if command is valid, else false
   * 
   */
  @Override
  public boolean areArgumentsValid(String command) {
    // Boolean var default to true
    boolean valid = true;

    // Get command name
    String commandName = this.getClass().getSimpleName().toLowerCase();

    if (!command.equals(commandName)) {
      valid = false;
    }
    return valid;
  }

  /**
   * Runs the tree command which prints a string representation of the 
   * hierarchy of Directories
   * 
   * @param jshell is an instance of JShell
   * 
   * @throws NoSuchDirectoryException When the given path to change directory
   *         is not found in the directory structure
   */
  @Override
  public String run(String commandKeyWords, JShell jshell) {
    // Temporary initiation
    String output = "";
    Directory root = jshell.getFileSystem().getCurrentDirectory();
    try {
      // gets the root directory as the first directory
      root = JShellFileSystem.getRoot();
    } catch (NoSuchDirectoryException e) {
      jshell.output(e.getMessage() + "\n");
    }
    // Runs the helper method to print the tree
    return run_helper(root, "", output);
  }

  /**
   * helper method which runs the recursion used to acquire the correct
   * directory names and indentation to be printed
   * 
   * @param dir the directory to be printed and checked for children directory
   * @param indent the size of the indent based on number of parent directories
   * @param otp output class used to print to the console
   */
  public String run_helper(Directory dir, String indent, String output) {
    // Base case: root directory
    if (dir.getName().equals("")) {
      // Prints the name of the directory after the given indentation
      output += indent + "/" + "\n";
    } else {
      output += indent + dir.getName() + "\n";
    }
    // If child directory(s) exist, runs the method on each, increasing the
    // indent size
    if (!dir.getChildrenDirectories().isEmpty()) {
      for (Directory temp : dir.getChildrenDirectories()) {
        output = run_helper(temp, indent + "  ", output);
      }
    }
    return output;
  }


  /**
   * Return the documentation of this command
   * 
   * @return This command's documentation
   */
  @Override
  public String getDocumentation() {
    return "Usage: tree [>/>> OUTFILE] \nOutputs a visual representation of "
        + "the file system (files and directories) \n\nExample: \ntree "
        + "\n/ \n  hello \n  world \n[>/>> OUTFILE] represents an argument to "
        + "redirect the output to OUTFILE";
  }

}
