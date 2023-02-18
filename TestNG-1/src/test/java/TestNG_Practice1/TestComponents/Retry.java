package TestNG_Practice1.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import TestNG_Practice1.TestComponents.Retry;

public class Retry implements IRetryAnalyzer {

	int count = 0;
	int maxTry = 1;

	@Override
	public boolean retry(ITestResult result) {
		if (count < maxTry) {
			count++;
			return true;
		}
		return false;
	}

}
