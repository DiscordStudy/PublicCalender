package discordstudy.calender.domain.member.dto;

import discordstudy.calender.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private String loginId;
    private String nickname;

    public SignupResponse(Member member) {
        //엔티티 이용
        loginId=member.getLoginId();
        nickname=member.getNickname();

    }
}
