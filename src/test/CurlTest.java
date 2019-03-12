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
import driver.Curl;
import driver.File;
import driver.InvalidNameException;
import driver.JShell;
import driver.JShellFileSystem;
import driver.NoSuchDirectoryException;


public class CurlTest {
  private Curl curl;
  private Cat cat;
  private JShell js;
  private JShellFileSystem fs;
  private File file;

  @Before
  public void setup() throws InvalidNameException, NoSuchDirectoryException {
    curl = new Curl();
    cat = new Cat();
    fs = new JShellFileSystem();
    js = new JShell(fs);
    String text =
        "===================================================================="
            + "======\n"
            + "CSC B36                Weekly Lecture Summaries               "
            + " Summer 2018\n"
            + "=============================================================="
            + "============\n" + "\n"
            + "Here's a very brief summary of what's covered each week during"
            + " lectures.\n"
            + "It is updated weekly in anticipation of the following week's "
            + "lectures.\n"
            + "Whenever possible you should come to class prepared.  This "
            + "means having\n"
            + "read all relevant parts of course and additional notes *before*"
            + " class.\n" + "\n" + "Week 1 (May 9,11):\n" + " * introduction\n"
            + " * additional notes on\n"
            + "   + common ways to write statements in English\n"
            + "   + simple and complete induction\n"
            + "   + structure of induction proofs\n"
            + "   + sample induction and well-ordering proofs\n"
            + "     > we won't do well-ordering proofs in this course\n"
            + "   (http://www.utsc.utoronto.ca/~nick/cscB36/additional-notes/)"
            + "\n" + "    - We didn't get to any examples.\n"
            + " * start of chapter 1\n" + "\n" + "Week 2 (May 16,18):\n"
            + " * end of chapter 1\n"
            + " * chapter 3 (omit section 3.2; enjoy it on your own)\n"
            + " * chapter 4\n" + " * start of chapter 2\n"
            + "   - We didn't get to this.\n" + " * additional notes on\n"
            + "   + pseudocode used for program correctness\n"
            + "   + proving program correctness\n"
            + "   - We didn't get to these.\n" + "\n" + "Week 3 (May 23,25):\n"
            + " * start of chapter 2\n" + " * additional notes on\n"
            + "   + pseudocode used for program correctness\n"
            + "   + proving program correctness\n"
            + "   + sample correctness proofs\n"
            + "     - We got through the iterative version of program Sq.\n"
            + "\n" + "Week 4 (May 30, June 1):\n" + " * more chapter 2\n"
            + " * more examples from additional on sample correctness proofs\n"
            + " * additional notes on an example where correctness is not obvi"
            + "ous\n" + "   - We didn't get to this one.\n" + "\n"
            + "Week 5 (June 6,8):\n" + " * wrap-up of program correctness\n"
            + " * start of chapter 7\n"
            + "   We defined regexes (page 192 of course notes).\n"
            + " * additional notes on structural induction for regular expre"
            + "ssions.\n" + "   - We didn't get to this.\n" + "\n"
            + "Week 6 (June 13,15):\n" + " * more chapter 7\n"
            + "   - We more or less finished to the end of section 7.3.\n"
            + " * additional notes on structural induction for regular expre"
            + "ssions.\n"
            + " * term test 1 on June 18 (see Bb announcement about it).\n"
            + "\n" + "Reading Week at UTSC (June 19-23): No classes.\n"
            + " Have a nice break!\n" + "\n" + "Week 7 (June 27,29):\n"
            + " * end of chapter 7\n" + "   + conventions for DFSAs\n"
            + "   + designing DFSAs\n"
            + " * additional notes on proving languages NOT regular using P"
            + "umping Lemma\n" + " * start of chapter 8\n"
            + "   - We didn't get to this.\n" + "\n" + "Week 8 (July 4,6):\n"
            + " * start of chapter 8\n"
            + "   - We defined CFGs and saw examples.\n"
            + "   - We saw examples of PDAs, but not defined them yet.\n\n"
            + "Week 9 (July 11,13):\n" + " * end of chapter 8\n"
            + " * start of chapter 5\n"
            + "   - We got to the end of section 5.2.2.\n" + "\n"
            + "Week 10 (July 18,20):\n" + " * end of chapter 5\n"
            + " * start of chapter 6\n"
            + "   -  We got to almost the end of section 6.2.\n" + "\n"
            + "Week 11 (July 25,27):\n"
            + " * more chapter 6 (omit section 6.7; enjoy that on your own)\n"
            + "\n";
    file = new File("lecture", JShellFileSystem.getRoot(), text);

  }

  @After
  public void tearDown() throws NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException {
    Field f = (fs.getClass()).getDeclaredField("ROOT");
    f.setAccessible(true);
    f.set(null, null);
  }

  /**
   * Tests the syntax check method when input is valid, given a url
   */
  @Test
  public void testSyntaxCheckValidArg() {
    String input = "curl www.helloworld.com/file.txt";

    boolean expected = true;
    boolean actual = curl.areArgumentsValid(input);

    assertEquals(expected, actual);
  }

  /**
   * Tests the syntax check method when input is not valid, given >1 url
   */
  @Test
  public void testSyntaxCheckNotValidArg() {
    String input = "curl abc def";

    boolean expected = false;
    boolean actual = curl.areArgumentsValid(input);

    assertEquals(expected, actual);
  }

  /**
   * Tests the run method when given input is within bounds of history
   */
  @Test
  public void testRunValidArg() {
    String input =
        "curl" + " https://www.utsc.utoronto.ca/~nick/cscB36/185/lecture.txt";

    String expected = file.getContents();
    curl.run(input, js);
    String actual = cat.run("cat lecture", js);

    assertEquals(expected, actual);
  }
}
