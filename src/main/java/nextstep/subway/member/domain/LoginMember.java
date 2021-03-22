package nextstep.subway.member.domain;


import nextstep.subway.auth.domain.UserDetails;

public class LoginMember implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private Integer age;

    public LoginMember(Long id, String email, String password, Integer age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public static LoginMember of(Long id, String email, String password, Integer age) {
        return new LoginMember(id, email, password, age);
    }

    public static LoginMember of(Member member) {
        return of(member.getId(), member.getEmail(), member.getPassword(), member.getAge());
    }

    @Override
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }
}
