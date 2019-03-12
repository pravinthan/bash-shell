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

import driver.Echo;
import driver.FileSystem;
import driver.JShell;
import driver.JShellFileSystem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EchoTest {

  private Echo echo;
  private FileSystem fs;
  private JShell js;

  @Before
  public void setup() {
    fs = new JShellFileSystem();
    js = new JShell(fs);
    echo = new Echo();
  }

  @org.junit.Test
  public void areArgumentsValidNoArgs() throws Exception {
    boolean actual = echo.areArgumentsValid("echo");
    boolean expected = false;
    assertEquals(expected, actual);
  }

  @org.junit.Test
  public void areArgumentsValid1Arg() throws Exception {
    boolean actual = echo.areArgumentsValid("echo \"A\" ");
    boolean expected = true;
    assertEquals(expected, actual);
  }

  @Test
  public void areArgumentsValidMultiArg() throws Exception {
    boolean actual = echo.areArgumentsValid("echo \"A\" \"A\"");
    boolean expected = false;
    assertEquals(expected, actual);
  }

  @Test
  public void areArgumentsValidStringWithSpace() throws Exception {
    boolean actual = echo.areArgumentsValid("echo \"Hello World\"");
    boolean expected = true;
    assertEquals(expected, actual);
  }

  @Test
  public void areArgumentsValidStringWoutQuotes() throws Exception {
    boolean actual = echo.areArgumentsValid("echo \"Hello\"");
    boolean expected = true;
    assertEquals(expected, actual);
  }

  @Test
  public void runStringWithoutSpace() {
    String actual = echo.run("echo \"Hello\"", js);
    String expected = "Hello";
    assertEquals(expected, actual);
  }

  @Test
  public void runStringWithSpace() {
    String actual = echo.run("echo \"Hello World\"", js);
    String expected = "Hello World";
    assertEquals(expected, actual);
  }

}
