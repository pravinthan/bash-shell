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
import org.junit.Before;
import org.junit.Test;
import driver.Cat;
import driver.JShell;
import driver.JShellFileSystem;
import driver.Man;


public class ManTest {
  private Man man;
  private Cat cat; // arbitrary command to test on
  private JShell js;
  private JShellFileSystem fs;

  @Before
  public void setup() {
    man = new Man();
    cat = new Cat();
    fs = new JShellFileSystem();
    js = new JShell(fs);
  }

  /**
   * Tests the syntax check method when input is valid and given one existing
   * command
   */
  @Test
  public void testSyntaxCheckOneExistingCommand() {
    String input = "man cat";

    boolean expected = true;
    boolean actual = man.areArgumentsValid(input);

    assertEquals(expected, actual);
  }

  /**
   * Tests the syntax check method when input is valid and given one
   * non-existing command
   */
  @Test
  public void testSyntaxCheckOneNonExistingCommand() {
    String input = "man dne";

    boolean expected = true;
    boolean actual = man.areArgumentsValid(input);

    assertEquals(expected, actual);
  }

  /**
   * Tests the syntax check method when input is valid and given multiple
   * existing command
   */
  @Test
  public void testSyntaxCheckMultipleExistingCommand() {
    String input = "man cat man";

    boolean expected = false;
    boolean actual = man.areArgumentsValid(input);

    assertEquals(expected, actual);
  }


  /**
   * Tests the run method when given input has some existing and non existing
   * file
   */
  @Test
  public void testRunExistingCommand() {
    String input = "man cat";

    String expected = cat.getDocumentation() + "\n";
    String actual = man.run(input, js);

    assertEquals(expected, actual);
  }
}
