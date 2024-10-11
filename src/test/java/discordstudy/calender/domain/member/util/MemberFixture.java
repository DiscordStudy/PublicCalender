package discordstudy.calender.domain.member.util;

import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.team.enums.Role;

public class MemberFixture {
    public static Member member = new Member(
            1L,
                    "loginId",
                    "홍길동",
                    "길동",
            Role.MEMBER
            );

    public static Member admin = new Member(
            2L,
            "adminId",
            "어드민",
            "password",
            Role.ADMIN
    );
}
