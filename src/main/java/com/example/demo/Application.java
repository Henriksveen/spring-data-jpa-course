package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            studentIdCardRepository.save(StudentIdCard.builder()
                    .cardNumber("12345678")
                    .student(Student.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                            .age(faker.number().numberBetween(17, 55))
                            .build())
                    .build());

            studentRepository.findById(1L)
                    .ifPresent(System.out::println);

            studentIdCardRepository.findById(1L)
                    .ifPresent(System.out::println);

            studentIdCardRepository.deleteById(1L); // TODO: Why does it delete Student Entity?
//            studentRepository.deleteById(1L);
        };
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i <= 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Student student = Student.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .age(faker.number().numberBetween(17, 55))
                    .build();
            studentRepository.save(student);
        }
    }
}
