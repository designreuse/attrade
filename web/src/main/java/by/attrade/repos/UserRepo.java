package by.attrade.repos;

import by.attrade.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findBySub(String sub);
}
