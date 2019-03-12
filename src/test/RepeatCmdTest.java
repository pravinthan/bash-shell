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
import driver.RepeatCmd;


public class RepeatCmdTest {
  private RepeatCmd rc;

  @Before
  public void setup() {
    rc = new RepeatCmd();
  }

  /**
   * Tests the syntax check method when input is valid, given a number
   */
  @Test
  public void testSyntaxCheckValidArg() {
    String input = "!1";

    boolean expected = true;
    boolean actual = rc.areArgumentsValid(input);

    assertEquals(expected, actual);
  }

  /**
   * Tests the syntax check method when input is not valid, given non-number
   */
  @Test
  public void testSyntaxCheckNotValidArg() {
    String input = "!abc";

    boolean expected = false;
    boolean actual = rc.areArgumentsValid(input);

    assertEquals(expected, actual);
  }

}
