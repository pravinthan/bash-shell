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

package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import driver.Cd;
import driver.Directory;
import driver.FileSystem;
import driver.JShell;
import driver.JShellFileSystem;
import driver.Mkdir;
import driver.Pwd;

public class CdTest {
  Cd cd;
  JShell jshell;
  JShellFileSystem fs;

  @Before
  public void setUp() {
    cd = new Cd();
    fs = new JShellFileSystem();
    jshell = new JShell(fs);
  }


  /**
   * Tests the syntax check method when input is valid and using relative path
   */
  @Test
  public void testSyntaxCheckValidRelative() {
    String input = "cd a";

    boolean expected = true;
    boolean actual = cd.areArgumentsValid(input);

    assertEquals(expected, actual);
  }


  /**
   * Tests the syntax check method when input is valid and using absolue path
   */
  @Test
  public void testSyntaxCheckValidAbsolute() {
    String input = "cd /a";

    boolean expected = true;
    boolean actual = cd.areArgumentsValid(input);

    assertEquals(expected, actual);
  }

  /**
   * Tests the syntax check method when input has not dir given
   */
  @Test
  public void testSyntaxCheckNoDirGiven() {
    String input = "cd";

    boolean expected = false;
    boolean actual = cd.areArgumentsValid(input);

    assertEquals(expected, actual);
  }



  /**
   * Tests the syntax check method when input gives too many dirs in relative
   * path
   */
  @Test
  public void testSyntaxCheckTooManyDirGivenRelative() {
    String input = "cd a b c";

    boolean expected = false;
    boolean actual = cd.areArgumentsValid(input);

    assertEquals(expected, actual);
  }



  /**
   * Tests the syntax check method when input gives too many dirs in absolute
   * path
   */
  @Test
  public void testSyntaxCheckTooManyDirGivenAbsolute() {
    String input = "cd /a /b /c";

    boolean expected = false;
    boolean actual = cd.areArgumentsValid(input);

    assertEquals(expected, actual);
  }


  /**
   * Tests the run method when given the dir obj to cd to
   */
  @Test
  public void testRunGivenDirObj() {
    // Setup
    try {
      Directory dir =
          Directory.createDirectoryWithParent("a", FileSystem.getRoot());
      // Cd to this dir
      cd.run(dir, jshell);

      // Expected is the dir we made
      Directory expected = dir;
      // Actual is the curr dir of file system
      Directory actual = jshell.getFileSystem().getCurrentDirectory();

      assertEquals(expected, actual);
    } catch (Exception e) {
    }
  }



  /**
   * Tests the run method when given input as relative path
   */
  @Test
  public void testRunUsingRelative() {
    Mkdir mkdir = new Mkdir();
    mkdir.run("mkdir b", jshell);

    // Cd to that root
    cd.run("cd b", jshell);

    // Actual directory is the current directory of file system now
    Directory dir = jshell.getFileSystem().getCurrentDirectory();

    // Expected is pwd
    Pwd pwd = new Pwd();
    // Expected
    String expected = pwd.run(jshell);
    // Actual is fiel system's curr dir
    String actual = dir.getPath();

    // If the curr dir path gotten from pwd matches the curr dir of file
    // system's path than pass
    assertEquals(expected, actual);
  }



  /**
   * Tests the run method when given input as a absolute path
   */
  @Test
  public void testRunUsingAbsolute() {
    Mkdir mkdir = new Mkdir();
    mkdir.run("mkdir c", jshell);

    // Cd to that root
    cd.run("cd /c", jshell);

    // Actual directory is the current directory of file system now
    Directory dir = jshell.getFileSystem().getCurrentDirectory();

    // Expected is pwd
    Pwd pwd = new Pwd();
    // Expected
    String expected = pwd.run(jshell);
    // Actual is fiel system's curr dir
    String actual = dir.getPath();

    // If the curr dir path gotten from pwd matches the curr dir of file
    // system's path than pass
    assertEquals(expected, actual);
  }



  /**
   * Tests the run method when given input with dir that dne in curr dir
   * (relative path)
   */
  @Test
  public void testRunDirDNERelative() {
    // Cdto that root
    cd.run("cd d", jshell);

    // Expected curr dir is still root
    String expected = "/";

    // Actual is fil system's curr dir
    String actual = jshell.getFileSystem().getCurrentDirectory().getPath();

    assertEquals(expected, actual);
  }


  /**
   * Tests the run method when given input with dir that dne in curr dir
   * (absolute path)
   */
  @Test
  public void testRunDirDNEAbsolute() {
    // Cd to that root
    cd.run("cd /e", jshell);

    // Expected curr dir is still root
    String expected = "/";

    // Actual is fil system's curr dir
    String actual = jshell.getFileSystem().getCurrentDirectory().getPath();

    assertEquals(expected, actual);
  }


  /**
   * Tests the run method when cd to same dir
   */
  @Test
  public void testRunCdSameDir() {
    // Cd to that root
    cd.run("cd .", jshell);

    // Expected curr dir is still root
    String expected = "/";

    // Actual is fil system's curr dir
    String actual = jshell.getFileSystem().getCurrentDirectory().getPath();

    assertEquals(expected, actual);
  }


  /**
   * Tests run method when cd to parent dir and currently at root dir
   */
  @Test
  public void testRunCdToParentCurrNotRoot() {
    Mkdir mkdir = new Mkdir();
    mkdir.run("mkdir f", jshell);

    // Cd to diff dir
    cd.run("cd f", jshell);

    // Cd to that root
    cd.run("cd ..", jshell);

    // Expected curr dir is still root
    String expected = "/";

    // Actual is fil system's curr dir
    String actual = jshell.getFileSystem().getCurrentDirectory().getPath();

    assertEquals(expected, actual);
  }


  /**
   * Tests run method when cd to parent dir and not currently at root dir
   */
  @Test
  public void testRunCdToParentCurrRoot() {
    // Cd
    cd.run("cd ..", jshell);

    // Expected curr dir is still root
    String expected = "/";

    // Actual is fil system's curr dir
    String actual = jshell.getFileSystem().getCurrentDirectory().getPath();

    assertEquals(expected, actual);
  }

}
