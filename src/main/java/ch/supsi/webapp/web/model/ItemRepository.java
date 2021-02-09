package ch.supsi.webapp.web.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT b FROM Item b where b.author.username = :value")
    List<Item> findAllByAuthor(@Param(value = "value") String username);

}
