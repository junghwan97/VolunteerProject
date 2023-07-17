package com.example.project2.mapper;

import com.example.project2.domain.DonationForm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.Authentication;

import java.util.List;

@Mapper
public interface KakaoMapper {

    @Insert("""
            INSERT INTO DonationForm (campaignId, partner_order_id, partner_user_id, item_name, total_amount)
            VALUES (#{campaignId}, #{partner_order_id}, #{partner_user_id}, #{item_name}, #{total_amount})
            """)
    Integer insertDonationInfo(DonationForm donationForm);

    @Select("""
            SELECT *
            FROM DonationForm
            WHERE partner_order_id = #{partner_order_id}
            """)
    DonationForm selectDonationByOrderId(String orderId);

    @Insert("""
            INSERT INTO OrderNum
            VALUES(#{partner_order_id})
            """)
    void insertOrderId(String partner_order_id);

    @Select("""
            SELECT orderId
            FROM OrderNum
            ORDER BY orderId DESC
            LIMIT 1
            """)
    Integer selectOrderId();

    @Select("""
            SELECT\s
                d.campaignId,
            	d.partner_order_id,
                d.item_name,
                d.total_amount
            FROM DonationForm d LEFT JOIN OrderNum o ON d.partner_order_id = o.orderId
            WHERE partner_user_id = #{id}
            """)
    List<DonationForm> findMyDonation(String id);
}
