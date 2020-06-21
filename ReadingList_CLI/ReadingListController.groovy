@Controller
@RequestMapping("/")
class ReadingListController {
    String reader = "Craig"

    @Autowired
    ReadingListRepository readingListRepository

    @RequestMapping(method=RequestMethod.GET)
    def readersBooks(Model model) {
        List<Book> readingList = readingListRepository.findByReader(reader)

        if(readingList) {
            model.addAttribute("books", readingList)
        }

        "readingList"
    }

    @RequestMapping(method=RequestMethod.POST)
    def addToReadingList(Book book) {
        // System.out.println(book.id + " " + book.reader + " " + book.isbn + " " + book.title + " " + book.author + " " + book.description)
        book.setReader(reader)
        readingListRepository.save(book)
        "redirect:/"
    }
}
