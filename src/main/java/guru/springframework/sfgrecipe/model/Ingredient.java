package guru.springframework.sfgrecipe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double amount;
    private String suggestion;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure uom;

    public Ingredient() {
    }

    public Ingredient(String name, Double amount, String suggestion, UnitOfMeasure uom) {
        this.name = name;
        this.amount = amount;
        this.suggestion = suggestion;
        this.uom = uom;
    }
}
