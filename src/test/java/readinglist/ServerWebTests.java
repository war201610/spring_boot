package readinglist;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ReadingListApplication.class)
public class ServerWebTests {
	private static EdgeDriver edge;
	@Value("${local.Server.port}")
	private int port;
	
	@BeforeAll
	public static void openBrowser() {
		edge = new EdgeDriver();
		edge.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterAll
	public static void closeBrowser() {
		edge.quit();
	}
	
	@Test
	public void addBookToEmptyList() {
		System.out.println("port: "+port);
		String baseUrl = "http://localhost:"+port+"/readingList/rlget/reader";
		
		edge.get(baseUrl);
		
		assertEquals("You have no books in your book list",
				edge.findElementByTagName("div").getText());
		edge.findElementByName("title").sendKeys("BOOK TITLE");
		edge.findElementByName("auther").sendKeys("BOOK AUTHOR");
		edge.findElementByName("isbn").sendKeys("123456");
		edge.findElementByName("description").sendKeys("DESCRIPTION");
		edge.findElementByName("form").submit();
		
		WebElement dl = edge.findElementByCssSelector("dt.bookHeadline");
		assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 123456)", dl.getText());
		WebElement dt = edge.findElementByCssSelector("dd.bookDescription");
		assertEquals("DESCRIPTION", dt.getText());
	}
}
