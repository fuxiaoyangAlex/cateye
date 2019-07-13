package com.aiyun.project2.service.impl;

import com.aiyun.project2.service.OrdersService;
import com.aiyun.project2.domain.Orders;
import com.aiyun.project2.repository.OrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Orders}.
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    private final Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);

    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    /**
     * Save a orders.
     *
     * @param orders the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Orders save(Orders orders) {
        log.debug("Request to save Orders : {}", orders);
        return ordersRepository.save(orders);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Orders> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return ordersRepository.findAll(pageable);
    }


    /**
     * Get one orders by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Orders> findOne(Long id) {
        log.debug("Request to get Orders : {}", id);
        return ordersRepository.findById(id);
    }

    /**
     * Delete the orders by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Orders : {}", id);
        ordersRepository.deleteById(id);
    }
}
