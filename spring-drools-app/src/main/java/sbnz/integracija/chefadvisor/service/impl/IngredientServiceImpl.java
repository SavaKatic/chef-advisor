package sbnz.integracija.chefadvisor.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbnz.integracija.chefadvisor.domain.Dish;
import sbnz.integracija.chefadvisor.domain.Ingredient;
import sbnz.integracija.chefadvisor.repository.IngredientRepository;
import sbnz.integracija.chefadvisor.service.IngredientService;
import sbnz.integracija.chefadvisor.service.dto.IngredientDTO;
import sbnz.integracija.chefadvisor.service.mapper.IngredientMapper;

/**
 * Service Implementation for managing {@link Ingredient}.
 */
@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    private final Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);

    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    /**
     * Save a ingredient.
     *
     * @param ingredientDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IngredientDTO save(IngredientDTO ingredientDTO) {
        log.debug("Request to save Ingredient : {}", ingredientDTO);
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDTO);
        ingredient = ingredientRepository.save(ingredient);
        return ingredientMapper.toDto(ingredient);
    }

    /**
     * Get all the ingredients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ingredients");
        return ingredientRepository.findAll(pageable)
            .map(ingredientMapper::toDto);
    }

    /**
     * Get one ingredient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IngredientDTO> findOne(Long id) {
        log.debug("Request to get Ingredient : {}", id);
        return ingredientRepository.findById(id)
            .map(ingredientMapper::toDto);
    }

    /**
     * Delete the ingredient by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ingredient : {}", id);
        ingredientRepository.deleteById(id);
    }
        
    /**
     * Get all by user is current user
     * 
     * @param pageable the pagination information
     * @return the list of entities
     * 
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientDTO> findByUserIsCurrentUser(Pageable pageable) {
        log.debug("Request to get all Ingredients by current user");
        List<Ingredient> ingredientList = ingredientRepository.findByUserIsCurrentUser();
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > ingredientList.size() ? ingredientList.size() : (start + pageable.getPageSize());
        Page<Ingredient> pages = new PageImpl<Ingredient>(ingredientList.subList(start, end), pageable, ingredientList.size());
        return pages.map(ingredientMapper::toDto);
    }
    
    public void adjustFridgeIngredients(Dish dish) {
        List<Ingredient> fridge = ingredientRepository.findByUserIsCurrentUser();
        for(Ingredient userIngredient: fridge) {
        	for (Ingredient requiredIngredient: dish.getIngredients()) {
        		if (userIngredient.equals(requiredIngredient)) {
        			if(requiredIngredient.getAmount() < userIngredient.getAmount()) {
        				Double newAmount = userIngredient.getAmount() - requiredIngredient.getAmount();
            			userIngredient.setAmount(newAmount);
            			ingredientRepository.save(userIngredient);
        			} else {
        		        ingredientRepository.deleteById(userIngredient.getId());
        			}
        		}
        	}
        }
    }
}
