package com.fageniuscode.epargneplus.api.ressources;

import com.fageniuscode.epargneplus.api.entities.dto.StatusTransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.TransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.TypeTransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.WalletDTO;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/account/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> getAllTransactions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionDTO> transactions = transactionService.getAllTransactions(pageable);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        try {
            TransactionDTO transaction = transactionService.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
            return ResponseEntity.ok(transaction);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO createdTransaction = transactionService.saveTransaction(transactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionDTO updatedTransactionDTO) {
        try {
            TransactionDTO updatedTransaction = transactionService.updateTransaction(id, updatedTransactionDTO);
            return ResponseEntity.ok(updatedTransaction);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countTransactions() {
        try {
            long count = transactionService.count();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/amount-range")
    public ResponseEntity<Page<TransactionDTO>> getTransactionsByAmountRange(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam double minAmount, @RequestParam double maxAmount) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionDTO> transactions = transactionService.findTransactionsByAmountRange(minAmount, maxAmount, pageable);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<TransactionDTO>> getTransactionsByDateRange(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionDTO> transactions = transactionService.findAllByDateBetween(startDate, endDate, pageable);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-wallet")
    public ResponseEntity<Page<TransactionDTO>> getTransactionsByWallet(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam Long walletId) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionDTO> transactions = transactionService.findByWallet(new WalletDTO(walletId), pageable);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-type-transaction")
    public ResponseEntity<Page<TransactionDTO>> getTransactionsByTypeTransaction(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam Long typeTransactionId) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionDTO> transactions = transactionService.findByTypeTransaction(new TypeTransactionDTO(typeTransactionId), pageable);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-status-transaction")
    public ResponseEntity<Page<TransactionDTO>> getTransactionsByStatusTransaction(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam Long statusTransactionId) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionDTO> transactions = transactionService.findByStatusTransaction(new StatusTransactionDTO(statusTransactionId), pageable);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-create-date-after")
    public ResponseEntity<Page<TransactionDTO>> getTransactionsByCreateDateAfter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionDTO> transactions = transactionService.findByCreateDateAfter(date, pageable);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-create-date-before")
    public ResponseEntity<Page<TransactionDTO>> getTransactionsByCreateDateBefore(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionDTO> transactions = transactionService.findByCreateDateBefore(date, pageable);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-status-and-type-transaction")
    public ResponseEntity<Page<TransactionDTO>> getTransactionsByStatusAndTypeTransaction(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam Long statusTransactionId, @RequestParam Long typeTransactionId) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionDTO> transactions = transactionService.findAllByStatusTransactionAndTypeTransaction(new StatusTransactionDTO(statusTransactionId), new TypeTransactionDTO(typeTransactionId), pageable);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
