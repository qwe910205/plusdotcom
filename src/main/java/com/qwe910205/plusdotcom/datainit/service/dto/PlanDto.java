package com.qwe910205.plusdotcom.datainit.service.dto;

public record PlanDto(String network_tech, String plan_category, String plan_desc, String name, String id,
                      Integer monthly_payment, String brand, String category, String dataPolicy_dataQty,
                      String sharing_data, String callingPolicy_calling, String messagePolicy_msg) {
}
