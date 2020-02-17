package guru.springframework.sfgrecipe.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UnitOfMeasure {

    @Id
    private String unitOfMeasure;

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
