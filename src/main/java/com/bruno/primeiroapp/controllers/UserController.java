package com.bruno.primeiroapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.primeiroapp.entities.User;
import com.bruno.primeiroapp.repositories.UserRepository;

@RestController
@RequestMapping(value="/users")
public class UserController {
    
    @Autowired
    private UserRepository repository;
    
    @GetMapping
    public List<User> findall(){
        List<User> result = repository.findAll();
        return result;
    }
    
    @GetMapping(value = "/{id}")
    public User findById(@PathVariable Long id){
        User result = repository.findById(id).orElse(null); // Melhor usar orElse(null) ou orElseThrow()
        return result;
    }
    
    @PostMapping
    public User insert(@RequestBody User user){
        User result = repository.save(user);
        return result;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { // A, C
        if (repository.existsById(id)) { // Verifica se o usuário existe antes de tentar deletar
            repository.deleteById(id); // B
            return ResponseEntity.noContent().build(); // Retorna 204 No Content para sucesso sem corpo
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não existir
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUserOptional = repository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            User updatedUser = repository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}