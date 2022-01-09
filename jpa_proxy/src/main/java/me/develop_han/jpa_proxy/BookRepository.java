package me.develop_han.jpa_proxy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
	// 인터페이스만 상속받았는데 Autowired 자동 주입 된다.
}
