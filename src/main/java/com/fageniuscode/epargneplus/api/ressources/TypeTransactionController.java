package com.fageniuscode.epargneplus.api.ressources;

import com.fageniuscode.epargneplus.api.entities.dto.TypeTransactionDTO;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.services.TypeTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account/transaction/types")
public class TypeTransactionController {

    private final TypeTransactionService typeTransactionService;

    @Autowired
    public TypeTransactionController(TypeTransactionService typeTransactionService) {
        this.typeTransactionService = typeTransactionService;
    }

    @GetMapping
    public ResponseEntity<List<TypeTransactionDTO>> getAllTypeTransactions() {
        List<TypeTransactionDTO> typeTransactions = typeTransactionService.findAllOrderedByName();
        return ResponseEntity.ok(typeTransactions);
    }

    @GetMapping("/{typeTransactionId}")
    public ResponseEntity<TypeTransactionDTO> getTypeTransactionById(@PathVariable Long typeTransactionId) {
        Optional<TypeTransactionDTO> typeTransactionDTO = typeTransactionService.findById(typeTransactionId);
        if (typeTransactionDTO.isPresent()) {
            return ResponseEntity.ok(typeTransactionDTO.get());
        } else {
            throw new EntityNotFoundException("Aucun type de transaction avec l'ID : " + typeTransactionId);
        }
    }

    @PutMapping("/{typeTransactionId}")
    public ResponseEntity<TypeTransactionDTO> updateTypeTransaction(@PathVariable @Min(1) Long typeTransactionId, @RequestBody @Valid TypeTransactionDTO updatedTypeTransactionDTO) {
        try {
            TypeTransactionDTO updateTypeTransaction = typeTransactionService.updateTypeTransaction(typeTransactionId, updatedTypeTransactionDTO);
            return ResponseEntity.ok(updateTypeTransaction);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/count")
    public long countTypeTransactions() {
        return typeTransactionService.count();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TypeTransactionDTO> getTypeTransactionByName(@PathVariable String name) {
        Optional<TypeTransactionDTO> typeTransactionDTO = typeTransactionService.findByName(name);
        if (typeTransactionDTO.isPresent()) {
            return ResponseEntity.ok(typeTransactionDTO.get());
        } else {
            throw new EntityNotFoundException("Aucun type de transaction avec le nom : " + name);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TypeTransactionDTO> createTypeTransaction(@Valid @RequestBody TypeTransactionDTO typeTransactionDTO) {
        try {
            TypeTransactionDTO createdTypeTransactionDTO = typeTransactionService.addTypeTransaction(typeTransactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTypeTransactionDTO);
        } catch (RequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{typeTransactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTypeTransaction(@PathVariable Long typeTransactionId) {
        try {
            typeTransactionService.deleteById(typeTransactionId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TypeTransactionDTO>> searchTypeTransactionsByDescription(@RequestParam String keyword) {
        List<TypeTransactionDTO> typeTransactions = typeTransactionService.findByDescriptionContaining(keyword);
        return ResponseEntity.ok(typeTransactions);
    }


    @GetMapping("/comment")
    public ResponseEntity<List<TypeTransactionDTO>> getTypeTransactionsByDescription(@RequestParam String keyword) {
        List<TypeTransactionDTO> typeTransactions = typeTransactionService.findTypeTransactionsByDescription(keyword);
        return ResponseEntity.ok(typeTransactions);
    }

}
