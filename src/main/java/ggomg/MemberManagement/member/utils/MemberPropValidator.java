package ggomg.MemberManagement.member.utils;

public class MemberPropValidator {

    public static void validateUsername(String username) {
        NoSpecialAndSpace(username);
        checkUsernameLength(username);
    }

    public static void validatePassword(String password) {
        NoSpace(password);
    }

    public static void validateNickname(String nickname) {
        KrEnNumOnly(nickname);
    }

    private static void KrEnNumOnly(String nickname) {
        String regularExpressions = "^[가-힣a-zA-Z0-9]*$";
        if (!nickname.matches(regularExpressions)) {
            throw new IllegalArgumentException("only kr en num");
        }
    }

    private static void NoSpecialAndSpace(String username) {
        String regularExpressions = "[a-zA-Z0-9\\\\s]+";
        if (!username.matches(regularExpressions)) {
            throw new IllegalArgumentException("could not include special or space");
        }
    }

    private static void checkUsernameLength(String username) {
        if (username.trim().length() < 4 || username.trim().length() > 17) {
            throw new IllegalArgumentException("length error");
        }
    }

    private static void NoSpace(String username) {
        if (!username.equals(username.replaceAll(" ", ""))) {
            throw new IllegalArgumentException("could not include space");
        }
    }

}
