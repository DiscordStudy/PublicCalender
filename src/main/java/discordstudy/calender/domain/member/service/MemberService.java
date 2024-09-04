package discordstudy.calender.domain.member.service;

import discordstudy.calender.domain.member.dto.LoginRequest;
import discordstudy.calender.domain.member.dto.SignupRequest;
import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.repository.MemberRepository;
import discordstudy.calender.global.exception.ApplicationException;
import discordstudy.calender.global.exception.ErrorCode;
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
        String encodedPassword=passwordEncoder.encode(request.getPassword());
        Member member=new Member(request.getLoginId(), request.getNickname(), encodedPassword);
        return memberRepository.save(member);
    }

    public boolean authenticate(LoginRequest request)
    {
        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND));

        return passwordEncoder.matches(request.getPassword(), member.getPassword());
        //entity 객체인 member의 password와 요청dto로 들어온 request의 password를 비교해서
        //맞으면 true 아니면 false를 가짐
    }



}
