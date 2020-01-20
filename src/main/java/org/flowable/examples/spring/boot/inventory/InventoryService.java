package org.flowable.examples.spring.boot.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Jose Antonio Alvarez
 */
@Service
public class InventoryService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryService.class);

    public void lockItem(String orderNumber, String article) {
        LOG.info(String.format("Article %s for order %s locked in inventory", article, orderNumber));
    }

}
