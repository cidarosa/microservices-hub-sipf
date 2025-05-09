package com.github.cidarosa.ms_pedido.repositories;

import com.github.cidarosa.ms_pedido.entities.ItemDoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDoPedidoRepository extends JpaRepository<ItemDoPedido, Long> {
}
