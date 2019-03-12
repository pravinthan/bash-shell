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
 * JShellFileSystem holds all the directories and files to simulate a file
 * system
 *
 * @author Pravinthan Prabagaran
 */
public class JShellFileSystem implements FileSystem {

  private static Directory ROOT;
  private JShellCurrentDirectorySystem currentDirectory;

  /**
   * Creates an instance of JShellFileSystem
   */
  public JShellFileSystem() {
    try {
      ROOT = Directory.createDirectoryWithoutParent("");
    } catch (InvalidNameException e) {
    }

    currentDirectory = new JShellCurrentDirectorySystem(ROOT);
  }

  /**
   * Returns the root of the file system
   *
   * @return the root of the file system
   * @throws NoSuchDirectoryException if root is not initialized
   */
  public static Directory getRoot() throws NoSuchDirectoryException {
    return ROOT;
  }

  /**
   * Returns the directory given its path
   *
   * @param path The path of the desired directory
   * @return The directory object at the path
   * @throws NoSuchDirectoryException When there is no directory at the given
   *         path
   * @author Gi Tae Lim
   */
  public Directory getDirectory(String path) throws NoSuchDirectoryException {
    // Changes path from relative to absolute
    path = convertRelativeToAbsolutePath(path);

    // Adds a necessary / at end if it is not given
    if (path.charAt(path.length() - 1) != '/') {
      path += "/";
    }

    // Run the helper
    return getDirectoryHelper(path, ROOT);
  }


  /**
   * Return the directory given its path
   *
   * @param remainingDirs The remaining string of the path of the next 
   *        directory to look for in currDir
   * @param currDir The current directory object to look for the next directory
   * @return The directory with the name/path as in remaininDirs
   * @throws NoSuchDirectoryException When trying to get directory that is not
   *         in file system
   * @author Gi Tae Lim
   */
  private Directory getDirectoryHelper(String remainingDirs, Directory currDir)
      throws NoSuchDirectoryException {
    Directory returnDirectory;

    // If no more remaining dirs to go through
    if (remainingDirs.equals("") || remainingDirs.equals("/")) {
      // Resulting dir is the curr dir
      returnDirectory = currDir;
    } else { // Else want to get the next dir name
      // The name of the next dir to be passed into this method
      String nextDirName;
      // The next dir obj
      Directory nextDir = null;

      // Next dir name is the substring in between the first and second '/'
      // Find index of first and second /
      int nextDirNameStart = remainingDirs.indexOf('/');
      int nextDirNameEnd = remainingDirs.indexOf('/', nextDirNameStart + 1);

      // Initialize the next dir name
      nextDirName =
          remainingDirs.substring(nextDirNameStart + 1, nextDirNameEnd);

      // Remove the next dir name from the remaining dirs but include the /
      // at start
      String newRemainingDirs = remainingDirs.substring(nextDirNameEnd);

      // If next dir is the curr dir keyword "."
      if (nextDirName.equals(".")) {
        // Next dir is just whatever dir we are at
        nextDir = currDir;

        // If the next dir is the parent keyword ".."
        // and this dir is not the root
      } else if (nextDirName.equals("..") && !currDir.equals(getRoot())) {
        // Our next dir is this curr dir's parent
        nextDir = currDir.getParent();

        // If next dir is parent keyword and this curr dir is the root
      } else if (nextDirName.equals("..")) {
        // Next dir is just the root or the just itself
        nextDir = currDir;

        // Else given a name for a new dir
      } else {
        // Look for next dir object in the curr dir's children
        for (Directory childDir : currDir.getChildrenDirectories()) {
          // If curr child dir has same name is our desired next dir
          if (childDir.getName().equals(nextDirName)) {
            // Set the next dir obj to the current child dir
            nextDir = childDir;
          }
        }
      }

      // At this point if next directory is still null
      // this means that dir DNE in curr dir
      // therefore throw exception
      if (nextDir == null) {
        throw new NoSuchDirectoryException("There is no directory "
            + nextDirName + " in " + currDir.getPath());
      }

      // Recursively call this helper method with new input params
      returnDirectory = getDirectoryHelper(newRemainingDirs, nextDir);
    }

    // Return the new directory
    return returnDirectory;
  }

  /**
   * Takes a path and converts it to absolute, if not already
   *
   * @param path is the path to change
   * @return the absolute path
   */
  private String convertRelativeToAbsolutePath(String path) {
    // If the path does not start with '/', it's a relative path
    if (path.indexOf('/') != 0) {
      path = this.currentDirectory.getCurrentDirectory().getPath() + path;
    }

    return path;
  }

  /**
   * Gets the file at a given path
   *
   * @param path is the path to find the file
   * @return the file at the given path
   * @throws NoSuchFileException if there is no file at the given path
   */
  public File getFile(String path) throws NoSuchFileException {
    // Changes path from relative to absolute
    path = convertRelativeToAbsolutePath(path);

    // Gets the directory of the file and then gets the file within the
    // directory
    Directory parent;
    try {
      parent = getDirectory(path.substring(0, path.lastIndexOf('/') + 1));
    } catch (NoSuchDirectoryException e) {
      parent = null;
    }

    // If the parent directory was not found, throw an exception
    if (parent == null) {
      throw new NoSuchFileException("No such file at path: " + path);
    }

    return parent.getFile(path.substring(path.lastIndexOf('/') + 1));
  }

  /**
   * Adds the given directory to the current directory
   *
   * @param directory is the directory to add
   */
  public void addDirectory(Directory directory) {
    // Adds given directory to the current directory and sets the given
    // directory's parent as the current directory
    currentDirectory.getCurrentDirectory().addChildDirectory(directory);
    directory.setParent(currentDirectory.getCurrentDirectory());
  }

  /**
   * Returns the current directory
   *
   * @return the current directory
   */
  public Directory getCurrentDirectory() {
    // Return the current directory in instance of CurrentDirectory
    return this.currentDirectory.getCurrentDirectory();
  }

  /**
   * Sets the current directory to a given one
   *
   * @param directory is the directory to set to
   */
  public void setCurrentDirectory(Directory directory) {
    // Set current directory in instance of CurrentDirectory
    this.currentDirectory.setCurrentDirectory(directory);
  }

  /**
   * Save the current directory user is in
   */
  public void saveCurrDir() {
    // Invoke method of CurrentDirectory to save the current directory
    this.currentDirectory.pushCurrDir();
  }

  /**
   * Remove and return the most recent saved current working directory
   *
   * @return the most recent stored current working directory object
   * @throws NoSavedWorkingDirectoryException if there is no saved working
   *         directory
   */
  public Directory getPrevCurrDir() throws NoSavedWorkingDirectoryException {
    // Call method of CurrentDirecotyr to pop the current directory
    return this.currentDirectory.popCurrDir();
  }
}
