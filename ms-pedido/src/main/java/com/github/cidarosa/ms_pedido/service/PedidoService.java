package com.github.cidarosa.ms_pedido.service;

import com.github.cidarosa.ms_pedido.dto.ItemDoPedidoDTO;
import com.github.cidarosa.ms_pedido.dto.PedidoDTO;
import com.github.cidarosa.ms_pedido.entities.ItemDoPedido;
import com.github.cidarosa.ms_pedido.entities.Pedido;
import com.github.cidarosa.ms_pedido.entities.Status;
import com.github.cidarosa.ms_pedido.repositories.ItemDoPedidoRepository;
import com.github.cidarosa.ms_pedido.repositories.PedidoRepository;
import com.github.cidarosa.ms_pedido.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ItemDoPedidoRepository itemDoPedidoRepository;

    @Transactional(readOnly = true)
    public List<PedidoDTO> findAllPedidos() {

        return repository.findAll()
                .stream().map(PedidoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public PedidoDTO findById(Long id) {

        Pedido entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. Id: " + id)
        );

        return new PedidoDTO(entity);
    }

    @Transactional
    public PedidoDTO savePedido(PedidoDTO dto) {

        Pedido entity = new Pedido();
        entity.setData(LocalDate.now());
        entity.setStatus(Status.REALIZADO);
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        itemDoPedidoRepository.saveAll(entity.getItens());
        return new PedidoDTO(entity);
    }

    @Transactional
    public PedidoDTO updatePedido(Long id, PedidoDTO dto) {

        try {
            Pedido entity = repository.getReferenceById(id);
            entity.setData(LocalDate.now());
            entity.setStatus(Status.REALIZADO);
            itemDoPedidoRepository.deleteByPedidoId(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            itemDoPedidoRepository.saveAll(entity.getItens());
            return new PedidoDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. Id: " + id);
        }
    }

    @Transactional
    public void deletePedido(Long id){

        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. Id: " + id);
        }

        repository.deleteById(id);
    }

    private void copyDtoToEntity(PedidoDTO dto, Pedido entity) {

        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());

        List<ItemDoPedido> itens = new ArrayList<>();
        itens.clear();

        for (ItemDoPedidoDTO itemDTO : dto.getItens()) {
            ItemDoPedido itemDoPedido = new ItemDoPedido();
            itemDoPedido.setQuantidade(itemDTO.getQuantidade());
            itemDoPedido.setDescricao(itemDTO.getDescricao());
            itemDoPedido.setValorUnitario(itemDTO.getValorUnitario());
            itemDoPedido.setPedido(entity);
            itens.add(itemDoPedido);
        }
        entity.setItens(itens);
    }
}
