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

import java.util.Arrays;

/**
 * This class represents a redirection to an outfile for jshell
 *
 * @author Gi Tae Lim, Ao Gao
 */
public class Redirection {

  /**
   * Used echo command to redirect output
   *
   * @param content the string to append or overwrite to path
   * @param redirType string indicating append/overwrite to file
   * @param filePath the file at this is to be appended/overwritten
   * @param jshell The current jShell instance
   */
  public final void redirectOutput(String content, String redirType,
      String filePath, JShell jshell) {
    // file system of jshell
    FileSystem fs = jshell.getFileSystem();

    // if rid of / at the end if there is any
    if (filePath.lastIndexOf('/') == filePath.length() - 1) {
      filePath = filePath.substring(0, filePath.length() - 1);
    }

    // add to current file if it exists
    try {
      File fileToChange = fs.getFile(filePath); // look for file
      String contents = fileToChange.getContents(); // get contents
      if (redirType.equals(">")) { // append or overwrite depending on input
        fileToChange.setContents(content);
      } else {
        fileToChange.setContents(contents + content);
      }

    } catch (NoSuchFileException nsfe) {
      // if no such file, then make a new one
      try {
        // find the path to add to, and name of the new file to be created
        String fileParentPath = findFilePath(filePath);
        String fileName = findFileName(filePath);
        Directory fileParentDirectory;

        // there is no path before the file,
        // then make new file in the current directory
        // if there is a path before the file, then find that path
        if (fileParentPath.equals("")) {
          fileParentDirectory = fs.getCurrentDirectory();
        } else {
          fileParentDirectory = fs.getDirectory(fileParentPath);
        }

        // create the new file inside the given path
        File newFile = new File(fileName, fileParentDirectory, content);

      } catch (InvalidNameException ine) {
        // if invalid characters in name or name already exists
        jshell.output("Invalid Characters, or name already exists" + "\n");
      } catch (NoSuchDirectoryException nsde) {
        // if the path given is not valid
        jshell.output("There are missing directories in " + filePath
            + " Please enter a valid path" + "\n");
      }
    }
  }


  /**
   * Given the string user entered, return the command and redirection part
   * separated
   *
   * @param userInput The string input of user
   * @param commandName The name of command user is running
   * @return The array of strings where the first element is the command part
   *         and second is the redirection part
   */
  public String[] isolateRedirectionArguments(String userInput,
      String commandName) {
    // Result
    String[] result = {"", ""};

    // If redirecting
    if (isRedirecting(userInput)) {
      // Command part is everything up to last two words of user input
      String[] userInputArr = userInput.split(" ");

      String[] commandArr =
          Arrays.copyOfRange(userInputArr, 0, userInputArr.length - 2);

      // Command part is all the words in the array we got above in a single
      // string
      for (String word : commandArr) {
        result[0] += word + " ";
      }
      // Remove the final space at end
      result[0] = result[0].trim();

      // Redirection part is the last two words
      // Get the two last words from array
      String[] redirectionArr = Arrays.copyOfRange(userInputArr,
          userInputArr.length - 2, userInputArr.length);
      // Concat to one string
      result[1] = redirectionArr[0] + " " + redirectionArr[1];
    } else {
      // Else not redirecting
      // Command part is just user input and no redirection part
      result[0] = userInput;
    }

    return result;
  }


  /**
   * Checks if the user's input is trying to redirect
   *
   * @param userInput String of user's input
   * @return True if user is trying to redirect, else false
   */
  private boolean isRedirecting(String userInput) {
    // Result
    boolean result = false;

    // Split user input into array where each element is a word in the string
    String[] userInputArr = userInput.split(" ");

    // Redirecting if one of the elements is string ">>" or ">"
    if (Arrays.asList(userInputArr).contains(">")
        || Arrays.asList(userInputArr).contains(">>")) {
      result = true;
    }
    // Return result
    return result;
  }


  /**
   * Given the string of parameters for redirection, split it into two strings.
   * The first string being the redirection option and the second the oufile
   *
   * @param redirectionArgumentsString String for redirection
   * @return String array of size 2. Where string at index 0 is the redirection
   *         option and string at index 1 is the outfile
   */
  public String[] redirectionPartToArray(String redirectionArgumentsString) {

    // Split this string into an array of the words in it
    String[] redirectionArgumentsArr = redirectionArgumentsString.split(" ");

    // Get redirection part of user input string
    String redirectionOption = redirectionArgumentsArr[0];
    String redirectionOutfile = redirectionArgumentsArr[1];

    // Result is redirection option then redirection outfile
    String[] result = {redirectionOption, redirectionOutfile};

    // Return the result
    return result;

  }

  /**
   * Given the string of arguments for redirection, check the syntax of it
   *
   * @param redirectionArgumentsString String for redirection
   * @return True iff syntax of redirectionArgumentsString is correct
   */
  public boolean isSyntaxCorrect(String redirectionArgumentsString) {
    // Result
    boolean result = false;

    // Split this string into an array of the words in it
    String[] redirectionArgumentsArr = redirectionArgumentsString.split(" ");

    // Correct syntax iff
    // Length of array is 2 (only redirect option and file given)
    // And redirect option is either > or >>
    if (redirectionArgumentsArr.length == 2
        && (redirectionArgumentsArr[0].equals(">")
            || redirectionArgumentsArr[0].equals(">>"))) {
      result = true;
    }

    // Return result
    return result;
  }


  /**
   * given the abs/relative path(including name) to a file, return only path
   * leading to file
   *
   * @param path is the string that represents the path of the file
   * @return returns everything before the file name,
   */
  private String findFilePath(String path) {
    String newPath;
    int fileIndex = path.lastIndexOf("/");

    // if there is no "/'
    if (fileIndex == -1) {
      newPath = "";
      // else ret everything before the file name
    } else {
      newPath = path.substring(0, fileIndex);
    }
    return newPath;
  }


  /**
   * given the abs/relative path to a file, return the file name
   *
   * @param path is the string that represents the path of the file
   * @return name of the file that the path represents
   */
  private String findFileName(String path) {
    String fileName;
    int fileIndex = path.lastIndexOf("/");

    // if FILE does not include abs/relative path
    if (fileIndex == -1) {
      fileName = path;
    } else {
      // retrieve the substring of path that is the file name
      fileName = path.substring(fileIndex + 1);
    }
    return fileName;
  }
}
