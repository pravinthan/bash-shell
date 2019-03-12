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
import driver.JShell;
import driver.JShellFileSystem;
import driver.Ls;
import driver.Mkdir;

public class MkdirTest {

  Mkdir mkdir;
  JShell jshell;


  @Before
  public void setUp() {
    mkdir = new Mkdir();
    JShellFileSystem fs = new JShellFileSystem();
    jshell = new JShell(fs);
  }


  /**
   * Tests the syntax check when valid input and not recursive relative
   */
  @Test
  public void testSyntaxCheckValidRelative() {
    String input = "mkdir a";


    boolean expected = true;
    boolean actual = mkdir.areArgumentsValid(input);

    assertEquals(expected, actual);
  }

  /**
   * Tests the syntax check when valid input and not recursive avsolute
   */
  @Test
  public void testSyntaxCheckValidAbsolute() {
    String input = "mkdir /a";


    boolean expected = true;
    boolean actual = mkdir.areArgumentsValid(input);

    assertEquals(expected, actual);
  }


  /**
   * Tests the syntax check when valid input and recursive relative
   */
  @Test
  public void testSyntaxCheckValidRecursiveRelative() {
    String input = "mkdir a b c";


    boolean expected = true;
    boolean actual = mkdir.areArgumentsValid(input);

    assertEquals(expected, actual);
  }


  /**
   * Tests the syntax check when valid input and recursive relative
   */
  @Test
  public void testSyntaxCheckValidRecursiveAbsolute() {
    String input = "mkdir /a /b /c";


    boolean expected = true;
    boolean actual = mkdir.areArgumentsValid(input);

    assertEquals(expected, actual);
  }


  /**
   * Tests the syntax check when no dir given to make
   */
  @Test
  public void testSyntaxCheckNoDirGiven() {
    String input = "mkdir";


    boolean expected = false;
    boolean actual = mkdir.areArgumentsValid(input);

    assertEquals(expected, actual);
  }

  /**
   * Tests the syntax check when no command name given
   */
  @Test
  public void testSyntaxCheckNoCommandName() {
    String input = "a";


    boolean expected = false;
    boolean actual = mkdir.areArgumentsValid(input);

    assertEquals(expected, actual);
  }



  /**
   * Test the run when mkaing dir in curr dir and using relative
   */
  @Test
  public void testRunMkdirInRootRelative() {
    String input = "mkdir a";

    mkdir.run(input, jshell);

    Ls ls = new Ls();
    String dirs = ls.run("Ls", jshell);

    assertTrue(dirs.contains("a"));
  }


  /**
   * Test the run when mkaing dir in curr dir and using absolute
   */
  @Test
  public void testRunMkdirInRootAbsolute() {
    String input = "mkdir /b";

    mkdir.run(input, jshell);

    Ls ls = new Ls();
    String dirs = ls.run("Ls", jshell);


    assertTrue(dirs.contains("b"));
  }


  /**
   * Test the run when making dir in diff dir than curr dir using relative
   */
  @Test
  public void testRunMkdirNotInRootRelative() {
    Cd cd = new Cd();

    cd.run("cd a", jshell);
    String input = "mkdir c";

    mkdir.run(input, jshell);

    Ls ls = new Ls();
    String dirs = ls.run("Ls", jshell);

    assertTrue(dirs.contains("c"));
  }


  /**
   * Test the run when making dir in diff dir than curr dir using absolute
   */
  @Test
  public void testRunMkdirNotInRootAbsolute() {
    Cd cd = new Cd();

    String input = "mkdir /a/d";

    mkdir.run(input, jshell);
    cd.run("cd a", jshell);

    Ls ls = new Ls();
    String dirs = ls.run("Ls", jshell);

    assertTrue(dirs.contains("d"));
  }



  /**
   * Tests run when making dir with the same name as an existing dir in the curr
   * dir
   */
  @Test
  public void testRunDuplicateName() {
    String input = "mkdir a";

    mkdir.run(input, jshell);

    Ls ls = new Ls();
    String dirs = ls.run("Ls", jshell);

    assertTrue(dirs.indexOf("a") == dirs.lastIndexOf("a"));
  }

  /**
   * Tests run when making dir with invalid name
   */
  @Test
  public void testRunInvalidName() {
    String input = "mkdir **";

    mkdir.run(input, jshell);

    Ls ls = new Ls();
    String dirs = ls.run("Ls", jshell);

    assertFalse(dirs.contains("d"));
  }
}
