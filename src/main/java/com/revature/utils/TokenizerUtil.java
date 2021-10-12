package com.revature.utils;

import java.util.ArrayList;

public class TokenizerUtil {

	public static ArrayList<String> getTokens(String string)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		String[] st = string.split("\\s");
		for(String token : st)
		{
			tokens.add(token.toLowerCase());
		}	
		return tokens;
	}
	
	//Use this function if I'd like to limit what words are considered to be tokenized, not using for now
	@SuppressWarnings("unused")
	private static boolean isValidToken(String token)
	{
		if(token.length() < 3) return false;
		return true;
	}
}
