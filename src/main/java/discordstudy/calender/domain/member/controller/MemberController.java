package discordstudy.calender.domain.member.controller;

import discordstudy.calender.domain.member.dto.LoginRequest;
import discordstudy.calender.domain.member.dto.SignupRequest;
import discordstudy.calender.domain.member.dto.SignupResponse;
import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.service.MemberService;
import discordstudy.calender.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Void> signup(@RequestBody SignupRequest request) {
        Member savedMember = memberService.registermember(request);
        SignupResponse response = new SignupResponse(savedMember);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request) {
        String token = memberService.authenticate(request);//jwt토큰 생성
        if (token != null)//회원가입이 성공한 회원 ! 이라면
        {
            //return ApiResponse.okWithMessage("로그인 성공",token);
            return ApiResponse.okWithCustomHeader("로그인 성공", "Authorization", "Bearer" + token);
        }
        return ApiResponse.errorCustom(HttpStatus.UNAUTHORIZED, "로그인 실패");
    }
}
