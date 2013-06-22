package com.jfik.logo.interpreter;

public class LogoNumber
{
  private int number;
  
  public LogoNumber(Token directionToken, String numberAsString)
  {
    number = Integer.parseInt(numberAsString);
    if (directionToken == Token.BACK || directionToken == Token.RIGHT)
    {
      number = (-1)*number;
    }
  }
  
  public int getNumber() {
	  return number;
  }
}
