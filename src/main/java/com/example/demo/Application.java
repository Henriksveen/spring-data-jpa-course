package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            System.out.println("Adding Maria and Ahmed");
            studentRepository.saveAll(
                    List.of(Student.builder()
                                    .firstName("Maria")
                                    .lastName("Jones")
                                    .email("maria.jones@amigoscode.edu")
                                    .age(21).build(),
                            Student.builder()
                                    .firstName("Maria")
                                    .lastName("Jones")
                                    .email("maria2.jones@amigoscode.edu")
                                    .age(25).build(),
                            Student.builder()
                                    .firstName("Ahmed")
                                    .lastName("Ali")
                                    .email("ahmed.ali@amigoscode.edu")
                                    .age(18).build()
                    )
            );

            studentRepository.findStudentByEmail("ahmed.ali@amigoscode.edu")
                    .ifPresentOrElse(System.out::println, () -> System.out.println("Student with email not found"));


            studentRepository.findStudentByEmail("unknown@amigoscode.edu")
                    .ifPresentOrElse(System.out::println, () -> System.out.println("Student with email not found"));

            studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual("Maria", 21)
                    .forEach(System.out::println);

            studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqualNative("Maria", 21)
                    .forEach(System.out::println);

            System.out.println("Deleting Maria 2");
            System.out.println(studentRepository.deleteStudentById(3L));
        };
    }
}
