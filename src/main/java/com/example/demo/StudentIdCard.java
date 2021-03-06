package com.example.demo;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "StudentIdCard")
@Table(
        name = "student_id_card",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_id_card_number_unique", columnNames = "card_number")
        }
)
public class StudentIdCard {
    @Id
    @SequenceGenerator(
            name = "student_id_card_sequence",
            sequenceName = "student_id_card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_id_card_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "card_number", nullable = false, length = 15)
    private String cardNumber;
    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_id_card_fk")
    )
    private Student student;
}
