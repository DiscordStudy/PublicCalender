package discordstudy.calender.domain.member.service;

import discordstudy.calender.domain.member.dto.LoginRequest;
import discordstudy.calender.domain.member.dto.SignupRequest;
import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.repository.MemberRepository;
import discordstudy.calender.domain.team.enums.Role;
import discordstudy.calender.global.config.jwt.JwtTokenProvider;
import discordstudy.calender.global.exception.ApplicationException;
import discordstudy.calender.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    //회원가입은 Save를 해야함 !
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Member registermember(SignupRequest request) {
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new ApplicationException(ErrorCode.DUPLICATED_LOGINID);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = new Member(request.getLoginId(), request.getNickname(), encodedPassword, Role.MEMBER);
        return memberRepository.save(member);
    }

    public String authenticate(LoginRequest request) {
        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        return jwtTokenProvider.createToken(member.getLoginId(), List.of(member.getRole().toString()));
    }


}
