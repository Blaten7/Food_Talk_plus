package com.icia.recipe.repository;

import com.icia.recipe.entity.InvenAdd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface InvenAddRepository extends JpaRepository<InvenAdd, Long> {

    @Modifying
    void deleteDeletefromfooditem();

    void executeGetinvenlist();

    void executeGetsortedinvenlist();

    @Modifying
    void saveInsertinvenadd();

    void executeGetinvenaddlist();

    void executeGetinvenaddlistsort();

    void executeFinalorder();

    void executeGetsearchmodaldetails();

    void executeGetfooditemlist();

    void executeGetimg();

    void executeEmptyfooditem();

    @Modifying
    void deleteGetdeletefooditemlist();

    @Modifying
    void updateUpdatefooditem();
}
