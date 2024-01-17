package com.fageniuscode.epargneplus.api.ressources;

import com.fageniuscode.epargneplus.api.entities.dto.AuditLogDTO;
import com.fageniuscode.epargneplus.api.exceptions.APIExceptionHandler;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.services.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Locale;

@RestController
@RequestMapping("/api/audit/auditLogs")
public class AuditLogController {
    private final AuditLogService auditLogService;
    private final MessageSource messageSource;
    private final APIExceptionHandler apiExceptionHandler;
    @Autowired
    public AuditLogController(AuditLogService auditLogService, MessageSource messageSource, APIExceptionHandler apiExceptionHandler) {
        this.auditLogService = auditLogService;
        this.messageSource = messageSource;
        this.apiExceptionHandler = apiExceptionHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuditLogDTO> createAuditLog(@Valid @RequestBody AuditLogDTO auditLogDTO) {
        try {
            AuditLogDTO createAuditLog = auditLogService.saveAuditLog(auditLogDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createAuditLog);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllAuditLogs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try{
            Pageable pageable = PageRequest.of(page, size);
            Page<AuditLogDTO> auditLogs = auditLogService.getAllAuditLogs(pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/auditlogid/{id}")
    public ResponseEntity<?> getAuditLogById(@PathVariable Long id) {
        try {
            AuditLogDTO auditLog = auditLogService.findById(id).orElseThrow(() -> new EntityNotFoundException(getMessage("auditlog.notfound", id)));
            return ResponseEntity.ok(auditLog);
        } catch (Exception e) {
            // Gérer l'exception et renvoyer un message d'erreur
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AuditLog introuvable pour l'ID " + id);
        }
    }

    @GetMapping("/auditlogusername")
    public ResponseEntity<?> getAuditLogByUsername(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String username){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AuditLogDTO> auditLogs = auditLogService.findByUsername(username, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/auditlogobjectname")
    public ResponseEntity<?> getAuditLogByObjectName(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String objectname){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AuditLogDTO> auditLogs = auditLogService.findByObjectName(objectname, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/auditlogcreatedateafter")
    public ResponseEntity<?> getAuditLogByCreateDateAfter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(value = "localDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AuditLogDTO> auditLogs = auditLogService.findByCreateDateAfter(localDateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/auditlogcreatedatebefore")
    public ResponseEntity<?> getAuditLogByCreateDateBefore(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(value = "localDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AuditLogDTO> auditLogs = auditLogService.findByCreateDateBefore(localDateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/auditlogcreatedatebetween")
    public ResponseEntity<?> getAuditLogByCreateDateBetween(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(value = "startDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime, @RequestParam(value = "endDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime ){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AuditLogDTO> auditLogs = auditLogService.findAllByCreateDateBetween(startDateTime, endDateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/auditlogusernameandcreatedate")
    public ResponseEntity<?> getAuditLogByUsernameAndCreateDate(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String username, @RequestParam(value = "dateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime dateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AuditLogDTO> auditLogs = auditLogService.findByUsernameAndCreateDate(username, dateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/auditlogobjectnameandcreatedate")
    public ResponseEntity<?> getAuditLogByObjectNameAndCreateDate(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String objectname, @RequestParam(value = "dateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime dateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AuditLogDTO> auditLogs = auditLogService.findByObjectNameAndCreateDate(objectname, dateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/auditlogusernameandcreatedatebetween")
    public ResponseEntity<?> getAuditLogByUsernameAndCreateDateBetween(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String username, @RequestParam(value = "startDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime startDateTime, @RequestParam(value = "endDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime endDateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AuditLogDTO> auditLogs = auditLogService.findByUsernameAndCreateDateBetween(username, startDateTime, endDateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/auditlogobjectnameandcreatedatebetween")
    public ResponseEntity<?> getAuditLogByObjectNameAndCreateDateBetween(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String objectname, @RequestParam(value = "startDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime startDateTime, @RequestParam(value = "endDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime endDateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AuditLogDTO> auditLogs = auditLogService.findByObjectNameAndCreateDateBetween(objectname, startDateTime, endDateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteauditlog/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAuditLog(@PathVariable Long id) {
        try {
            auditLogService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException  e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{auditLogId}")
    public ResponseEntity<AuditLogDTO> updateAuditLog(@PathVariable @Min(1) Long auditLogId, @RequestBody @Valid AuditLogDTO updatedAuditLogDTO) {
        try {
            AuditLogDTO updateAuditLog = auditLogService.updateAuditLog(auditLogId, updatedAuditLogDTO);
            return ResponseEntity.ok(updateAuditLog);
        } catch (EntityNotFoundException  ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

}
