package readinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//SpringMVC控制器
@Controller
@RequestMapping("/readingList")
//@EnableConfigurationProperties//让下面的注解生效, 有无都可以, 奇怪之前这个amazonID属性一直显示为空
//@ConfigurationProperties(prefix="amazon")
public class ReadingListController {
	
	private AmazonProperties amazonProperties;
	
	private ReadingListRepository readingListRepository;

	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository, 
			AmazonProperties amazonProperties) {
		this.readingListRepository = readingListRepository;
		this.amazonProperties = amazonProperties;
	}
//和类前面的requestmapping连起来用, get方法的映射是/readinglocalhost/rlget/任意值
	@RequestMapping(value = "/rlget/{reader}", method = RequestMethod.GET)
	public String readersBooks(@PathVariable("reader") String reader, Model model) {
		System.out.println("get method");
		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("reader", reader);
			model.addAttribute("amazonID", amazonProperties.getAssociateId());
		}
		return "readingList";
	}

	@RequestMapping(value = "/rlpost/{reader}", method = RequestMethod.POST)
	public String addToReadingList(@PathVariable("reader") String reader, Book book) {
		System.out.println("post method");
		book.setReader(reader);
		readingListRepository.save(book);
		return "redirect:/readingList/rlget/{reader}";
	}
}
