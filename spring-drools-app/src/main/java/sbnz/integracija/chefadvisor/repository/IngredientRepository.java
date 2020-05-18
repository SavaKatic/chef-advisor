package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.Ingredient;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Ingredient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("select ingredient from Ingredient ingredient where ingredient.user.login = ?#{principal.username}")
    List<Ingredient> findByUserIsCurrentUser();
}
