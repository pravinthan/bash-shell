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
 * Directory holds all the methods and variables required for using a directory
 *
 * @author Pravinthan Prabagaran
 */
public class Directory {

  private String name;
  private String path;
  private Directory parent;
  private ArrayList<Directory> childrenDirectories;
  private ArrayList<File> files;

  /**
   * Creates an instance of Directory with name and parent
   *
   * @param name is the name of this directory
   * @param parent is the parent directory of this directory
   * @throws InvalidNameException if the name is not alphanumeric
   */
  private Directory(String name, Directory parent) throws InvalidNameException {
    // Set the corresponding references to the given ones
    files = new ArrayList<File>();
    this.parent = parent;
    this.childrenDirectories = new ArrayList<Directory>();
    setName(name);

    if (this.parent != null) {
      this.parent.childrenDirectories.add(this);
    }
  }

  /**
   * Factory method that returns an instance of Directory without a parent
   *
   * @param name is the name of this directory
   * @return an instance of Directory with parent as null
   * @throws InvalidNameException if the name is not alphanumeric
   */
  public static Directory createDirectoryWithoutParent(String name)
      throws InvalidNameException {
    return new Directory(name, null);
  }

  /**
   * Factory method that returns an instance of Directory with a parent
   *
   * @param name is the name of this directory
   * @param parent is the parent directory of this directory
   * @return an instance of Directory
   * @throws InvalidNameException if the name is not alphanumeric
   */
  public static Directory createDirectoryWithParent(String name,
      Directory parent) throws InvalidNameException {
    return new Directory(name, parent);
  }

  /**
   * Return the name of this directory
   *
   * @return the name of this directory
   */
  public String getName() {
    return this.name;
  }

  /**
   * Set the name of this directory to a given one
   *
   * @param name is the name to set to
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
      // Check dirs for same name
      for (Directory directory : this.parent.childrenDirectories) {
        if (directory.getName().equals(name)) {
          throw new InvalidNameException(
              "There already exists a directory with name \"" + name
                  + "\" in directory " + this.parent.getPath());
        }
      }

      // Check files for same names
      for (File file : this.parent.getFiles()) {
        if (file.getName().equals(name)) {
          throw new InvalidNameException(
              "There already exists a file with name \"" + name
                  + "\" in directory " + this.parent.getPath());
        }
      }
    }

    this.name = name;
    resetPath();
  }

  /**
   * Reset the path of this directory
   */
  private void resetPath() {
    // If the root has not been initialized, then make the path equal to "/"
    Directory root;
    try {
      root = JShellFileSystem.getRoot();
    } catch (NoSuchDirectoryException e) {
      root = null;
    }

    // Set the path as a path of a parent, if there is one
    if (root == null) {
      this.path = "/";
    } else if (this.parent == null) {
      this.path = "/" + this.name + "/";
    } else {
      this.path = this.parent.path + this.name + "/";
    }
  }

  /**
   * Return this directory's path
   *
   * @return this directory's path
   */
  public String getPath() {
    return this.path;
  }

  /**
   * Returns the file with the given name
   *
   * @param name is the name of the file
   * @return the file with the given name
   * @throws NoSuchFileException if there is no file with the given name
   */
  public File getFile(String name) throws NoSuchFileException {
    File returnFile = null;
    for (int i = 0; i < files.size(); i++) {
      if (files.get(i).getName().equals(name)) {
        returnFile = files.get(i);
      }
    }

    if (returnFile == null) {
      throw new NoSuchFileException("No such file in this directory");
    }

    return returnFile;
  }

  /**
   * Return a list of all files of this directory
   *
   * @return the list of all files of this directory
   */
  public ArrayList<File> getFiles() {
    return this.files;
  }

  /**
   * Delete a file with the given name
   *
   * @param name is the name of the file to delete
   * @throws NoSuchFileException if there is no file in this directory
   */
  public void deleteFile(String name) throws NoSuchFileException {
    // Find the file by looping through all files and checking names
    for (int i = 0; i < files.size(); i++) {
      if (files.get(i).getName().equals(name)) {
        files.remove(files.get(i));
        return;
      }
    }

    // Throw an exception if there is no file in this directory
    throw new NoSuchFileException("No such file in this directory");

  }

  /**
   * Add a given file to this directory
   *
   * @param file is the file to add
   */
  public void addFile(File file) {
    files.add(file);
  }

  /**
   * Returns the parent directory of this directory
   *
   * @return the parent directory of this directory
   * @throws NoSuchDirectoryException if there is no parent directory
   */
  public Directory getParent() throws NoSuchDirectoryException {
    if (parent == null) {
      throw new NoSuchDirectoryException(
          "There is no parent for this directory");
    }

    return parent;
  }

  /**
   * Set the parent of this directory to the given directory
   *
   * @param parent is the parent directory to set to
   */
  public void setParent(Directory parent) {
    this.parent = parent;
    this.parent.childrenDirectories.add(this);
  }

  /**
   * Returns a list of all child directories of this directory
   *
   * @return a list of all child directories of this directory
   */
  public ArrayList<Directory> getChildrenDirectories() {
    return this.childrenDirectories;
  }

  /**
   * Adds the given child directory to the list of child directories
   *
   * @param childDirectory is the directory to add
   */
  public void addChildDirectory(Directory childDirectory) {
    this.childrenDirectories.add(childDirectory);
  }

  /**
   * Removes the given child directory from the list of child directories
   *
   * @param childDirectory is the directory to remove
   */
  public void removeChildDirectory(Directory childDirectory) {
    childrenDirectories.remove(childDirectory);
  }
}
