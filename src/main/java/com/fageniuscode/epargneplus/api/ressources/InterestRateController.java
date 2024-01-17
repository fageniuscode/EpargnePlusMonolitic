package com.fageniuscode.epargneplus.api.ressources;

import com.fageniuscode.epargneplus.api.entities.dto.InterestRateDTO;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.services.InterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account/interest-rates")
public class InterestRateController {

    private final InterestRateService interestRateService;

    @Autowired
    public InterestRateController(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<InterestRateDTO>> getAllInterestRates() {
        List<InterestRateDTO> interestRates = interestRateService.getAllInterestRates();
        return ResponseEntity.ok(interestRates);
    }

    @GetMapping("/{interestRateId}")
    public ResponseEntity<InterestRateDTO> getInterestRateById(@PathVariable Long interestRateId) {
        Optional<InterestRateDTO> interestRateDTO = interestRateService.findInterestRateById(interestRateId);
        if (interestRateDTO.isPresent()) {
            return ResponseEntity.ok(interestRateDTO.get());
        } else {
            throw new EntityNotFoundException("Aucun taux d'intérêt.");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InterestRateDTO> createInterestRate(@Valid @RequestBody InterestRateDTO interestRateDTO) {
        try {
            InterestRateDTO createInterestRateDTO = interestRateService.saveInterestRate(interestRateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createInterestRateDTO);
        } catch (RequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{interestRateId}")
    public ResponseEntity<InterestRateDTO> updateInterestRate(@PathVariable @Min(1) Long interestRateId, @RequestBody @Valid InterestRateDTO updatedInterestRateDTO) {
        try {
            InterestRateDTO updateInterestRate = interestRateService.updateInterestRate(interestRateId, updatedInterestRateDTO);
            return ResponseEntity.ok(updateInterestRate);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{interestRateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteInterestRate(@PathVariable @Min(1) Long interestRateId) {
        try {
            interestRateService.deleteInterestRateById(interestRateId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/greater-than/{rate}")
    public List<InterestRateDTO> getInterestRatesGreaterThan(@PathVariable double rate) {
        return interestRateService.findInterestRatesByRateGreaterThan(rate);
    }

    @GetMapping("/less-than/{rate}")
    public List<InterestRateDTO> getInterestRatesLessThan(@PathVariable double rate) {
        return interestRateService.findInterestRatesByRateLessThan(rate);
    }

    @GetMapping("/between/{minRate}/{maxRate}")
    public List<InterestRateDTO> getInterestRatesBetween(
            @PathVariable double minRate,
            @PathVariable double maxRate) {
        return interestRateService.findInterestRatesByRateBetween(minRate, maxRate);
    }

    @GetMapping("/description")
    public List<InterestRateDTO> getInterestRatesByDescriptionContaining(@RequestParam String keyword) {
        return interestRateService.findInterestRatesByDescriptionContaining(keyword.toLowerCase());
    }

    @GetMapping("/count")
    public long countInterestRates() {
        return interestRateService.countInterestRates();
    }

    @GetMapping("/custom-query")
    public List<InterestRateDTO> getInterestRatesByCustomJPQLQuery(@RequestParam String description) {
        return interestRateService.findInterestRatesByCustomJPQLQuery(description);
    }


}
