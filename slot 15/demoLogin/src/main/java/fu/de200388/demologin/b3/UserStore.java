package fu.de200388.demologin.b3;

import java.time.LocalDate;
import java.util.*;


public final class UserStore {
    private static final Map<String, User> USERS = new HashMap<>();

    static {    // tài khoản mẫu
        add(new User("Quản trị viên", "admin@example.com", "admin", "Admin@123",
                "Khác", LocalDate.of(2000, 1, 1), "Kỹ thuật phần mềm", List.of()));
    }

    private UserStore() {}

    public static boolean exists(String username) {
        return USERS.containsKey(username.trim().toLowerCase(Locale.ROOT));
    }

    public static void add(User u) {
        String username = u.username().trim().toLowerCase(Locale.ROOT);
        if (USERS.putIfAbsent(username, u) != null) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }
    }

    public static Optional<User> login(String username, String password) {
        return Optional.ofNullable(USERS.get(username.trim().toLowerCase(Locale.ROOT)))
                .filter(u -> u.password().equals(password));
    }
}
// Lưu ý: app thật phải lưu HASH mật khẩu (BCrypt...), không lưu plain text.
