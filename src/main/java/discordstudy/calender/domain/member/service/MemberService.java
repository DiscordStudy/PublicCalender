package discordstudy.calender.domain.member.service;

import discordstudy.calender.domain.member.dto.SignupRequest;
import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    //회원가입은 Save를 해야함 !
    private final MemberRepository memberRepository;

    public Member registermember(SignupRequest request)

    {
        Member member=new Member();
        member.setLoginId(request.getLoginId());
        member.setNickname(request.getNickname());
        member.setPassword(request.getPassword());
        return memberRepository.save(member);
    }


}
