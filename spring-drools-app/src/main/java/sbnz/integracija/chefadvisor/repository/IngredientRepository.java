package sbnz.integracija.chefadvisor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sbnz.integracija.chefadvisor.domain.Ingredient;

/**
 * Spring Data  repository for the Ingredient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("select ingredient from Ingredient ingredient where ingredient.user.login = ?#{principal.username}")
    List<Ingredient> findByUserIsCurrentUser();
    
    @Query("select ingredient from Ingredient ingredient where ingredient.dish.id = :id")
    List<Ingredient> findByDish(@Param("id") Long id);
}
