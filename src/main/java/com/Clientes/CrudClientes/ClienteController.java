package com.Clientes.CrudClientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path =  "api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService){
        this.clienteService=clienteService;
    }

    @GetMapping
    public List<Cliente> getClientes(){
        return clienteService.getClientes();
    }
    @PostMapping(path = "/postCliente")
    public ResponseEntity<Object> registrarCliente(@RequestBody  Cliente cliente){
        return this.clienteService.registrarCliente(cliente);
    }
    @PutMapping
    public ResponseEntity<Object> actualizarCliente(@RequestBody Cliente cliente){
        return this.clienteService.actualizarCliente(cliente);
    }
    @PostMapping(path = "/postClientes")
    public List<Cliente> registrarClientes(@RequestBody List<Cliente> clientes){
        return this.clienteService.registrarClientes(clientes);
    }
    @DeleteMapping(path= "{clienteId}")
    public ResponseEntity<Object> eliminarCliente(@PathVariable("clienteId") Long id){
        return this.clienteService.eliminarCliente(id);
    }
    @DeleteMapping(path = "/deleteAll")
    public void eliminarBasedeDatos(){
        clienteService.eliminarBBDD();
    }

}

