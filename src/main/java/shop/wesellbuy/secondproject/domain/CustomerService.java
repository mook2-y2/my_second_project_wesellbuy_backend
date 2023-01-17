package shop.wesellbuy.secondproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import shop.wesellbuy.secondproject.domain.common.BaseDateColumnEntity;
import shop.wesellbuy.secondproject.domain.reply.CustomerServiceReply;

import java.util.ArrayList;
import java.util.List;

/**
 * 고객지원 board
 * writer : 이호진
 * init : 2023.01.14
 * updated by writer :
 * update :
 * description : 고객지원 게시판을 정의한다.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerService extends BaseDateColumnEntity {

    @Id @GeneratedValue
    @Column(name = "customerService_num")
    private Integer num;
    private String reportedId; // 신고된 회원 아이디
    private String content; // 신고 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num")
    private Member member; // 신고한 회원 아이디

    @OneToMany(mappedBy = "customerService")
    private List<CustomerServiceReply> customerServiceReplyList = new ArrayList<>(); // 댓글 모음

    // ** setter ** //
    public void addReportedId(String reportedId) {
        this.reportedId = reportedId;
    }

    public void addContent(String content) {
        this.content = content;
    }

    // ** 연관관계 메서드 ** //
    public void addMember(Member member) {
        this.member = member;
        member.getCustomerServiceList().add(this);
    }

    // ** 생성 메서드 ** //
    public static CustomerService createCustomerService(String reportedId, String content, Member member) {
        CustomerService customerService = new CustomerService();

        customerService.addReportedId(reportedId);
        customerService.addContent(content);
        customerService.addMember(member);

        return customerService;
    }

    // ** 비즈니스(서비스) 로직(메서드) ** //

}
