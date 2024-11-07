package org.algaworks.algafood.core.page_order_translation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public class Translator {

    public static Pageable translate(Pageable pageable, Map<String, String> fields) {
        List<Sort.Order> orders = pageable.getSort().stream()
                .filter(order -> fields.get(order.getProperty()) != null)
                .map(order -> new Sort.Order(order.getDirection(), fields.get(order.getProperty()))).toList();
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
}
