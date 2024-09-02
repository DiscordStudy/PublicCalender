package discordstudy.calender.domain.member.service;

import discordstudy.calender.domain.member.dto.LoginRequest;
import discordstudy.calender.domain.member.dto.SignupRequest;
import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    //회원가입은 Save를 해야함 !
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member registermember(SignupRequest request)

    {
//
        Member member=new Member(request.getLoginId(), request.getNickname(), request.getNickname());
        return memberRepository.save(member);
    }

    public boolean authenticate(LoginRequest request)
    {
        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 로그인 아이디 나 패스워드 입니다"));

        return passwordEncoder.matches(request.getPassword(), member.getPassword());
        //entity 객체인 member의 password와 요청dto로 들어온 request의 password를 비교해서
        //맞으면 true 아니면 false를 가짐
    }



}
