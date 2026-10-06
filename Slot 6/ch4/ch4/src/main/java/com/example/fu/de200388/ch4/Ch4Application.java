package com.example.fu.de200388.ch4;

import com.example.fu.de200388.ch4.pojo.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class Ch4Application {

	public static void main(String[] args) {
		SpringApplication.run(Ch4Application.class, args);
	}

}
