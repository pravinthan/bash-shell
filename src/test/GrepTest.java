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
import driver.Grep;
import driver.InvalidNameException;
import driver.JShell;
import driver.JShellFileSystem;
import driver.Directory;
import driver.File;

/**
 * @author Pravinthan Prabagaran
 *
 */
public class GrepTest {

  private Grep grep;
  private FileSystem fs;
  private JShell js;
  private String expected;
  private String actual;

  @Before
  public void setUp() throws Exception {
    fs = new JShellFileSystem();
    grep = new Grep();
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
    grep = new Grep();
    js = new JShell(fs);
    expected = null;
    actual = null;
  }

  @Test
  public void testArgsInValidOneArg() {
    assertFalse(grep.areArgumentsValid("grep \"s\""));
  }

  @Test
  public void testArgsInValidMultipleArgs() {
    assertFalse(grep.areArgumentsValid("grep . \"s\" e"));
  }

  @Test
  public void testArgsValidNonRecursive() {
    assertTrue(grep.areArgumentsValid("grep \"s\" e"));
  }

  @Test
  public void testArgsValidRecursive() {
    assertTrue(grep.areArgumentsValid("grep -R \"s\" ."));
  }

  @Test
  public void testOneFileNoMatch() throws InvalidNameException {
    new File("e", fs.getCurrentDirectory(), "ee");
    expected = "e:";
    actual = grep.run("grep \"a\" e", js);
    assertEquals(expected, actual);
  }

  @Test
  public void testOneFileMatch() throws InvalidNameException {
    new File("e", fs.getCurrentDirectory(), "ee gg");
    expected = "e:\nee gg";
    actual = grep.run("grep \"ee\" e", js);
    assertEquals(expected, actual);
  }

  @Test
  public void testMultipleFilesNoMatch() throws InvalidNameException {
    new File("e", fs.getCurrentDirectory(), "ee gg");
    new File("ew", fs.getCurrentDirectory(), "eee hgg");
    expected = "e:\n\new:";
    actual = grep.run("grep \"ff\" e ew", js);
    assertEquals(expected, actual);
  }

  @Test
  public void testMultipleFilesMatch() throws InvalidNameException {
    new File("e", fs.getCurrentDirectory(), "ee gg");
    new File("ew", fs.getCurrentDirectory(), "eee hgg");
    expected = "e:\nee gg\n\new:\neee hgg";
    actual = grep.run("grep \"ee\" e ew", js);
    assertEquals(expected, actual);
  }

  @Test
  public void testOneFileNoMatchRecursive() throws InvalidNameException {
    new File("e", fs.getCurrentDirectory(), "ee");
    expected = "e:";
    actual = grep.run("grep -R \"a\" e", js);
    assertEquals(expected, actual);
  }

  @Test
  public void testOneFileMatchRecursive() throws InvalidNameException {
    new File("e", fs.getCurrentDirectory(), "ee gg");
    expected = "e:\nee gg";
    actual = grep.run("grep -R \"ee\" e", js);
    assertEquals(expected, actual);
  }

  @Test
  public void testMultipleFilesNoMatchRecursive() throws InvalidNameException {
    new File("e", fs.getCurrentDirectory(), "ee gg");
    new File("ew", fs.getCurrentDirectory(), "eee hgg");
    expected = "e:\n\new:";
    actual = grep.run("grep -R \"ff\" e ew", js);
    assertEquals(expected, actual);
  }

  @Test
  public void testMultipleFilesMatchRecursive() throws InvalidNameException {
    new File("e", fs.getCurrentDirectory(), "ee gg");
    new File("ew", fs.getCurrentDirectory(), "eee hgg");
    expected = "e:\nee gg\n\new:\neee hgg";
    actual = grep.run("grep -R \"ee\" e ew", js);
    assertEquals(expected, actual);
  }

  @Test
  public void testMultipleFilesInDirectoriesMatchRecursive()
      throws InvalidNameException {
    Directory d =
        Directory.createDirectoryWithParent("a", fs.getCurrentDirectory());
    new File("e", d, "ee gg");
    new File("ew", fs.getCurrentDirectory(), "eee hgg");
    expected = "./ew:\neee hgg\n\n./a/e:\nee gg";
    actual = grep.run("grep -R \"ee\" .", js);
    assertEquals(expected, actual);
  }

}
