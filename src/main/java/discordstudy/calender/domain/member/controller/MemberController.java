package discordstudy.calender.domain.member.controller;

import discordstudy.calender.domain.member.dto.SignupRequest;
import discordstudy.calender.domain.member.dto.SignupResponse;
import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.service.MemberService;
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
}
