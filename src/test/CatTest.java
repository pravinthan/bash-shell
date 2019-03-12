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

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import driver.Cat;
import driver.File;
import driver.InvalidNameException;
import driver.JShell;
import driver.JShellFileSystem;
import driver.NoSuchDirectoryException;

public class CatTest {
  private Cat cat;
  private File f1, f2, f3;
  private JShell js;
  private JShellFileSystem fs;

  @Before
  public void setup() throws InvalidNameException, NoSuchDirectoryException {
    cat = new Cat();
    fs = new JShellFileSystem();
    js = new JShell(fs);
    f1 = new File("f1", JShellFileSystem.getRoot(), "abc");
    f2 = new File("f2", JShellFileSystem.getRoot(), "123");
    f3 = new File("f3", JShellFileSystem.getRoot(), "abc123,./");
  }

  @After
  public void tearDown() throws NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException {
    Field f = (fs.getClass()).getDeclaredField("ROOT");
    f.setAccessible(true);
    f.set(null, null);
  }

  /**
   * Tests the syntax check method when input is valid and given one existing
   * file
   */
  @Test
  public void testSyntaxCheckOneExistingFile() {
    String input = "cat f1";

    boolean expected = true;
    boolean actual = cat.areArgumentsValid(input);

    assertEquals(expected, actual);
  }


  /**
   * Tests the syntax check method when input is valid and given multiple
   * existing files
   */
  @Test
  public void testSyntaxCheckMultipleExistingFile() {
    String input = "cat f1 f2 f3";

    boolean expected = true;
    boolean actual = cat.areArgumentsValid(input);

    assertEquals(expected, actual);
  }

  /**
   * Tests the syntax check method when input has no files given
   */
  @Test
  public void testSyntaxCheckNoFileGiven() {
    String input = "cat";

    boolean expected = false;
    boolean actual = cat.areArgumentsValid(input);

    assertEquals(expected, actual);
  }



  /**
   * Tests the syntax check method when input gives a non-existing file name
   */
  @Test
  public void testSyntaxCheckNonExistingFile() {
    String input = "cat f4";

    boolean expected = true;
    boolean actual = cat.areArgumentsValid(input);

    assertEquals(expected, actual);
  }



  /**
   * Tests the run method when given input has one existing file
   */
  @Test
  public void testRunOneExistingFile() {
    String input = "cat f1";

    String expected = "abc";
    String actual = cat.run(input, js);

    assertEquals(expected, actual);
  }



  /**
   * Tests the run method when given input has multiple existing file
   */
  @Test
  public void testRunMultipleExistingFile() {
    String input = "cat f1 f2 f3";

    String expected = "abc\n\n\n\n123\n\n\n\nabc123,./";
    String actual = cat.run(input, js);

    assertEquals(expected, actual);
  }



  /**
   * Tests the run method when given input has a non existing file
   */
  @Test
  public void testRunOneNonExistingFile() {
    String input = "cat f4";

    String expected = "No such file in this directory";
    String actual = cat.run(input, js);

    assertEquals(expected, actual);
  }


  /**
   * Tests the run method when given input has multiple non existing file
   */
  @Test
  public void testRunMultipleNonExistingFile() {
    String input = "cat f4 f5 f6";

    String expected = "No such file in this directory\n\n\n\nNo such file in"
        + " this directory\n\n\n\nNo such file in this directory";
    String actual = cat.run(input, js);

    assertEquals(expected, actual);
  }


  /**
   * Tests the run method when given input has some existing and non existing
   * file
   */
  @Test
  public void testRunExistingAndNonExistingFile() {
    String input = "cat f1 f4 f2";

    String expected = "abc\n\n\n\nNo such file in this directory\n\n\n\n123";
    String actual = cat.run(input, js);

    assertEquals(expected, actual);
  }
}
