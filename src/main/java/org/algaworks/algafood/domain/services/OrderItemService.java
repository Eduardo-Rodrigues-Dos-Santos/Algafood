package org.algaworks.algafood.domain.services;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.repositories.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;


}
