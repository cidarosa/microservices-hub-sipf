package com.github.cidarosa.ms_pagamento.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {

    @KafkaListener(topics = "topico-pedidos", groupId = "grupo-ms")
    public void consumirMensagem(String mensagem){

        System.out.println("Mensagem recebida: " + mensagem);
    }


}
