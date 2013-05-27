package com.example.logointerpreter;

public class NumberRecord
{
  private int number;
  
  public NumberRecord(Token directionToken, String numberAsString)
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
