package tadashboard;

import org.apache.log4j.Logger;
import org.testng.annotations.*;

import common.LogWrapper;
import constant.Constant;

public class DA_LOGIN_TC001_LoginDashboard extends BaseTest {
	
	protected static final Logger LOG = LogWrapper.createLogger(DA_LOGIN_TC001_LoginDashboard.class.getName());
	
	private String repoName = "SampleRepository";
	
	@Test (description = "Verify that user can login specific repository successfully via Dashboard login page with correct credentials")
	public void Valid_Login() throws Exception {
	
		LOG.info("Login with valid credential");
		LoginPage loginPage= page.GetInstance(LoginPage.class);
		HomePage homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD, Constant.REPO);
		
		LOG.info("Verify");
		homePage.checkRepoDisplay(repoName);
		
	}
}
