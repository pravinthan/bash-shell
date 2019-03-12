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
import driver.PopD;
import driver.PushD;

public class PopDTest {

  PopD popd;
  JShell jshell;


  @Before
  public void setUp() {
    popd = new PopD();
    JShellFileSystem fs = new JShellFileSystem();
    jshell = new JShell(fs);
  }


  /**
   * Test syntax check for valid case
   */
  @Test
  public void testSyntaxCheckValid() {
    boolean expected = true;
    boolean actual = popd.areArgumentsValid("popd");


    assertEquals(expected, actual);
  }

  /**
   * Test syntax check when input has params
   */
  @Test
  public void testSyntaxCheckParamsGive() {
    boolean expected = false;
    boolean actual = popd.areArgumentsValid("popd aas");


    assertEquals(expected, actual);
  }

  /**
   * Test syntax check when input has no command name
   */
  @Test
  public void testSyntaxCheckNoCommandName() {
    boolean expected = false;
    boolean actual = popd.areArgumentsValid("");

    assertEquals(expected, actual);
  }

  /**
   * Tests run when previously pushd'd
   */
  @Test
  public void testRunWhenDirSaved() {
    PushD pushd = new PushD();
    Mkdir mkdir = new Mkdir();

    mkdir.run("mkdir a", jshell);
    pushd.run("pushd a", jshell);

    popd.run("popd", jshell);

    String expected = "/";
    String actual = jshell.getFileSystem().getCurrentDirectory().getPath();

    assertEquals(expected, actual);
  }

  /**
   * Tests run when previously did not pushd'd
   */
  @Test
  public void testRunWhenDirNotSaved() {
    Mkdir mkdir = new Mkdir();

    mkdir.run("mkdir a", jshell);

    popd.run("popd", jshell);

    String expected = "/";
    String actual = jshell.getFileSystem().getCurrentDirectory().getPath();

    assertEquals(expected, actual);
  }
}
