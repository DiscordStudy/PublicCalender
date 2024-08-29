package discordstudy.calender.domain.member.controller;

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
    public ResponseEntity<Member> signup(@RequestBody Member member )
    {
        Member savedmember = memberService.registermember(member);
        return ResponseEntity.ok(savedmember);
        //responseEntity의 body에 savedmember넣기
    }
}
