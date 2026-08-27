package controller;

import controller.shared.InputHandler;
import controller.shared.Runnable;
import model.entity.User;
import model.service.UserService;
import model.vo.Id;

public class AuthController implements Runnable {

    private User currentUser;
    private final UserService userService;
    private final static String WELCOME_MESSAGE = """
            Выберите, что вы хотите сделать:
            1. Зарегестрироваться
            2. Войти в сущ. аккаунт
            3. Закрыть приложение""";

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @Override
    public void run() {

        while(true){

            int choice = InputHandler.getIntFromClient(WELCOME_MESSAGE);

            if (choice == 3)
                return;
            if (choice < 1 || choice > 3){
                System.out.println("Такого числа нет в списке. Попробуйте снова");
                continue;
            }

            Id currentUserId = switch(choice){
                case 1 -> runRegistrationPage();
                case 2 -> runLoginPage();
                default -> null;
            };

            if (currentUserId != null){
                this.currentUser = userService.getUserById(currentUserId);
                break;
            }
        }

    }

    private Id runRegistrationPage(){
        System.out.println("Регистрация пользователя:");
        return userService.registerUser(InputHandler.getUserEntityFromClient());
    }

    private Id runLoginPage(){
        System.out.println("Вход в аккаунт:");
        return userService.loginUser(InputHandler.getEmailFromClient(), InputHandler.getPasswordFromClient());
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
