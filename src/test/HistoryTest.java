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

import driver.History;
import driver.FileSystem;
import driver.JShell;
import driver.JShellFileSystem;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HistoryTest {

  History history;
  private FileSystem fs;
  private JShell js;

  @Before
  public void setup() {
    fs = new JShellFileSystem();
    js = new JShell(fs);
    history = new History();
  }

  @After
  public void tearDown() throws Exception {
    Field f = (fs.getClass()).getDeclaredField("ROOT");
    f.setAccessible(true);
    f.set(null, null);
    fs = new JShellFileSystem();
    js = new JShell(fs);
    history = new History();
  }


  @org.junit.Test
  public void areArgumentsValidNoArgs() throws Exception {
    boolean actual = history.areArgumentsValid("history");
    boolean expected = true;
    assertEquals(expected, actual);
  }

  @org.junit.Test
  public void areArgumentsValid1Num() throws Exception {
    boolean actual = history.areArgumentsValid("history 1");
    boolean expected = true;
    assertEquals(expected, actual);
  }

  @org.junit.Test
  public void areArgumentsValid2Num() throws Exception {
    boolean actual = history.areArgumentsValid("history 1 2");
    boolean expected = false;
    assertEquals(expected, actual);
  }

  @Test
  public void runAllHistory() throws Exception {
    history.store("history");
    String actual = history.run("history", js);
    String expected = "1. history";
    assertEquals(expected, actual);
  }

  @Test
  public void runAllHistoryMulti() throws Exception {
    history.store("history");
    history.store("kmn");
    String actual = history.run("history", js);
    String expected = "1. history\n2. kmn";
    assertEquals(expected, actual);
  }

  @Test
  public void runAllHistoryNumSmaller() throws Exception {
    history.store("history");
    history.store("kmn");
    String actual = history.run("history 1", js);
    String expected = "2. kmn";
    assertEquals(expected, actual);
  }

  @Test
  public void runAllHistoryNumBigger() throws Exception {
    history.store("history");
    history.store("kmn");
    String actual = history.run("history 10", js);
    String expected = "1. history\n2. kmn";
    assertEquals(expected, actual);
  }

}
