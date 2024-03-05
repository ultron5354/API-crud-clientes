package com.Clientes.CrudClientes;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private List<Cliente> clientes;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository=clienteRepository;
    }

    public List<Cliente> getClientes() {
        return  this.clienteRepository.findAll();
    }

    public ResponseEntity<Object> registrarCliente(Cliente cliente) {
        //buscar si el cliente existe
        Optional<Cliente> clienteTemp = clienteRepository.findClienteByName(cliente.getName());
        // error por si el cliente existe en la BBDD
        if(clienteTemp.isPresent() && cliente.getId()==null){
            
            return new ResponseEntity<>(
                  
                    HttpStatus.CONFLICT);
        }
        clienteRepository.save(cliente);

        return new ResponseEntity<>(
                HttpStatus.CREATED);
    }

 public List<Cliente> registrarClientes(@NotNull List<Cliente> clientes) {


       List<Cliente> clientesBBDD = clienteRepository.findAll();
        for (Cliente cli : clientes) {
                 if (!clienteEstaEnLaBBDD(cli,clientesBBDD)){
                 clienteRepository.save(cli);
                 }
        }
       return clientes;
}

public boolean clienteEstaEnLaBBDD(Cliente cliente, @NotNull List<Cliente> clientes){
       for(Cliente cli : clientes){
           if (cliente.equals(cli)){
               return true;
           }
       }
       return false;
   }


public ResponseEntity<Object> eliminarCliente(Long id) {
        boolean existe = this.clienteRepository.existsById(id);
        if(!existe){

            return new ResponseEntity<>(
                    HttpStatus.CONFLICT);
        }
        clienteRepository.deleteById(id);
        return new ResponseEntity<>(
                HttpStatus.ACCEPTED);
    }

    public void  eliminarBBDD() {
         clienteRepository.deleteAll();
    }

    public ResponseEntity<Object> actualizarCliente( Cliente cliente) {
        if(cliente.getId()!=null){
            clienteRepository.save(cliente);
            return new ResponseEntity<>(
                    HttpStatus.ACCEPTED
            );
        }
        return new ResponseEntity<>(
                HttpStatus.CONFLICT
        );
    }
}
