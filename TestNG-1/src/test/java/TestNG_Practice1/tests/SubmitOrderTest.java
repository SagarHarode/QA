package TestNG_Practice1.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import TestNG_Practice1.TestComponents.BaseTest;
import TestNG_Practice1.pageobjects.CartPage;
import TestNG_Practice1.pageobjects.CheckoutPage;
import TestNG_Practice1.pageobjects.ConfirmationPage;
import TestNG_Practice1.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		// ProductCatalogue productCatalogue =new ProductCatalogue(driver);
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("productName"));
		productCatalogue.goToCartPage();
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));

		// validations should be in main file only can't go in page object file
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));

	}

	@Test(dependsOnMethods = { "submitOrder" })

	public void OrderHistoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("sagartest@gmail.com", "Password#123");
		TestNG_Practice1.pageobjects.OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}

	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "//src//test//java//TestNG_Practice1//data//PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}
}
/*
 * @DataProvider public Object[][] getData() {
 * 
 * HashMap<String,String> map= new HashMap<String,String>(); map.put("email",
 * "sagartest@gmail.com"); map.put("password", "Password#123");
 * map.put("productName", "ZARA COAT 3");
 * 
 * HashMap<String,String> map1= new HashMap<String,String>(); map1.put("email",
 * "shetty@gmail.com"); map1.put("password", "Iamking@000");
 * map1.put("productName", "ADIDAS ORIGINAL"); //below using arrays //return new
 * Object [][] { {"sagartest@gmail.com","Password#123","ZARA COAT 3"},
 * {"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL"}}; //object is generic
 * data type return new Object [][] {{map , map1}}; }
 */
