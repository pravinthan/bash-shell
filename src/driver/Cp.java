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
 * Cp is a class to hold all the methods required for the cp command to work
 *
 * @author Ao Gao
 */
public class Cp extends Command {

  /**
   * Returns a string representation of the cp class
   *
   * @return String that defines the cd class
   */
  @Override
  public String toString() {
    return "Copy";
  }

  /**
   * Returns the documentation of this command
   *
   * @return documentation as string
   */
  @Override
  public String getDocumentation() {
    return "Usage: cp PATH1 PATH2 [>/>> OUTFILE] \nIf PATH1 and PATH2 are "
        + "directories, then copy directory PATH1 to PATH2 \nIf PATH1 is a "
        + "directory and PATH2 doesn't exist, create directory PATH2 with "
        + "PATH1's contents \nIf PATH1 is a file and PATH2 is a file, "
        + "overwrite file PATH2's contents with file PATH1 \nIf PATH1 is a "
        + "file and PATH2 is a directory, copy file PATH1 to directory PATH2 "
        + "\nIf PATH1 is a file and PATH2 does not exist, create a new file "
        + "PATH2 with file PATH1's contents \n[>/>> OUTFILE] represents an "
        + "argument to redirect the output to OUTFILE";
  }

  /**
   * checks the syntax of the user input
   * @param userInput The string input user entered into console
   * @return whether the syntax is valid
   */
  @Override
  public boolean areArgumentsValid(String userInput) {
    String[] commandKeyWords = userInputStringToArray(userInput);
    boolean result = false;

    if (commandKeyWords.length == 3) {
      result = true;
    }

    return result;
  }

  /**
   * Runs the cp command to change file/directory to the given file/directory
   * @param userInput The string input user entered into console
   * @param jShell is the jshell instance the command was executed in
   * @return null, if invalid operations, string of what was copied if
   * run terminates
   */
  @Override
  public String run(String userInput, JShell jShell) {
    String[] commandKeyWords = userInputStringToArray(userInput);

    // Get the reference to file system
    FileSystem fileSys = jShell.getFileSystem();

    // Find out which arguments are files and which are directories
    File fileSource;
    File fileDestination;
    Directory directorySource;
    Directory directoryDestination;

    String result = "";

    // find the directory/ file to copy
    try {
      fileSource = fileSys.getFile(commandKeyWords[1]);
    } catch (NoSuchFileException e) {
      fileSource = null;
    }

    try {
      directorySource = fileSys.getDirectory(commandKeyWords[1]);
    } catch (NoSuchDirectoryException e) {
      directorySource = null;
    }

    // Try and catch for the second argument
    try {
      fileDestination = fileSys.getFile(commandKeyWords[2]);
    } catch (NoSuchFileException e) {
      fileDestination = null;
    }

    try {
      directoryDestination = fileSys.getDirectory(commandKeyWords[2]);
    } catch (NoSuchDirectoryException e) {
      directoryDestination = null;
    }

    // cases:

    // no source
    if (fileSource == null && directorySource == null) {
      jShell.output("source file/directory does not exist\n");
      return null;

      // has file but no destination
    } else if (fileSource != null && fileDestination == null
        && directoryDestination == null) {
      // Creates the file
      jShell.runCommand("echo " + "\"" + fileSource.getContents() + "\" > "
          + commandKeyWords[2]);

      // no destination
    } else if (fileDestination == null && directoryDestination == null) {
      jShell.output("destination file/directory does not exist");
      return null;

      // file > file
    } else if (fileSource != null && fileDestination != null) {
      result += "copied " + commandKeyWords[1] + " to " + commandKeyWords[2];
      fileDestination.setContents(fileSource.getContents());

      // file > dir
    } else if (fileSource != null && directoryDestination != null) {
      result += "copied " + commandKeyWords[1] + " to " + commandKeyWords[2];
      try {
        new File(fileSource.getName(), directoryDestination,
            fileSource.getContents());
      } catch (InvalidNameException ine) {
      }

      // dir > dir
    } else if (directorySource != null && directoryDestination != null) {
      result += "copied " + commandKeyWords[1] + " to " + commandKeyWords[2];
      copy(directorySource, directoryDestination, jShell);

      // dir > file (mismatch type)
    } else {
      jShell.output("cannot copy directory to file");
      return null;
    }

    // return null
    return result;
  }

  /**
   * makes a deep copy of directory into parent directory
   * @param directory is the deep copy to be created
   * @param parentDirectory is the parent of the deep copy
   * @param jShell contains FileSystem which has the directories
   * @return new deep copy of direcotry
   */
  private Directory copy(Directory directory, Directory parentDirectory,
      JShell jShell) {

    Directory newCopy = null;

    //create deep copy of given directory and add it to parent directory
    try {
      newCopy = Directory.createDirectoryWithParent(directory.getName(),
          parentDirectory);
      for (Directory childDir : directory.getChildrenDirectories()) {
        newCopy.addChildDirectory(copy(childDir, newCopy, jShell));
      }
      for (File childFile : directory.getFiles()) {
        newCopy.addFile(childFile);
      }

    } catch (InvalidNameException e) {
      jShell.output(e.getMessage());
    }

    return newCopy;
  }

}
