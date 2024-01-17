package com.fageniuscode.epargneplus.api.ressources;

import com.fageniuscode.epargneplus.api.entities.dto.TransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.WalletDTO;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.services.WalletService;
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
import java.util.List;

@RestController
@RequestMapping("/api/account/wallets")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/saveWallet")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WalletDTO> createWallet(@Valid @RequestBody WalletDTO walletDTO) {
        try {
            WalletDTO createdWallet = walletService.saveWallet(walletDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWallet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<WalletDTO>> getAllWallets(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WalletDTO> wallets = walletService.getAllWallet(pageable);
        return new ResponseEntity<>(wallets, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WalletDTO> updateWallet(@PathVariable Long id, @Valid @RequestBody WalletDTO updatedWalletDTO) {
        try {
            WalletDTO updatedWallet = walletService.updateWallet(id, updatedWalletDTO);
            return ResponseEntity.ok(updatedWallet);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletDTO> getWalletById(@PathVariable Long id) {
        try {
            WalletDTO wallet = walletService.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Wallet not found with ID: " + id));
            return ResponseEntity.ok(wallet);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/number/{walletNumber}")
    public ResponseEntity<WalletDTO> getWalletByWalletNumber(@PathVariable String walletNumber) {
        try {
            WalletDTO wallet = walletService.findByWalletNumber(walletNumber)
                    .orElseThrow(() -> new EntityNotFoundException("Wallet not found with number: " + walletNumber));
            return ResponseEntity.ok(wallet);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<WalletDTO>> getWalletsByUsername(@PathVariable String username) {
        try {
            List<WalletDTO> wallets = walletService.findByUsername(username);
            return ResponseEntity.ok(wallets);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/by-create-date-after")
    public ResponseEntity<Page<WalletDTO>> getWalletsByCreateDateAfter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<WalletDTO> wallets = walletService.findByCreateDateAfter(date, pageable);
            return ResponseEntity.ok(wallets);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-create-date-before")
    public ResponseEntity<Page<WalletDTO>> getWalletsByCreateDateBefore(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<WalletDTO> wallets = walletService.findByCreateDateBefore(date, pageable);
            return ResponseEntity.ok(wallets);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-create-date-between")
    public ResponseEntity<Page<WalletDTO>> getWalletsByCreateDateBetween(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<WalletDTO> wallets = walletService.findAllByDateBetween(startDate, endDate, pageable);
            return ResponseEntity.ok(wallets);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/amount-greater-than/{amount}")
    public ResponseEntity<Page<WalletDTO>> getWalletsByAmountGreaterThan(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,@PathVariable double amount) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<WalletDTO> wallets = walletService.findByAmountGreaterThan(amount, pageable);
            return ResponseEntity.ok(wallets);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteWallet(@PathVariable Long id) {
        try {
            walletService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/calculate-total-amount/{walletId}")
    public ResponseEntity<Double> calculateTotalAmount(@PathVariable Long walletId) {
        try {
            Double totalAmount = walletService.calculateTotalAmount(walletId);
            return ResponseEntity.ok(totalAmount);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/add-amount/{walletId}")
    public ResponseEntity<Void> addAmountToWallet(@PathVariable Long walletId, @RequestParam double amount) {
        try {
            int updatedCount = walletService.addAmountToAmount(walletId, amount);
            if (updatedCount > 0) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/deduct-amount/{walletId}")
    public ResponseEntity<Void> deductAmountFromWallet(@PathVariable Long walletId, @RequestParam double amount) {
        try {
            int updatedCount = walletService.deductAmountFromAmount(walletId, amount);
            if (updatedCount > 0) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{walletId}/add-transaction")
    public ResponseEntity<TransactionDTO> addTransactionToWallet(@PathVariable Long walletId, @Valid @RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO createdTransaction = walletService.addTransactionToWallet(walletId, transactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{walletId}/update-transaction/{transactionId}")
    public ResponseEntity<TransactionDTO> updateTransactionInWallet(@PathVariable Long walletId, @PathVariable Long transactionId, @Valid @RequestBody TransactionDTO updatedTransactionDTO) {
        try {
            TransactionDTO updatedTransaction = walletService.updateTransactionInWallet(walletId, transactionId, updatedTransactionDTO);
            return ResponseEntity.ok(updatedTransaction);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
