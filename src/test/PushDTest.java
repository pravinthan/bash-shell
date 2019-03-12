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
import driver.JShell;
import driver.JShellFileSystem;
import driver.Mkdir;
import driver.PushD;

public class PushDTest {
  PushD pushd;
  JShell jshell;

  @Before
  public void setUp() {
    pushd = new PushD();
    JShellFileSystem fs = new JShellFileSystem();
    jshell = new JShell(fs);
  }

  /**
   * Tests syntax check valid case
   */
  @Test
  public void testSyntaxValid() {
    boolean expected = true;
    boolean actual = pushd.areArgumentsValid("pushd a");

    assertEquals(expected, actual);
  }

  /**
   * Tests syntax check when input has too many directories
   */
  @Test
  public void testSyntaxTooManyDirs() {
    boolean expected = false;
    boolean actual = pushd.areArgumentsValid("pushd a b c");

    assertEquals(expected, actual);
  }


  /**
   * Tests syntax check when input has too little dirs
   */
  @Test
  public void testSytnaxNoEnoughDir() {
    boolean expected = false;
    boolean actual = pushd.areArgumentsValid("pushd");

    assertEquals(expected, actual);
  }

  /**
   * Tests syntax check whe input has not commandname
   */
  @Test
  public void testSyntaxNoCommandName() {
    boolean expected = false;
    boolean actual = pushd.areArgumentsValid("a b c");

    assertEquals(expected, actual);
  }


  /**
   * Test run when given dir in relative
   */
  @Test
  public void testRunUsingRelative() {
    Mkdir mkdir = new Mkdir();

    mkdir.run("mkdir a", jshell);

    pushd.run("pushd a", jshell);

    String expected = "/a/";
    String actual = jshell.getFileSystem().getCurrentDirectory().getPath();

    assertEquals(expected, actual);
  }

  /**
   * Test run when given dir in absolute
   */
  @Test
  public void testRunUsingAbsolute() {
    Mkdir mkdir = new Mkdir();

    mkdir.run("mkdir b", jshell);

    pushd.run("pushd /b", jshell);

    String expected = "/b/";
    String actual = jshell.getFileSystem().getCurrentDirectory().getPath();

    assertEquals(expected, actual);
  }

  /**
   * Tests run when given a dir that dne
   */
  @Test
  public void testRunWhenDirDNE() {
    pushd.run("pushd /d", jshell);

    String expected = "/";
    String actual = jshell.getFileSystem().getCurrentDirectory().getPath();

    assertEquals(expected, actual);
  }

}
