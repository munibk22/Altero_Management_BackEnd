package com.munib.coding.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.munib.coding.models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
	Optional<Users> findByUsername(String username);
}
