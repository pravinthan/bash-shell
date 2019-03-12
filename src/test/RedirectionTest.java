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
import driver.Redirection;

public class RedirectionTest {

  Redirection redi;

  @Before
  public void setUp() {
    redi = new Redirection();
  }

  /**
   * Tests the isolate method when user input has no redirection part
   */
  @Test
  public void testIsolateNoRedirectionPart() {
    String input = "cd a";

    // Expected
    String[] expected = {"cd a", ""};

    // Actual
    String[] actual = redi.isolateRedirectionArguments(input, "cd");

    assertArrayEquals(expected, actual);
  }


  /**
   * Tests the isolate method when user input has no command part
   */
  @Test
  public void testIsolateNoCommandPart() {
    String input = ">> file";

    // Expected
    String[] expected = {"", ">> file"};

    // Actual
    String[] actual = redi.isolateRedirectionArguments(input, "");

    assertArrayEquals(expected, actual);
  }


  /**
   * Tests the isolate method when user input has both command and redirection
   * part
   */
  @Test
  public void testIsolateGivenCommandAndRedirectionPart() {
    String input = "cd a >> file";

    // Expected
    String[] expected = {"cd a", ">> file"};

    // Actual
    String[] actual = redi.isolateRedirectionArguments(input, "cd");

    assertArrayEquals(expected, actual);
  }


  /**
   * Tests the the splitting of redirection to arr method
   */
  @Test
  public void testRedirectionPartToArr() {
    String input = ">> file";

    // Expected
    String[] expected = {">>", "file"};

    // Actual is result
    String[] actual = redi.redirectionPartToArray(input);

    assertArrayEquals(expected, actual);
  }


  /**
   * Test the syntax check when no redirection type given
   */
  @Test
  public void testSyntaxCheckNoType() {
    String input = "file file2";

    // Expected is false
    boolean expected = false;

    // Actual is the reuslt of method
    boolean actual = redi.isSyntaxCorrect(input);

    assertEquals(expected, actual);


  }


  /**
   * Test the syntax check when too many redirection type given
   */
  @Test
  public void testSyntaxCheckTooManyType() {
    String input = ">> > file file2";

    // Expected is false
    boolean expected = false;

    // Actual is the reuslt of method
    boolean actual = redi.isSyntaxCorrect(input);

    assertEquals(expected, actual);

  }


  /**
   * Test the syntax check when redirection type given is not > or >>
   */
  @Test
  public void testSyntaxCheckInvalidType() {
    String input = ">>> file file2";

    // Expected is false
    boolean expected = false;

    // Actual is the reuslt of method
    boolean actual = redi.isSyntaxCorrect(input);

    assertEquals(expected, actual);

  }


  /**
   * Test the syntax check when no outfile given
   */
  @Test
  public void testSyntaxCheckNoOutFile() {
    String input = ">>";

    // Expected is false
    boolean expected = false;

    // Actual is the reuslt of method
    boolean actual = redi.isSyntaxCorrect(input);

    assertEquals(expected, actual);

  }


  /**
   * Test the syntax check when too many outfile given
   */
  @Test
  public void testSyntaxCheckTooManyOutFile() {
    String input = ">> file file2";

    // Expected is false
    boolean expected = false;

    // Actual is the reuslt of method
    boolean actual = redi.isSyntaxCorrect(input);

    assertEquals(expected, actual);

  }


  /**
   * Test the syntax check for the single valid case
   */
  @Test
  public void testSyntaxCheckValid() {
    String input = ">> file";

    // Expected is true
    boolean expected = true;

    // Actual is the reuslt of method
    boolean actual = redi.isSyntaxCorrect(input);

    assertEquals(expected, actual);
  }


}
