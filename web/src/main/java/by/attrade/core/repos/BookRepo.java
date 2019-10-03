package by.attrade.core.repos;

import by.attrade.core.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Long>{
    Book findByName(String name);
}
