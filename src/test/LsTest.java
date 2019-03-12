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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;
import driver.Directory;
import driver.FileSystem;
import driver.InvalidNameException;
import driver.JShell;
import driver.JShellFileSystem;
import driver.Ls;
import driver.Mkdir;

/**
 * @author Pravinthan Prabagaran
 *
 */
public class LsTest {

  private Ls ls;
  private FileSystem fs;
  private JShell js;
  private String expected;
  private String actual;

  @Before
  public void setUp() throws Exception {
    fs = new JShellFileSystem();
    ls = new Ls();
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
    ls = new Ls();
    js = new JShell(fs);
    expected = null;
    actual = null;
  }

  @Test
  public void testArgsValid() {
    assertTrue(ls.areArgumentsValid("ls -R e ee ee e ee"));
  }

  /**
   * Running ls with no subdirectories nor files
   */
  @Test
  public void testNoSubDirsFiles() {
    actual = ls.run("ls", js);
    expected = "";
    assertEquals(expected, actual);
  }

  /**
   * Running ls with one directory in the file system
   */
  @Test
  public void testOneDir() {
    new Mkdir().run("mkdir a", js);
    actual = ls.run("ls a", js);
    expected = "a/:";
    assertEquals(expected, actual);
  }

  /**
   * Running ls with one file in the file system
   * 
   * @throws InvalidNameException
   */
  @Test
  public void testOneFile() throws InvalidNameException {
    Directory.createDirectoryWithParent("b",
        js.getFileSystem().getCurrentDirectory());
    actual = ls.run("ls", js);
    expected = "b";
    assertEquals(expected, actual);
  }

  /**
   * Running ls with multiple directories in the file system and no args for ls
   */
  @Test
  public void testMultipleDirNoArgs() {
    new Mkdir().run("mkdir a b c", js);
    actual = ls.run("ls", js);
    expected = "a\nb\nc";
    assertEquals(expected, actual);
  }

  /**
   * Running ls with multiple directories in the file system and ls has args
   */
  @Test
  public void testMultipleDir() {
    new Mkdir().run("mkdir a b c", js);
    actual = ls.run("ls a b c", js);
    expected = "a/:\n\nb/:\n\nc/:";
    assertEquals(expected, actual);
  }

  /**
   * Running ls one nested directory
   */
  @Test
  public void testOneDirNested() {
    new Mkdir().run("mkdir a a/b a/b/c", js);
    actual = ls.run("ls a a/b a/b/c", js);
    expected = "a/:\nb\n\na/b/:\nc\n\na/b/c/:";
    assertEquals(expected, actual);
  }

  /**
   * Running ls with multiple directories, some nested
   */
  @Test
  public void testMultipleDirNested() {
    new Mkdir().run("mkdir a a/b a/b/c d", js);
    actual = ls.run("ls a a/b a/b/c d", js);
    expected = "a/:\nb\n\na/b/:\nc\n\na/b/c/:\n\nd/:";
    assertEquals(expected, actual);
  }

  /**
   * Running ls recursively with one nested directory
   */
  @Test
  public void testOneDirNestedRecursiveNoArgs() {
    new Mkdir().run("mkdir a a/b a/b/c", js);
    actual = ls.run("ls -R", js);
    expected = "/:\na\n\n/a/:\nb\n\n/a/b/:\nc\n\n/a/b/c/:";
    assertEquals(expected, actual);
  }

  /**
   * Running ls recursively with multiple nested directories with no ls paths
   */
  @Test
  public void testMultipleDirNestedRecursiveNoArgs() {
    new Mkdir().run("mkdir a a/b a/b/c d d/e", js);
    actual = ls.run("ls -R", js);
    expected = "/:\na\nd\n\n/a/:\nb\n\n/a/b/:\nc\n\n/a/b/c/:\n\n/d/:\n"
        + "e\n\n/d/e/:";
    assertEquals(expected, actual);
  }

  /**
   * Running ls recursively with multiple nested directories with ls paths
   */
  @Test
  public void testMultipleDirNestedRecursive() {
    new Mkdir().run("mkdir a a/b a/b/c d d/e", js);
    actual = ls.run("ls -R a a/b a/b/c d d/e", js);
    expected = "a/:\nb\n\na/b/:\nc\n\na/b/c/:\n\na/b/:\nc\n\na/b/c/:\n\na/b/c/"
        + ":\n\nd/:\ne\n\nd/e/:\n\nd/e/:";
    assertEquals(expected, actual);
  }

}
