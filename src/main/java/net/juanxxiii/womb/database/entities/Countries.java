package net.juanxxiii.womb.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "countries")
public class    Countries implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "iso")
    private String iso;

    @Column(name = "nicename")
    private String nicename;

    @Column(name = "name")
    private String name;

    @Column(name = "iso3")
    private String iso3;

    @Column(name = "numcode")
    private int numcode;

    @Column(name = "phonecode")
    private int phonecode;


}
