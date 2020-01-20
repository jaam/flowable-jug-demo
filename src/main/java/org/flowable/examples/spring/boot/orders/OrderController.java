package org.flowable.examples.spring.boot.orders;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.cmmn.api.CmmnRuntimeService;
import org.flowable.cmmn.api.CmmnTaskService;
import org.flowable.cmmn.api.runtime.CaseInstance;
import org.flowable.examples.spring.boot.orders.model.OrderDto;
import org.flowable.examples.spring.boot.orders.model.ShipmentInformation;
import org.flowable.task.api.Task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Filip Hrisafov
 */
@RestController
public class OrderController {

	protected final CmmnRuntimeService cmmnRuntimeService;

	protected final CmmnTaskService cmmnTaskService;

	protected final ObjectMapper objectMapper;

	public OrderController(CmmnRuntimeService cmmnRuntimeService, CmmnTaskService cmmnTaskService, ObjectMapper objectMapper) {
		this.cmmnRuntimeService = cmmnRuntimeService;
		this.cmmnTaskService = cmmnTaskService;
		this.objectMapper = objectMapper;
	}


	@PostMapping("/orders")
	public ResponseEntity<ObjectNode> createOrder(@RequestBody OrderDto orderDto) {
		CaseInstance caseInstance = cmmnRuntimeService.createCaseInstanceBuilder()
				.caseDefinitionKey("new_order")
				.variable("orderNumber", orderDto.getOrderNumber())
				.variable("article", orderDto.getArticle())
				.variable("price", orderDto.getPrice())
				.variable("deliveryType", orderDto.getDeliveryType())
				.start();

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(objectMapper.createObjectNode()
						.put("orderPlacementId", caseInstance.getId())
				);
	}

	@PostMapping("/orders/{orderPlacementId}/shipped")
	public ResponseEntity<ObjectNode> orderShipped(@PathVariable String orderPlacementId, @RequestBody ShipmentInformation shipmentInformation) {
		Task orderShipped = cmmnTaskService.createTaskQuery()
				.caseInstanceId(orderPlacementId)
				.taskDefinitionKey("orderShipped")
				.singleResult();

		if (orderShipped == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(objectMapper.createObjectNode()
							.put("message", "Order placement does not exist or it has already been shipped")
					);
		}
		else {
			LocalDate shipmentDate = shipmentInformation.getShipmentDate();
			Period deliveryPeriod = shipmentInformation.getDeliveryPeriod();
			if (deliveryPeriod.isNegative()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(objectMapper.createObjectNode()
								.put("message", "Delivery must be positive")
						);
			}

			int years = deliveryPeriod.getYears();
			int months = deliveryPeriod.getMonths();
			int days = deliveryPeriod.getDays();
			int deliveryDays = years * 365 + months * 30 + days;
			// There is not yet support for LocalDate
			Map<String, Object> variables = new HashMap<>();
			variables.put("shipmentDate", Date.from(shipmentDate.atStartOfDay(ZoneOffset.UTC).toInstant()));
			variables.put("deliveryDays", deliveryDays);
			cmmnTaskService.complete(orderShipped.getId(), variables);
			return ResponseEntity.status(HttpStatus.OK)
					.body(objectMapper.createObjectNode()
							.put("orderPlacementId", orderPlacementId)
					);
		}
	}
}
