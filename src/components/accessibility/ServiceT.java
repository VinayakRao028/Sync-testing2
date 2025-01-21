package melreposteria.org.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import melreposteria.org.dto.*;
import melreposteria.org.model.*;
import melreposteria.org.repository.*;




@Service

public class ClientesService {
	
	private final ClientesRepository clientesRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	 @Autowired
	    public ClientesService(ClientesRepository clientesRepository) {
	        this.clientesRepository = clientesRepository;
	    }//

	 
	 public List<Clientes> getAllClientes() {
	        return clientesRepository.findAll();
	    }//getClientes
	 
	  public Clientes getClientes(Long id) {
	        return clientesRepository.findById(id).orElseThrow(
	            () -> new IllegalArgumentException("El usuario con el id [" + id + "] no existe")
	        );
	    }//getClientes

	  public Clientes deleteClientes(Long id) {
	        Clientes tmpclientes = null;
	        if (clientesRepository.existsById(id)) {
	            tmpclientes = clientesRepository.findById(id).get();
	            clientesRepository.deleteById(id);
	        }
	        return tmpclientes;
	    }//deleteClientes
	 
	  public Clientes addClientes(Clientes clientes) {
	        Optional<Clientes> tmpclientes = clientesRepository.findByEmail(clientes.getEmail());
	        if (tmpclientes.isEmpty()) {
	        	clientes.setPassword(encoder.encode(clientes.getPassword()));
	            return clientesRepository.save(clientes);
	        } else {
	            System.out.println("El usuario con el email [" + clientes.getEmail() + "] ya existe");
	            return null;
	        }
	    }//addClientes
	  
	  	  
	public Clientes updateClientes(Long id, ChangePassword changePassword) {
			Clientes tmpclientes= null;
			if(clientesRepository.existsById(id)) {
				tmpclientes =clientesRepository.findById(id).get();
//				if (changePassword.getPassword().equals(tmpclientes)) {
				if(encoder.matches(changePassword.getPassword(), tmpclientes.getPassword())) {
					tmpclientes.setPassword(encoder.encode(changePassword.getNPassword()));
					clientesRepository.save(tmpclientes);
				}else {
					System.out.println("updateUser - El password del usuario [" +
						id	+ "] no coincide");
					tmpclientes=null;
				}//else
			}//if existsById
			return tmpclientes;
		}


	public boolean validateCliente(Clientes clientes) {
		Optional<Clientes> userByEmail =
		clientesRepository.findByEmail(clientes.getEmail());
		if(userByEmail.isPresent()) {
			Clientes tmpClientes = userByEmail.get();
			if(encoder.matches(clientes.getPassword(), tmpClientes.getPassword())) {
				return true;				
			} // if encoder			
		}		
		return false;
	} // validateCliente
	  	

}// class ClientesService
