package pepperfry;

import org.testng.annotations.DataProvider;


public class DataProviders {
  
  @DataProvider(name="loginTest")
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { "User's email or username", "User's password" }
    };
  }
}




