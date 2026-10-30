package fu.de200388.demologin.b3;

import java.time.LocalDate;
import java.util.*;


public record User(String fullName, String email, String username, String password,
                   String gender, LocalDate dob, String major, List<String> hobbies) {}
