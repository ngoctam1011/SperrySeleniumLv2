package tadashboard;

import org.apache.log4j.Logger;
import org.testng.annotations.*;

import common.LogWrapper;
import constant.Constant;

public class DA_LOGIN_TC001_LoginDashboard extends BaseTest {

	protected static final Logger LOG = LogWrapper.createLogger(DA_LOGIN_TC001_LoginDashboard.class.getName());

	private String invalidUser = "abc";
	private String invalidPass = "123";

	@Test(description = "Verify that user can login specific repository successfully via Dashboard login page with correct credentials")
	public void Valid_Login() throws Exception {

		LOG.info("Login with valid credential");
		LoginPage loginPage = page.GetInstance(LoginPage.class);
		HomePage homePage = loginPage.loginValid(Constant.USERNAME, Constant.PASSWORD, Constant.REPO);

		LOG.info("Verify: Repo display correctly");
		homePage.checkRepoDisplay(Constant.REPO);

		LOG.info("Verify: Login User display correctly");
		homePage.checkLoginUserDisplay(Constant.USERNAME);
	}

	@Test(description = "Verify that user fails to login specific repository successfully via Dashboard login page with incorrect credentials")
	public void Login_Invalid_Credential() throws Exception {

		LOG.info("Login with invalid credential");
		LoginPage loginPage = page.GetInstance(LoginPage.class).loginInValid(invalidUser, invalidPass, Constant.REPO);

		LOG.info("Verify: 'Username or password is invalid' message displays in alert");
		loginPage.checkAlertMessage("Username or password is invalid", 20);

	}
}
