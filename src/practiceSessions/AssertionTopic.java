package practiceSessions;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AssertionTopic {

	@Test
	public void Compare() {
		Assert.assertEquals(10, 12);
	}
	@Test
	public void Compare1() {
		Assert.assertEquals(10, 12,"Failed please check	");
	}
	@Test
	public void Comparecorrect() {
		Assert.assertEquals(10, 10,"Failed please check	");
	}
	@Test
	public void test1() {
		String actual = "Madhu";
		
		Assert.assertTrue(actual.contains("Madhu"));
	}
	@Test
	public void test11() {
		String actual = "Madhu";
		Assert.assertTrue(actual.contains("kakularam"));
	}
	//softAssert
	@Test
	public void softAssert() {
		SoftAssert Assertion = new SoftAssert();
		System.out.println("Started");
		Assertion.assertEquals(12, 13);
		System.out.println("Completed");
		Assertion.assertAll();
	}
	
}
