package sbnz.integracija.chefadvisor.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import sbnz.integracija.chefadvisor.domain.User;
import sbnz.integracija.chefadvisor.events.UserActionEvent;
import sbnz.integracija.chefadvisor.facts.AlarmInputFact;
import sbnz.integracija.chefadvisor.repository.UserRepository;
import sbnz.integracija.chefadvisor.service.AlarmService;
import sbnz.integracija.chefadvisor.service.RatingService;
import sbnz.integracija.chefadvisor.service.dto.AlarmDTO;
import sbnz.integracija.chefadvisor.service.dto.RatingDTO;
import sbnz.integracija.chefadvisor.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link sbnz.integracija.chefadvisor.domain.Rating}.
 */
@RestController
@RequestMapping("/api")
public class RatingResource {

    private final Logger log = LoggerFactory.getLogger(RatingResource.class);

    private static final String ENTITY_NAME = "rating";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatingService ratingService;
    
    private final UserRepository userRepository;
    
    private final AlarmService alarmService;
    
    @Autowired
    private KieSession cepKieSession;

    public RatingResource(RatingService ratingService, UserRepository userRepository, AlarmService alarmService) {
        this.ratingService = ratingService;
        this.userRepository = userRepository;
        this.alarmService = alarmService;
    }

    /**
     * {@code POST  /ratings} : Create a new rating.
     *
     * @param ratingDTO the ratingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratingDTO, or with status {@code 400 (Bad Request)} if the rating has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ratings")
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO, @AuthenticationPrincipal UserDetails loggedUser) throws URISyntaxException {
        log.debug("REST request to save Rating : {}", ratingDTO);
        if (ratingDTO.getId() != null) {
            throw new BadRequestAlertException("A new rating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (ratingDTO.getUserId() == null) {
        	User user = userRepository.findOneByLogin(loggedUser.getUsername()).orElse(null);

        	ratingDTO.setUserLogin(loggedUser.getUsername());
        	ratingDTO.setUserId(user.getId());
        }
        RatingDTO result = ratingService.save(ratingDTO);
        AlarmInputFact aif = new AlarmInputFact();
        this.cepKieSession.insert(new UserActionEvent(result.getUserId(), Double.parseDouble(result.getRating().toString())));
        this.cepKieSession.insert(aif);
        this.cepKieSession.fireAllRules();
        if (aif.getMessage() != null) {
        	AlarmDTO alarmDTO = new AlarmDTO();
        	alarmDTO.setMessage(aif.getMessage());
        	alarmDTO.setUserId(aif.getUserId());
        	this.alarmService.save(alarmDTO);
        }
        
        return ResponseEntity.created(new URI("/api/ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ratings} : Updates an existing rating.
     *
     * @param ratingDTO the ratingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingDTO,
     * or with status {@code 400 (Bad Request)} if the ratingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ratings")
    public ResponseEntity<RatingDTO> updateRating(@RequestBody RatingDTO ratingDTO) throws URISyntaxException {
        log.debug("REST request to update Rating : {}", ratingDTO);
        if (ratingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RatingDTO result = ratingService.save(ratingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ratingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ratings} : get all the ratings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratings in body.
     */
    @GetMapping("/ratings")
    public List<RatingDTO> getAllRatings() {
        log.debug("REST request to get all Ratings");
        return ratingService.findAll();
    }

    /**
     * {@code GET  /ratings/:id} : get the "id" rating.
     *
     * @param id the id of the ratingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ratings/{id}")
    public ResponseEntity<RatingDTO> getRating(@PathVariable Long id) {
        log.debug("REST request to get Rating : {}", id);
        Optional<RatingDTO> ratingDTO = ratingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratingDTO);
    }

    /**
     * {@code DELETE  /ratings/:id} : delete the "id" rating.
     *
     * @param id the id of the ratingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ratings/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        log.debug("REST request to delete Rating : {}", id);
        ratingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
