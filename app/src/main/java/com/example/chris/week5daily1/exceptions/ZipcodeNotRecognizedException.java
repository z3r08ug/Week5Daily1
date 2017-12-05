package com.example.chris.week5daily1.exceptions;

public class ZipcodeNotRecognizedException extends Exception
{
	public ZipcodeNotRecognizedException() 
	{
        super("Zipcode must be 5 digits.");
    }
}
