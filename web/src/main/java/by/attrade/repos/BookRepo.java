package by.attrade.repos;

import by.attrade.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Long>{
    Book findByName(String name);
}
