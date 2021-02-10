package ch.supsi.webapp.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Item> itemsInThisCategory;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SottoCategory> sottoCategorie;

}
