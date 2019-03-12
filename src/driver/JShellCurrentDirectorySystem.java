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

import java.util.Stack;

/**
 * This class represents the Current Directory of the file system of jshell
 * Keeps a record of the past current working directories as well as the
 * current directory user is in
 *
 * @author Gi Tae Lim
 */
public class JShellCurrentDirectorySystem {

  // The reference to the current working directory
  private Directory currDir;

  // The stack of past curr working directories
  private Stack<Directory> pastCurrDirs = new Stack<>();


  /**
   * Creates a new instance of current directory
   *
   * @param dir Default current directory Directory object
   */
  public JShellCurrentDirectorySystem(Directory dir) {
    // Default it to the given dir
    this.currDir = dir;
  }


  /**
   * Return the current working directory
   *
   * @return the current working directory
   */
  public Directory getCurrentDirectory() {
    return this.currDir;
  }


  /**
   * Given a directory set the current working directory to it
   *
   * @param dir the new current working directory
   */
  public void setCurrentDirectory(Directory dir) {
    // Set the curr directory to given directory object
    this.currDir = dir;
  }


  /**
   * Pushes the given Directory into the stack of current directories
   */
  public void pushCurrDir() {
    // Push the current directory into stack
    pastCurrDirs.push(currDir);
  }


  /**
   * Return the Directory at the top of stack
   *
   * @return the most recent current directory user has pushed
   * @throws NoSavedWorkingDirectoryException When trying to pop when no
   *         directory has been previously saved
   */
  public Directory popCurrDir() throws NoSavedWorkingDirectoryException {
    // If stack empty throw exception
    if (pastCurrDirs.isEmpty()) {
      throw new NoSavedWorkingDirectoryException(
          "There is no current directory saved to change to");
    }

    // Pop from stack
    return pastCurrDirs.pop();
  }

}
