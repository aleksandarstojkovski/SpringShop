package ch.supsi.webapp.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity // Entity => da persistere
@NoArgsConstructor
public class Type {

    @Id
    private String name;

    public Type(String name){
        this.name=name;
    }

}
