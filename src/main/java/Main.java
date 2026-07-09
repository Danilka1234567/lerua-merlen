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

        repository.save(new User(
                new Email("test@example.com"),
                new PhoneNumber("+79956083169"),
                "Vacya",
                "Pupkin"
        ));


    }
}
