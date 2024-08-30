package discordstudy.calender.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private final String loginId;
    private final String nickname;
    private final String password;

    public SignupRequest(String loginId, String nickname, String password) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
    } //아직 db의 Member entity에 저장되기 이전이니까 그냥 loginId , nickname ,password

}
