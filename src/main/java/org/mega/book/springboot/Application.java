package org.mega.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing //메인에 이거 있어야 Auditing 기능 사용할 수 있게 됨
@SpringBootApplication
public class Application {
    public static void main(String[] args){ SpringApplication.run(Application.class, args);}
}
