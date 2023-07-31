package com.example.project2.mapper;

import com.example.project2.domain.DonationForm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
            ORDER BY d.partner_order_id DESC
            """)
    List<DonationForm> findMyDonation(String id);

    @Select("""
            SELECT\s     
            	d.total_amount               
            FROM DonationForm d LEFT JOIN OrderNum o ON d.partner_order_id = o.orderId
            WHERE partner_user_id = #{id}
            """)
    List<String> findMyDonationMoney(String id);

    @Select("""
            SELECT\s     
            	d.total_amount               
            FROM DonationForm d LEFT JOIN OrderNum o ON d.partner_order_id = o.orderId
            WHERE campaignId = #{id}
            """)
    List<String> findMyDonationMoneyByCampaiginId(Integer id);

    @Insert("""
            INSERT INTO DonationForm(campaignId, targetAmount)
            VALUES (#{id}, #{targetAmount})
            """)
    void insertDonationTargetAmount(String targetAmount, Integer id);

    @Select("""
            SELECT partner_order_id
            FROM DonationForm
            WHERE campaignId = #{campaignId}
            """)
    Integer selectPartnerOrderId(Integer campaignId);

    @Select("""
            SELECT targetAmount
            FROM DonationForm
            WHERE campaignId = #{id} AND targetAmount IS NOT NULL
            """)
    Integer findCampaignTarget(Integer id);

    @Select("""
            SELECT SUM(total_amount)
            FROM DonationForm;
            """)
    Integer selectTotal_Amount();

    @Select("""
            SELECT count(partner_order_id)
            FROM DonationForm
            """)
    Integer countDonation();

    @Select("""
            SELECT count(partner_order_id)
            FROM DonationForm
            WHERE partner_user_id = #{id};
            """)
    Integer countDonaTime(String name);
}
