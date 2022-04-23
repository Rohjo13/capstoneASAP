package co.mjc.capstoneasap;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import android.widget.EditText;

import java.util.Optional;

import co.mjc.capstoneasap.dto.Member;
import co.mjc.capstoneasap.repository.MemberRepository;
import co.mjc.capstoneasap.repository.MemoryMemberRepository;
import co.mjc.capstoneasap.service.MemberService;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    MemberService memberService;
    MemberRepository memberRepository;

    public ExampleUnitTest() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);

    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


//    @Test
//    public void 로그인멤버() {
//        String id = "123";
//        String pwd = "123";
//        Optional<Member> login = memberService.login(id, pwd);
//
//        assertEquals(memberRepository.getId(id).get(),login.get());
//    }
}