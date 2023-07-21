package com.example.project2.domain;

import lombok.Data;

@Data
public class DonationForm {

    private Integer campaignId;
    private String partner_order_id;
    private String partner_user_id;
    private String item_name;
    private String total_amount;
    private String targetAmount;
}
