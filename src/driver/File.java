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
 * File holds all the methods and variables required for using a file
 *
 * @author Pravinthan Prabagaran
 */
public class File {

  private String name;
  private String path;
  private Directory parent;
  private String contents;

  /**
   * Creates an instance of File object
   *
   * @param name is the name of this file
   * @param parent is the parent directory of this file
   * @param contents is the contents of the file
   * @throws InvalidNameException if the name is not alphanumeric
   */
  public File(String name, Directory parent, String contents)
      throws InvalidNameException {
    this.parent = parent;
    setName(name);
    this.parent.addFile(this);
    this.contents = contents;
  }

  /**
   * Return the name of this file
   *
   * @return the name of this file
   */
  public String getName() {
    return this.name;
  }

  /**
   * Set the name of this file to the given one
   *
   * @param name is the new name of this file
   * @throws InvalidNameException if the name is not alphanumeric
   */
  public void setName(String name) throws InvalidNameException {
    // Check for root nullity
    Directory root;
    try {
      root = JShellFileSystem.getRoot();
    } catch (NoSuchDirectoryException e) {
      root = null;
    }

    // Check for invalid names
    if (root != null && name.equals("")) {
      throw new InvalidNameException("No empty string allowed for name");
    } else if (!name.matches("^[a-zA-Z0-9]*$")) {
      throw new InvalidNameException(
          "The only characters allowed are a-z, A-Z, 0-9");
    } else if (this.parent != null) {
      // Check files for same names
      for (File file : this.parent.getFiles()) {
        if (file.getName().equals(name)) {
          throw new InvalidNameException(
              "There already exists a file with name \"" + name
                  + "\" in directory " + this.parent.getPath());
        }
      }

      // Check dirs for same names
      for (Directory dir : this.parent.getChildrenDirectories()) {
        if (dir.getName().equals(name)) {
          throw new InvalidNameException(
              "There already exists a directory with name \"" + name
                  + "\" in directory " + this.parent.getPath());
        }
      }
    }

    this.name = name;
    resetPath();
  }

  /**
   * Reset the path of this file
   */
  private void resetPath() {
    // Set this path to parents name plus given one
    if (this.parent == null) {
      this.path = "/" + name;
    } else {
      this.path = this.parent.getPath() + name;
    }
  }

  /**
   * Return this file's path
   *
   * @return this file's path
   */
  public String getPath() {
    return this.path;
  }

  /**
   * Return this file's parent directory
   *
   * @return this file's parent directory
   */
  public Directory getParent() {
    return this.parent;
  }

  /**
   * Returns the contents of this file
   *
   * @return the contents of this file
   */
  public String getContents() {
    return this.contents;
  }

  /**
   * Set the contents of this file to the given one
   *
   * @param contents is the contents to set to
   */
  public void setContents(String contents) {
    this.contents = contents;
  }
}
