package oracle.adf.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.adf.utilities.ADFLogger;

public class Verify {

	public static ADFLogger logger = ADFLogger.getInstance();
	public static int success = 0;
	public static int fail = 0;

	public static void verifyEquals(String message, String expectedValue,
			String actualValue) {
		logger.debug("Expected Value : "+expectedValue);
		logger.debug("Actual Value : "+actualValue);
		Pattern pattern = Pattern.compile(expectedValue, Pattern.LITERAL);
		Matcher matcher = pattern.matcher(actualValue);
		boolean matchFound = matcher.matches();
		logger.verificationPoint(message+" result="+matchFound+" expected="+expectedValue+" actual="+actualValue, matchFound);
		if(matchFound)
			success++;
		else
			fail++;
	}

	public static void verifyEquals(String expectedValue, String actualValue) {
		logger.debug("Expected Value : "+expectedValue);
		logger.debug("Actual Value : "+actualValue);
		Pattern pattern = Pattern.compile(expectedValue);
		Matcher matcher = pattern.matcher(actualValue);
		boolean matchFound = matcher.matches();
		logger.verificationPoint("result="+matchFound+" expected="+expectedValue+" actual="+actualValue, matchFound);
		if(matchFound)
			success++;
		else
			fail++;
	}
	
	public static void verifyEquals(String message, Boolean result) {
		logger.verificationPoint(message+" result="+result, result);
		if(result)
			success++;
		else
			fail++;
	}

}
