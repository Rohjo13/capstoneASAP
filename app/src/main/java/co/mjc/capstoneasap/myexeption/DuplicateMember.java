package co.mjc.capstoneasap.myexeption;

public class DuplicateMember extends RuntimeException{

    public DuplicateMember(RuntimeException e) {
        super(e);
    }
}
