package test;

import static org.junit.Assert.*;

import driver.Cp;
import driver.File;
import driver.Directory;
import driver.FileSystem;
import driver.InvalidNameException;
import driver.JShell;
import driver.JShellFileSystem;
import driver.Ls;
import driver.NoSuchDirectoryException;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CpTest {
  private Cp cp;
  private FileSystem fs;
  private JShell js;
  private File a, b, c;
  private Directory x, y, z;

  @Before
  public void setup() throws InvalidNameException, NoSuchDirectoryException {
    fs = new JShellFileSystem();
    js = new JShell(fs);
    cp = new Cp();

    x = Directory.createDirectoryWithParent("x", JShellFileSystem.getRoot());
    y = Directory.createDirectoryWithParent("y", x);

    a = new File("a", JShellFileSystem.getRoot(), "AA");
    b = new File("b", x, "BB");

  }

  @After
  public void tearDown() throws Exception {
    Field f = (fs.getClass()).getDeclaredField("ROOT");
    f.setAccessible(true);
    f.set(null, null);
    fs = new JShellFileSystem();
    js = new JShell(fs);
    cp = new Cp();
  }

  @Test
  public void areArgumentsValidNoPath() throws Exception {
    boolean actual = cp.areArgumentsValid("cp");
    boolean expected = false;
    assertEquals(expected, actual);
  }

  @Test
  public void areArgumentsValid1Path() throws Exception {
    boolean actual = cp.areArgumentsValid("cp some/path");
    boolean expected = false;
    assertEquals(expected, actual);
  }
  @Test
  public void areArgumentsValidCorrectPath() throws Exception {
    boolean actual = cp.areArgumentsValid("cp some/path other/path");
    boolean expected = true;
    assertEquals(expected, actual);
  }

  @Test
  public void areArgumentsValidManyPath() throws Exception {
    boolean actual = cp.areArgumentsValid("cp a b c");
    boolean expected = false;
    assertEquals(expected, actual);
  }

  @Test
  public void runSourceFileDNE() throws Exception {
    String actual = cp.run("cp i j", js);
    String expected = null;
    assertEquals(expected, actual);
  }

  @Test
  public void runDestinationDNE() throws Exception {
    String actual = cp.run("cp i j", js);
    String expected = null;
    assertEquals(expected, actual);
  }

  @Test
  public void runFile2File() throws Exception {
    String actual = cp.run("cp a x/b", js);
    String expected = "copied a to x/b";
    assertEquals(expected, actual);
    assertEquals(a.getContents(), b.getContents());

  }  @Test
  public void runFile2Dir() throws Exception {
    String actual = cp.run("cp x/b /", js);
    String expected = "copied x/b to /";
    assertEquals(expected, actual);
    assertEquals("a\nb\nx", new Ls().run("ls", js));
  }

  @Test
  public void runDir2Dir() throws Exception {
    String actual = cp.run("cp x/y /", js);
    String expected = "copied x/y to /";
    assertEquals(expected, actual);
    assertEquals("a\nx\ny", new Ls().run("ls", js));
  }

  @Test
  public void runDir2File() throws Exception {
    String actual = cp.run("cp x a", js);
    String expected = null;
    assertEquals(expected, actual);
  }

}
