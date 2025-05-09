package com.github.cidarosa.ms_pedido.service;

import com.github.cidarosa.ms_pedido.dto.PedidoDTO;
import com.github.cidarosa.ms_pedido.entities.Pedido;
import com.github.cidarosa.ms_pedido.repositories.PedidoRepository;
import com.github.cidarosa.ms_pedido.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Transactional(readOnly = true)
    public List<PedidoDTO> findAllPedidos(){

        return repository.findAll()
                .stream().map(PedidoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public PedidoDTO  findById(Long id){

        Pedido entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. Id: " + id)
        );

        return new PedidoDTO(entity);
    }
}
