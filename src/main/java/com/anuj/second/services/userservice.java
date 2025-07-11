package com.anuj.second.services;
import com.anuj.second.entity.user;
import com.anuj.second.repository.userrepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class userservice {

    @Autowired
    private userrepository repo;

    private static final PasswordEncoder encode=new BCryptPasswordEncoder();

    public boolean save_new_user(user d){
        try{
            d.setPassword(encode.encode(d.getPassword()));
            d.setRoles(Arrays.asList("USER"));
            repo.save(d);
            return true;
        }
        catch(Exception e){
            log.info("Error while saving user {}",d.getUsername(),e);
            return false;
        }
    }

    public void save_admin(user d){
        d.setPassword(encode.encode(d.getPassword()));
        d.setRoles(Arrays.asList("USER","ADMIN"));
        repo.save(d);
    }

    public void save_existing_user(user d){
        repo.save(d);
    }

    public List<user> getAll(){
        return repo.findAll();
    }

    public void deleteById(String id){
        repo.deleteById(id);
    }

    public user findByUsername(String user){return repo.findByusername(user);}
}
