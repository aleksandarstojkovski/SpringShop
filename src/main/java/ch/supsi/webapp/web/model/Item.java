package ch.supsi.webapp.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@Entity // Entity => da persistere
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private User author;

    @OneToOne
    private Category category;

    @ManyToOne
    private Type type;

    @Lob
    private byte[] image;

    private Date date = new Date();

    public void edit(Item item){
        this.title = item.title;
        this.description = item.description;
        this.author = item.author;
        this.category = item.category;
        this.type = item.type;
    }

}
