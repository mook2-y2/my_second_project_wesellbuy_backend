package shop.wesellbuy.secondproject.repository.orderitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.wesellbuy.secondproject.domain.order.OrderStatus;

/**
 *  OrderItem findAll for condition dto
 * writer : 이호진
 * init : 2023.01.19
 * updated by writer :
 * update :
 * description :  OrderItem finaAll에 사용되는 where 절의 조건 데이터 모음
 */
@Getter
@AllArgsConstructor
public class OrderItemSearchCond {

    private int sellerNum;// 판매자 번호
    private String orderId; // 주문자 아이디
    private String orderStatus; // 주문 상태
    private String deliveryStatus; // 배달 상태
    private String createDate; // 주문 날짜

    // ** 비즈니스 메서드 ** //
    /**
     * writer : 이호진
     * init : 2023.02.04
     * updated by writer :
     * update :
     * description : 판매자 번호 담기
     */
    public void addSellerNum(int sellerNum) {
        this.sellerNum = sellerNum;
    }
}
