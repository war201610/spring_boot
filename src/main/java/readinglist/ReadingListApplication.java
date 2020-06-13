package readinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//整个程序的启动类
@SpringBootApplication
//上面的注解混合了三个注解: @Configuration @ComponentScan @Controller
public class ReadingListApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReadingListApplication.class, args);
	}
 
}
