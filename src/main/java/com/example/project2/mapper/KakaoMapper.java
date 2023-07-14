package com.example.project2.mapper;

import com.example.project2.domain.DonationForm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.Authentication;

@Mapper
public interface KakaoMapper {

    @Insert("""
            INSERT INTO DonationForm (partner_order_id, partner_user_id, item_name, total_amount)
            VALUES (#{partner_order_id}, #{partner_user_id}, #{item_name}, #{total_amount})
            """)
    Integer insertDonationInfo(DonationForm donationForm);

    @Select("""
            SELECT *
            FROM DonationForm
            WHERE partner_order_id = #{partner_order_id}
            """)
    DonationForm selectDonationByOrderId(String partner_order_id);
}
