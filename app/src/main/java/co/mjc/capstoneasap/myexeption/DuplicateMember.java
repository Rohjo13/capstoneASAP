package co.mjc.capstoneasap.myexeption;


// 지금 사용하지 않음
@Deprecated
public class DuplicateMember extends RuntimeException{

    public DuplicateMember(RuntimeException e) {
        super(e);
    }
}
