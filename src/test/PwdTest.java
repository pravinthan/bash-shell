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
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import driver.Cd;
import driver.FileSystem;
import driver.JShell;
import driver.JShellFileSystem;
import driver.Mkdir;
import driver.Pwd;

public class PwdTest {

  private Pwd pwd;
  private FileSystem fs;
  private JShell js;
  private String expected;
  private String actual;

  @Before
  public void setUp() throws Exception {
    fs = new JShellFileSystem();
    pwd = new Pwd();
    js = new JShell(fs);
    expected = null;
    actual = null;
  }

  @After
  public void tearDown() throws Exception {
    Field f = (fs.getClass()).getDeclaredField("ROOT");
    f.setAccessible(true);
    f.set(null, null);
    fs = new JShellFileSystem();
    pwd = new Pwd();
    js = new JShell(fs);
    expected = null;
    actual = null;
  }

  @Test
  public void testArgsValid() {
    assertTrue(pwd.areArgumentsValid("pwd"));
  }

  @Test
  public void testArgsInvalid() {
    assertFalse(pwd.areArgumentsValid("pwd asd asdasd ads "));
  }

  @Test
  public void testRoot() {
    expected = "/";
    actual = pwd.run("pwd", js);
    assertEquals(expected, actual);
  }

  @Test
  public void testArbitraryDirectory() {
    new Mkdir().run("mkdir a a/b", js);
    new Cd().run("cd a/b", js);
    expected = "/a/b/";
    actual = pwd.run("pwd", js);
    assertEquals(expected, actual);
  }

}
