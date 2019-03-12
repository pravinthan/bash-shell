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
import driver.FileSystem;
import driver.InvalidNameException;
import driver.JShell;
import driver.JShellFileSystem;
import driver.Ls;
import driver.Mkdir;
import driver.Mv;
import driver.File;
import driver.Cat;

/**
 * @author Pravinthan Prabagaran
 *
 */
public class MvTest {

  private Mv mv;
  private FileSystem fs;
  private JShell js;
  private String actual;

  @Before
  public void setUp() throws Exception {
    fs = new JShellFileSystem();
    mv = new Mv();
    js = new JShell(fs);
    actual = null;
  }

  @After
  public void tearDown() throws Exception {
    Field f = (fs.getClass()).getDeclaredField("ROOT");
    f.setAccessible(true);
    f.set(null, null);
    fs = new JShellFileSystem();
    mv = new Mv();
    js = new JShell(fs);
    actual = null;
  }

  @Test
  public void testArgsValid() {
    assertTrue(mv.areArgumentsValid("mv e ee"));
  }

  @Test
  public void testArgsInvalid() {
    assertFalse(mv.areArgumentsValid("mv e e e ee"));
  }

  /**
   * Replacing one file with another of the same contents
   * 
   * @throws InvalidNameException
   */
  @Test
  public void testFileToExistantFile() throws InvalidNameException {
    new File("a", fs.getCurrentDirectory(), "qwe");
    new File("b", fs.getCurrentDirectory(), "rty");
    mv.run("mv a b", js);
    assertEquals("b", new Ls().run("ls", js));
    assertEquals("qwe", new Cat().run("cat b", js));
  }


  /**
   * Changing name of a file
   * 
   * @throws InvalidNameException
   */
  @Test
  public void testFileToNonExistantFile() throws InvalidNameException {
    new File("a", fs.getCurrentDirectory(), "qwe");
    mv.run("mv a b", js);
    actual = new Cat().run("cat b", js);
    assertEquals("qwe", actual);
  }


  /**
   * Moving a file to a given directory
   * 
   * @throws InvalidNameException
   */
  @Test
  public void testFileToExistantDir() throws InvalidNameException {
    new File("a", fs.getCurrentDirectory(), "qwe");
    new Mkdir().run("mkdir b", js);
    mv.run("mv a b", js);
    assertEquals("b", new Ls().run("ls", js));
    assertEquals("qwe", new Cat().run("cat b/a", js));
  }


  /**
   * Moving one directory to another
   * 
   * @throws InvalidNameException
   */
  @Test
  public void testDirToExistantDir() throws InvalidNameException {
    new Mkdir().run("mkdir a a/b c", js);
    mv.run("mv a c", js);
    assertEquals("c", new Ls().run("ls", js));
    assertEquals("c/:\na", new Ls().run("ls c", js));
  }

}
