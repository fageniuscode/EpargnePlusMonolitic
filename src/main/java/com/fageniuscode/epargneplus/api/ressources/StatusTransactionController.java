package com.fageniuscode.epargneplus.api.ressources;

import com.fageniuscode.epargneplus.api.entities.dto.StatusTransactionDTO;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.services.StatusTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account/status-transactions")
public class StatusTransactionController {
    private final StatusTransactionService statusTransactionService;

    @Autowired
    public StatusTransactionController(StatusTransactionService statusTransactionService) {
        this.statusTransactionService = statusTransactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StatusTransactionDTO> saveStatusTransaction(@Valid @RequestBody StatusTransactionDTO statusTransactionDTO) {
        try {
            StatusTransactionDTO createStatusTransactionDTO = statusTransactionService.saveStatusTransaction(statusTransactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createStatusTransactionDTO);
        } catch (RequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{statusTransactionId}")
    public ResponseEntity<StatusTransactionDTO> updateStatusTransaction(@PathVariable @Min(1) Long statusTransactionId, @RequestBody @Valid StatusTransactionDTO updatedStatusTransactionDTO) {
        try {
            StatusTransactionDTO updateTypeStatusTransaction = statusTransactionService.updateStatusTransaction(statusTransactionId, updatedStatusTransactionDTO);
            return ResponseEntity.ok(updateTypeStatusTransaction);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusTransactionDTO> getStatusTransactionById(@PathVariable Long id) {
        try {
            Optional<StatusTransactionDTO> statusTransactionDTO = statusTransactionService.findById(id);
            return ResponseEntity.ok(statusTransactionDTO.orElse(null));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<StatusTransactionDTO> getStatusTransactionByName(@PathVariable String name) {
        try {
            Optional<StatusTransactionDTO> statusTransactionDTO = statusTransactionService.findByName(name);
            return ResponseEntity.ok(statusTransactionDTO.orElse(null));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<StatusTransactionDTO>> getAllStatusTransactions() {
        List<StatusTransactionDTO> statusTransactions = statusTransactionService.findAllOrderedByName();
        return ResponseEntity.ok(statusTransactions);
    }

    @GetMapping("/comment")
    public ResponseEntity<List<StatusTransactionDTO>> getStatusTransactionsByComment(@RequestParam String keyword) {
        List<StatusTransactionDTO> statusTransactions = statusTransactionService.findByCommentContaining(keyword);
        return ResponseEntity.ok(statusTransactions);
    }

    @GetMapping("/count")
    public long countStatusTransactions() {
        return statusTransactionService.count();
    }

    @GetMapping("/search")
    public ResponseEntity<List<StatusTransactionDTO>> searchStatusTransactionsByComment(@RequestParam String keyword) {
        List<StatusTransactionDTO> statusTransactions = statusTransactionService.findStatusTransactionsByComment(keyword);
        return ResponseEntity.ok(statusTransactions);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteStatusTransaction(@PathVariable Long id) {
        try {
            statusTransactionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
