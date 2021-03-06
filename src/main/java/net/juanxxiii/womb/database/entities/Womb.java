package net.juanxxiii.womb.database.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "womb")
@NoArgsConstructor
public class Womb implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idwomb")
    private int id;

    @Column(name = "review")
    private String review;

    @Column(name = "score")
    private float score;

    @Column(name = "date")
    private String date;


    @ManyToOne(optional = false)
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    private Users user;


    @ManyToOne(optional = false)
    @JoinColumn(name = "idproduct", referencedColumnName = "idproduct")
    private Products product;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Womb womb = (Womb) o;
        return id == womb.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
