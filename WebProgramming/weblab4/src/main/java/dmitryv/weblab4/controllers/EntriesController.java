package dmitryv.weblab4.controllers;

import dmitryv.weblab4.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import dmitryv.weblab4.DTO.EntryDTO;
import dmitryv.weblab4.entities.Entry;
import dmitryv.weblab4.entities.User;
import dmitryv.weblab4.repositories.EntryRepository;

import java.security.Principal;

@RestController
@RequestMapping("/web-lab4/api/entries")
public class EntriesController {
    @Autowired
    private UserService userService;

    @Autowired
    private EntryRepository entryRepository;

    @GetMapping
    ResponseEntity<?> getUserEntries(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.findByUser(user));
    }

    @PostMapping
    ResponseEntity<?> addEntry(@Validated @RequestBody EntryDTO entryDTO, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.save(new Entry(
                entryDTO.getX(),
                entryDTO.getY(),
                entryDTO.getR(),
                user)));
    }

    @DeleteMapping
    ResponseEntity<?> deleteUserEntries(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.deleteByUser(user));
    }
}
