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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This class represents the curl command
 *
 * @author Thomas Lo, Gi Tae Lim
 */
public class Curl extends Command {


  /**
   * Return the string representation of this command
   *
   * @return String representation of what this command does
   */
  @Override
  public String toString() {
    String str =
        "This command retrieves a file in the given url and adds the file "
            + "into the current wroking directory";

    return str;
  }


  /**
   * Returns the documentation of this command
   *
   * @return documentation as string
   */
  @Override
  public String getDocumentation() {
    return "Usage: curl URL [>/>> OUTFILE] \nGets the file at URL and adds it "
        + "to the current directory \n[>/>> OUTFILE] represents an argument "
        + "to redirect the output to OUTFILE";
  }


  /**
   * Check whether the given user input is a valid command
   *
   * @param userInput The string input user entered into console
   * @return boolean True if userInput is valid, else false
   */
  @Override
  public boolean areArgumentsValid(String userInput) {
    // Result of syntax check
    boolean result = true;

    // String user input parsed into array of string
    String[] userInputArray = userInputStringToArray(userInput);

    // Invalid if length is not equal to 2
    if (userInputArray.length != 2) {
      result = false;
    }

    // Return result
    return result;
  }


  /**
   * Runs the curl command to get the contents from given url's file and create
   * a new file with the contents in the current directory
   *
   * @param userInput The string input user entered into console
   * @return null
   */
  @Override
  public String run(String userInput, JShell jshell) {
    String result = null;

    URL contentAddress = null;
    String output = "";
    String urlString = "";
    String inputLine, fileName, commandInput;
    int slashIndex = 0;
    BufferedReader in = null;

    urlString = userInput.substring(5);
    try {
      // URL includes everything but the command name and space
      contentAddress = new URL(urlString);
      in = new BufferedReader(
          new InputStreamReader(contentAddress.openStream()));
      // Reading content from url
      while ((inputLine = in.readLine()) != null) {
        // Concatenate
        output += inputLine + "\n";
      }
      in.close();
    } catch (IOException e) {
      result = e.getMessage() + "\n";
    }

    // Gets the file name from url
    for (int i = 0; i < urlString.length(); i++) {
      if (urlString.charAt(i) == '/') {
        slashIndex = i;
      }
    }

    // Gets the file name
    fileName = urlString.substring(slashIndex + 1);
    int dotIndex = fileName.lastIndexOf('.');
    fileName = fileName.substring(0,dotIndex);
    // Creates the file
    commandInput = "echo " + "\"" + output + "\" > " + fileName;
    jshell.runCommand(commandInput);

    return result;
  }

}
