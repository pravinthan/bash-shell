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
 * This class represents the cat command in JShell
 *
 * @author Thomas Lo
 */
public class Cat extends Command {

  /**
   * Creates an instance of Cat
   */
  public Cat() {
    super();
  }

  /**
   * Returns a string representation of the cat class
   *
   * @return String that defines the cat class
   */
  @Override
  public String toString() {
    String s = "This command is the cat command. Given a file, displays "
        + "the contents of the file on the console. If multiple files are "
        + "given, concatenates the files with a 3 line break in between. ";
    return s;
  }

  /**
   * Validates a given String input command user entered and returns true if it
   * is formatted correctly commandKeyWords is valid
   *
   * @param commandKeyWords An array of strings where each element of the array
   *        is a keyword of the user's input command (ie ["cat", FILE1, FILE2])
   *        that needs to be validated
   * @return boolean True if commandKeyWords is valid, else false
   */
  @Override
  public boolean areArgumentsValid(String commandKeyWords) {
    // boolean defaulted to true
    boolean valid = true;

    // Get command name
    String commandName = this.getClass().getSimpleName().toLowerCase();
    String[] splitArguments = commandKeyWords.split("\\s+");
    if (!commandName.equals(splitArguments[0]) || splitArguments.length <= 1) {
      valid = false;
    }

    return valid;
  }


  /**
   * Give the string contents of a number of files separated by 3 line breaks
   *
   * @param commandKeyWords An array of strings where each element of the array
   *        is the parameter of the command (ie [FILE])
   * @return the string of the contents of each file
   * @throws NoSuchFileException When given a file to display that is not found
   *         in the current working directory
   */
  @Override
  public String run(String commandKeyWords, JShell js) {
    String pathName, output = "";
    String[] fileList = commandKeyWords.split("\\s+");
    File currFile;
    JShellFileSystem fs = (JShellFileSystem) js.getFileSystem();

    for (int i = 1; i < fileList.length; i++) {
      // stores each of the given file paths
      pathName = fileList[i];
      try {
        // sets the current file
        currFile = fs.getFile(pathName);
        // gets the contents of the file
        output += currFile.getContents();
      } catch (NoSuchFileException e) {
        // if the file doesn't exist, prints an error message
        output += e.getMessage();
      }
      // Checks if there is another file to be concatenated
      if (i < fileList.length - 1) {
        // Adds 3 line break
        output += "\n\n\n\n";
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
    return "Usage: cat FILE1 [FILE2 ...] [>/>> OUTFILE] \n[FILE2 ...] "
        + "represents optional arguments \n... represents a list \nDisplays "
        + "contents of FILE1 and [FILE2 ...] in a concatenated form in the "
        + "shell \nContents of each file seperated by 3 line breaks"
        + "\n\nExample: \ncat ex.txt ex1.txt \nhello\n\n\n\nworld\n\n[>/>> "
        + "OUTFILE] represents an argument to redirect the output to OUTFILE";
  }

}
