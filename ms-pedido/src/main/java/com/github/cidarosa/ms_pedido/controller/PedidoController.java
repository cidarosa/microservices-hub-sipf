package com.github.cidarosa.ms_pedido.controller;

import com.github.cidarosa.ms_pedido.dto.PedidoDTO;
import com.github.cidarosa.ms_pedido.dto.StatusDTO;
import com.github.cidarosa.ms_pedido.entities.Pedido;
import com.github.cidarosa.ms_pedido.service.PedidoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping("/port")
    public String getPort(@Value("${local.server.port}") String porta){
        return String.format("Request da Instância recebida na porta %s ", porta);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos(){

        List<PedidoDTO> list = service.findAllPedidos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getById(@PathVariable Long id){

        PedidoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody @Valid PedidoDTO dto){

        dto = service.savePedido(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id,
                                                  @RequestBody @Valid PedidoDTO dto){

        dto = service.updatePedido(id, dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/pago")
    public ResponseEntity<String> aprovarPagamentoDoPedido(@PathVariable
                                                           @NotNull Long id){

        service.aprovarPagamentoDoPedido(id);
        String msg = "Pedido pago, aguardando confirmação do pagamento";
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> updatePedidoStatus(@PathVariable Long id,
                                                        @RequestBody StatusDTO statusDTO){
        PedidoDTO dto = service.updatePedidoStatus(id, statusDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidoById(@PathVariable Long id){

        service.deletePedido(id);
        return ResponseEntity.noContent().build();
    }






}
