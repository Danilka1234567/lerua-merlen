import infrastructure.config.DataBaseConfig;
import model.entities.User;
import model.repository.implementations.UserRepositoryImpl;
import model.repository.interfaces.UserRepository;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public class Main {

    public static void main(String[] args) {

        DataBaseConfig.initialize();

        UserRepository repository = new UserRepositoryImpl();

        Long id = repository.save(new User(
                new Email("test2324@example.com"),
                new PhoneNumber("+79776083169"),
                "Petya",
                "Petrushkin"
        ));

        repository.update(new User(
                id,
                new Email("test123@example.com"),
                new PhoneNumber("+79776083169"),
                "Petya",
                "Petrushkin"
        ));


    }
}
