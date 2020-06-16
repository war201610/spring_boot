package readinglist;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ReadingListApplication.class)
@WebAppConfiguration
class MockMvcWebTests {
	@Autowired
	private WebApplicationContext webContext;
	
	private MockMvc mockMvc;

	@BeforeEach
	public void setupMockMvc() {
		System.out.println("setupMockMvc");
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.webContext)
				.apply(springSecurity())
				.build();
	}
	
	@AfterEach
	public void aftertest() {
		System.out.println("after test");
	}

	@Test
	void contextLoads() throws Exception {
		System.out.println("contextLoads");
	}
	
	@Test
	public void homePage() throws Exception {
		System.out.println("homePage");
		mockMvc.perform(MockMvcRequestBuilders.get("/readingList/rlget/craig"))
			.andExpect(status().isOk())
			.andExpect(view().name("readingList"))
			.andExpect(model().attributeExists("books"))
			.andExpect(model().attribute("books", is(empty())));
	}
	
	@Test
	public void postBook() throws Exception {
		//前后两次测试是关联的要向同一个读者发送请求
		final String reader = "craig";
		System.out.println("postBook");
		mockMvc.perform(post("/readingList/rlpost/{reader}",reader)//向字符串传递参数应该用post的参数里添加
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "BOOK TITLE")
				.param("author", "BOOK AUTHOR")
				.param("isbn", "123456")
				.param("description", "DESCRIPTION"))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/readingList/rlget/" + reader));
		
		Book expectedBook = new Book();
		expectedBook.setId(1L);
		expectedBook.setReader("craig");
		expectedBook.setTitle("BOOK TITLE");
		expectedBook.setAuthor("BOOK AUTHOR");
		expectedBook.setIsbn("123456");
		expectedBook.setDescription("DESCRIPTION");
		
		mockMvc.perform(get("/readingList/rlget/craig"))
			.andExpect(status().isOk())
			.andExpect(view().name("readingList"))
			.andExpect(model().attributeExists("books"))
			.andExpect(model().attribute("books", hasSize(1)))
			.andExpect(model().attribute("books", 
					contains(samePropertyValuesAs(expectedBook))));
	}
}
