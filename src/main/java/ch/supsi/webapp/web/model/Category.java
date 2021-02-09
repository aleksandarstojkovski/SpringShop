package ch.supsi.webapp.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity // Entity => da persistere
@NoArgsConstructor
public class Category {

    @Id
    private String name;

    public Category(String name){
        this.name=name;
    }

}
