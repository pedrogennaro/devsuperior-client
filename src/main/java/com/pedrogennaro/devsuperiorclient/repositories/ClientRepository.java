package com.pedrogennaro.devsuperiorclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrogennaro.devsuperiorclient.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}

