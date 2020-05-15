package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.IngredientModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the IngredientModel entity.
 */
@Repository
public interface IngredientModelRepository extends JpaRepository<IngredientModel, Long> {

    @Query(value = "select distinct ingredientModel from IngredientModel ingredientModel left join fetch ingredientModel.unitTypes left join fetch ingredientModel.ingredientTypes",
        countQuery = "select count(distinct ingredientModel) from IngredientModel ingredientModel")
    Page<IngredientModel> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct ingredientModel from IngredientModel ingredientModel left join fetch ingredientModel.unitTypes left join fetch ingredientModel.ingredientTypes")
    List<IngredientModel> findAllWithEagerRelationships();

    @Query("select ingredientModel from IngredientModel ingredientModel left join fetch ingredientModel.unitTypes left join fetch ingredientModel.ingredientTypes where ingredientModel.id =:id")
    Optional<IngredientModel> findOneWithEagerRelationships(@Param("id") Long id);
}
