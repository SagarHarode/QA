package TestNG_Practice1.tests;

import java.io.IOException;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sun.net.httpserver.Authenticator.Retry;

import TestNG_Practice1.TestComponents.BaseTest;
import TestNG_Practice1.pageobjects.CartPage;
import TestNG_Practice1.pageobjects.OrderPage;
import TestNG_Practice1.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	String productName = "ZARA COAT 3";

	@Test(groups = { "ErrorHandling" }, retryAnalyzer = TestNG_Practice1.TestComponents.Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {

		String productName = "ZARA COAT 3";
		landingPage.loginApplication("sagartest@gmail.com", "Password#123Incorrect");
		Assert.assertEquals("Incorrect email ()or password.", landingPage.getErrorMessage());

	}

	public void ProductErrorValidation() throws IOException, InterruptedException {

		ProductCatalogue productCatalogue = landingPage.loginApplication("rahulshetty@gmail.com", "Iamking@000");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);

	}

}
