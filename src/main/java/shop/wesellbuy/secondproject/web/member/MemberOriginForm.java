package shop.wesellbuy.secondproject.web.member;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import shop.wesellbuy.secondproject.domain.member.SelfPicture;
import shop.wesellbuy.secondproject.util.ValidationOfPattern;

/**
 * 회원 정보 form dto
 * writer : 이호진
 * init : 2023.01.26
 * updated by writer : 이호진
 * update : 2023.02.08
 * description : 클라이언트가 보내온 회원 정보를 담아둔다.
 *
 * update : - 비밀번호 확인 추가
 *          - 생성자 추가
 *
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberOriginForm {

    @Pattern(regexp = "^[가-힣|a-zA-Z]+$")
    private String name; // 이름
    @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣\\w]{1,21}$")
    private String id; // 아이디
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[?<>~!@#$%^&*_+-])[a-z\\d?<>~!@#$%^&*_+-]{8,21}$")
    private String pwd; // 비밀번호

    private String pwdConfirm; // 비밀번호 확인
    @Pattern(regexp = "^\\w+@[a-zA-Z\\d]+\\.[a-zA-Z\\d]+(\\.[a-zA-Z\\d]+)?$")
    private String email; // 이메일
    @Pattern(regexp = "^01(0|[6-9])\\d{4}\\d{4}$")
    private String selfPhone; // 휴대전화(필수)
    private String homePhone; // 집전화(선택)
    @NotBlank(message = "국적을 선택해주세요")
    private String country; // 나라 이름
    @NotBlank(message = "지역을 선택해주세요")
    private String city; // 지역 이름
    @NotBlank(message = "동/거리명을 입력해주세요")
    private String street; // 동
    @NotBlank(message = "상세주소를 입력해주세요")
    private String detail; // 상세주소
    @NotBlank(message = "우편번호를 입력해주세요")
    private String zipcode; // 우편번호

    private MultipartFile file; // 회원 이미지

    // 생성자




    // ** 비즈니스 로직 ** //
    /**
     * writer : 이호진
     * init : 2023.01.26
     * updated by writer :
     * update :
     * description : MemberOriginForm을 MemberForm으로 변경
     */
    public MemberForm changeAsMemberForm(SelfPicture selfPicture) {

        MemberForm memberForm = new MemberForm(name, id, pwd, email, selfPhone, homePhone, country, city, street, detail, zipcode, selfPicture);

        return memberForm;
    }

    /**
     * writer : 이호진
     * init : 2023.02.08
     * updated by writer :
     * update :
     * description : 회원 가입 value 입력 오류 검사
     */
    public void validateJoinValues(BindingResult bindingResult) {
        // 아이디 오류
        String patternId = "^[ㄱ-ㅎㅏ-ㅣ가-힣\\w]{1,21}$";
        ValidationOfPattern.validateValues(patternId, this.getId(), bindingResult, "id", "failed", null);

        // 비밀번호 오류
        String patternPwd = "^(?=.*[a-z])(?=.*\\d)(?=.*[?<>~!@#$%^&*_+-])[a-z\\d?<>~!@#$%^&*_+-]{8,21}$";
        ValidationOfPattern.validateValues(patternPwd, this.getPwd(), bindingResult, "pwd", "failed", null);

        // 비밀번호 확인 오류
        String pwd = this.getPwd();
        String pwdConfirm = this.getPwdConfirm();
        if(StringUtils.hasText(pwd) && StringUtils.hasText(pwdConfirm)) {
            if(!pwd.equals(pwdConfirm)) {
                bindingResult.rejectValue("pwdConfirm", "failed", null);
            }
        }

        // 이름 오류
        String patternName = "^[가-힣|a-zA-Z]+$";
        ValidationOfPattern.validateValues(patternName, this.getName(), bindingResult, "memberName", "failed", null);

        // 이메일 오류
        String patternEmail = "^\\w+@[a-zA-Z\\d]+\\.[a-zA-Z\\d]+(\\.[a-zA-Z\\d]+)?$";
        ValidationOfPattern.validateValues(patternEmail, this.getEmail(), bindingResult, "memberEmail", "failed", null);

        // 휴대전화 오류
        String patternSelfPhone = "^01(0|[6-9])\\d{4}\\d{4}$";
        ValidationOfPattern.validateValues(patternSelfPhone, this.getSelfPhone(), bindingResult, "selfPhone", "failed", null);

        // 선택 사항으로 만들기
        // 집전화 오류
//        String patternPhone2 = "^0(2|[3-6][1-5])\\d{3,4}\\d{4}$";
//        ValidationOfPattern.validateValues(patternPhone2, this.getPhone2(), bindingResult, "phone2", "failed", null);

        // 파일 확장자 조사
        String patternFile = ".*(?<=\\.(jpg|JPG|png|PNG|jpeg|JPEG|gif|GIF))";
        if(getFile() != null) {
            ValidationOfPattern.validateValues(patternFile, this.getFile().getOriginalFilename(), bindingResult, "file", "failed", null);
        }
    }


}
