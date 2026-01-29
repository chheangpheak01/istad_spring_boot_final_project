package com.sopheak.istadfinalems.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "addresses")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    private String street;
    private String city;
    private String province;
    private String country;
    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToOne(mappedBy = "address")
    @ToString.Exclude
    private Employee employee;
}