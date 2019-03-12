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

import driver.NoSavedWorkingDirectoryException;
import driver.NoSuchDirectoryException;
import driver.NoSuchFileException;

public class MockJShellFileSystem implements MockFileSystem {

  @Override
  public MockDirectory getDirectory(String path)
      throws NoSuchDirectoryException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MockFile getFile(String path) throws NoSuchFileException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MockDirectory getCurrentDirectory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setCurrentDirectory(MockDirectory directory) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addDirectory(MockDirectory directory) {
    // TODO Auto-generated method stub

  }

  @Override
  public void saveCurrDir() {
    // TODO Auto-generated method stub

  }

  @Override
  public MockDirectory getPrevCurrDir()
      throws NoSavedWorkingDirectoryException {
    // TODO Auto-generated method stub
    return null;
  }

}
