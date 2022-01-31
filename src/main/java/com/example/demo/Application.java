package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Student student = Student.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .age(faker.number().numberBetween(17, 55))
                    .build();

            student.addBook(Book.builder()
                    .bookName("Clean Code")
                    .createdAt(LocalDateTime.now().minusDays(4))
                    .build());

            student.addBook(Book.builder()
                    .bookName("Think and Grow Rich")
                    .createdAt(LocalDateTime.now())
                    .build());

            student.addBook(Book.builder()
                    .bookName("Spring Data JPA")
                    .createdAt(LocalDateTime.now().minusYears(1))
                    .build());

            StudentIdCard studentIdCard = StudentIdCard.builder()
                    .cardNumber("12345678")
                    .student(student)
                    .build();

            student.setStudentIdCard(studentIdCard);

            student.addEnrolment(Enrolment.builder()
                    .id(new EnrolmentId(1L, 1L))
                    .student(student)
                    .course(Course.builder()
                            .name("Computer Science")
                            .department("IT")
                            .build())
                    .createdAt(LocalDateTime.now())
                    .build());

            student.addEnrolment(Enrolment.builder()
                    .id(new EnrolmentId(1L, 2L))
                    .student(student)
                    .course(Course.builder()
                            .name("Amigoscode Spring Data JPA")
                            .department("IT")
                            .build())
                    .createdAt(LocalDateTime.now())
                    .build());

            studentRepository.save(student);

            studentRepository.findById(1L)
                    .ifPresent(s -> {
                        System.out.println("fetch books lazy...");
                        List<Book> books = student.getBooks();
                        books.forEach(book ->
                                System.out.println(s.getFirstName() + " borrowed " + book.getBookName()));
                    });

//            studentIdCardRepository.findById(1L)
//                    .ifPresent(System.out::println);

//            studentIdCardRepository.deleteById(1L); // TODO: Why does it delete Student Entity?
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
