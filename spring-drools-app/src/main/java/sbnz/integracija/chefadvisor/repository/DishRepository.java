package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.Dish;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Dish entity.
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query(value = "select distinct dish from Dish dish left join fetch dish.types left join fetch dish.users",
        countQuery = "select count(distinct dish) from Dish dish")
    Page<Dish> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct dish from Dish dish left join fetch dish.types left join fetch dish.users")
    List<Dish> findAllWithEagerRelationships();

    @Query("select dish from Dish dish left join fetch dish.types left join fetch dish.users where dish.id =:id")
    Optional<Dish> findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select dish from Dish dish left join dish.users u where u.login = ?#{principal.username}")
    List<Dish> findByUserIsCurrentUser();
}
