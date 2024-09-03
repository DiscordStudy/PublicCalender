package discordstudy.calender.domain.member.controller;

import discordstudy.calender.domain.member.dto.LoginRequest;
import discordstudy.calender.domain.member.dto.SignupRequest;
import discordstudy.calender.domain.member.dto.SignupResponse;
import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.service.MemberService;
import discordstudy.calender.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequest request)
    {
        Member savedMember=memberService.registermember(request);
        SignupResponse response=new SignupResponse(savedMember);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody LoginRequest request)
    {
        boolean authenticate = memberService.authenticate(request);
        if(authenticate)//회원가입이 성공한 회원 ! 이라면
        {
            return ResponseEntity.ok().build();//로그인 성공시 ok를 보내고 싶음
        }
        return ResponseEntity.status(401).build();
    }
}
