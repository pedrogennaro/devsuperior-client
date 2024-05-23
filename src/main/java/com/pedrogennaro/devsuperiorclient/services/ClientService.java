package com.pedrogennaro.devsuperiorclient.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedrogennaro.devsuperiorclient.dto.ClientDTO;
import com.pedrogennaro.devsuperiorclient.entities.Client;
import com.pedrogennaro.devsuperiorclient.repositories.ClientRepository;
import com.pedrogennaro.devsuperiorclient.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Client product = clientRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Cliente não encontrado!"));
		return new ClientDTO(product);
	}
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(Pageable pageable) {
		Page<Client> result = clientRepository.findAll(pageable);
		return result.map(x -> new ClientDTO(x));
	}
	
	@Transactional
	public ClientDTO save(ClientDTO client) {
		Client entity = new Client();
		copyDtoToEntity(client, entity);
		
		entity = clientRepository.save(entity);
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO product) {
		try {
			Client entity = clientRepository.getReferenceById(id);
			
			copyDtoToEntity(product, entity);
			
			entity = clientRepository.save(entity);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Cliente não encontrado!");
		}
		
	}
	
	@Transactional
	public void delete(Long id) {
		if (!clientRepository.existsById(id)) {
			throw new ResourceNotFoundException("Cliente não encontrado");
		}
		clientRepository.deleteById(id);
	}
	
	private void copyDtoToEntity(ClientDTO client, Client entity) {
		entity.setName(client.getName());
		entity.setCpf(client.getCpf());
		entity.setIncome(client.getIncome());
		entity.setBirthDate(client.getBirthDate());
		entity.setChildren(client.getChildren());
	}

}
