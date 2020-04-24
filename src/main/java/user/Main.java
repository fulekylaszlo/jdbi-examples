package user;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import java.time.LocalDate;
import static user.User.Gender.OTHER;

public class Main
    {
    public static void main(String[] args)
        {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());

        try (Handle handle = jdbi.open())
            {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

            User usr01 = User.builder()
                    .id(1L)
                    .username("nemvagyokkreatívfelhasználónév")
                    .password("12345királyjelszó")
                    .name("Mégmindignemvagyokkreatív")
                    .email("bármilyennemlétező@emeilcím.hu")
                    .gender(OTHER)
                    .dob(LocalDate.parse("2020-04-24"))
                    .enabled(true)
                    .build();

            dao.insert(usr01);

            System.out.println(dao.findById(usr01.getId()) + " was found by ID.");
            System.out.println(dao.findByUsername(usr01.getUsername()) +" was found by Username.");
            dao.list().stream().forEach(System.out::println);

            dao.delete(usr01);
            }
        }
    }