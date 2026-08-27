package controller;


import controller.shared.InputHandler;
import model.entity.User;
import model.enums.UserRole;
import model.service.UserService;
import model.vo.Id;

import java.util.List;

public class UserController implements Runnable{

    private final UserService userService;
    private User currentUser;

    private final static String WELCOME_MESSAGE = """
            Выберите, что хотите сделать:
            1. Поменять информацию о пользователе
            2. Пометить пользователя как удалённого
            3. Очистить помеченных пользователей
            4. Выполнить поиск по пользователям
            5. Выйти в главное меню
            """;

    public UserController(User currentUser, UserService userService){
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @Override
    public void run() {
        while(true){

            if(currentUser == null)
                throw new IllegalArgumentException(
                        "Текущий юзер должен быть определён до запуска!"
                );

            int choice = InputHandler.getIntFromClient(WELCOME_MESSAGE);

            if (choice == 5)
                return;

            if (choice < 1 || choice > 5){
                System.out.println("Такой кнопки нет в списке! Попробуйте снова");
                continue;
            }

            switch(choice){
                case 1 -> runUpdatingPage();
                case 2 -> runMarkingAsDeletedPage();
                case 3 -> cleanDeleted();
                case 4 -> runMainSearchingPage();
            }

        }
    }

    public void setCurrentUser(User currentUser) {
        if (currentUser == null)
            throw new IllegalArgumentException(
                    "Текущий пользовотелей не может быть пустым"
            );
        this.currentUser = currentUser;
    }

    private void runUpdatingPage(){
        if (currentUser.getRole() == UserRole.ADMIN){

            userService.updateUserInfo(
                    null,
                    null,
                    InputHandler.getUserRoleFromClient(),
                    InputHandler.getIdFromClient("Введите айди пользователя:")
            );
        }else{
            System.out.println("Введите все данные для изменяния:" + "\n" +
                    "(если хотите оставить что-то не неизменным, введите прежние значения");

            userService.updateUserInfo(
                    InputHandler.getContactInfoFromClient(),
                    InputHandler.getPasswordFromClient(),
                    currentUser.getRole(),
                    currentUser.getId()
            );
        }

    }

    private void runMarkingAsDeletedPage(){
        if (currentUser.getRole() == UserRole.ADMIN){
            Id userId = InputHandler.getIdFromClient(
                    "Введите айди пользователя, которого вы бы хотели удалить:"
            );
            User user = userService.getUserById(userId);
            System.out.println(mapUserToString(user));
            boolean agreement = getAgreement(
                    "Информация об этом пользователе будет удалена."
            );

            if (! agreement)
                return;

            userService.markAsDeleted(userId);
        }else{
            boolean agreement = getAgreement(
                    "Информация о вас будет удалена из базы данных."
            );

            if (! agreement)
                return;

            userService.markAsDeleted(currentUser.getId());
        }
    }

    private void cleanDeleted(){
        if (currentUser.getRole() != UserRole.ADMIN){
            System.out.println("Чистить базу может только администратор!");
            return;
        }

        boolean agreement = getAgreement(
                "Удаление всех почеменных пользователей из базы." +
                        "\nИнформацию о них можно увидеть из главного меню.");

        if (! agreement){
            return;
        }

        System.out.println("Было удалено из базы:" + userService.cleanDeleted());
    }


    private void runMainSearchingPage(){
        if(currentUser.getRole() != UserRole.ADMIN)
            throw new IllegalArgumentException(
                    "Просмотривать пользователей может только администратор!"
            );

        String message = """
                Выберите, что хотите сделать:
                1. Найти пользователя по индентификатору
                2. Найти пользователя по адресу эл. почты
                3. Найти пользователя по номеру телефона
                4. Найти всех пользователей по роли
                5. Найти всех удалённых пользоваталей
                6. Выйти в главное меню.""";

        while(true){

            int choice = InputHandler.getIntFromClient(message);

            if (choice == 6)
                return;

            if (choice < 1 || choice > 6){
                System.out.println("Такой кнопки нет в меню! Попробуйте снова");
                continue;
            }

            List<User> users = null;
            User user = null;

            switch (choice){
                case 1 -> user = userService.getUserById(
                               InputHandler.getIdFromClient("Введите айди пользователя")
                       );
                case 2 -> user = userService.getUserByEmail(
                                InputHandler.getEmailFromClient()
                );
                case 3 -> user = userService.getUserByPhoneNumber(
                                InputHandler.getPhoneNumberFromClient()
                        );
                case 4 -> users = userService.getAllByRole(
                                InputHandler.getUserRoleFromClient()
                        );
                case 5 -> users =userService.getDeletedUsers();
            }

            System.out.println("Результат:");

            if (user != null){
                System.out.println(mapUserToString(user));
                break;
            }

            if (users != null && ! users.isEmpty()){
                int i = 1;
                for (User u : users){
                    System.out.println(i++ + ". " + mapUserToString(u));
                }
                break;
            }

            System.out.println("По вашему запросу ничего не найдено! Попробуйте снова");
        }
    }

    protected static String mapUserToString(User user){
        return "{type: User, id: %d, name: %s, %s, password: restricted, role: %s}".formatted(
                user.getId().getValue(), user.getName(), user.getContactInfo(), user.getRole()
        );
    }

    private boolean getAgreement(String welcomeMessage){
        System.out.println(welcomeMessage);
        return InputHandler.getBooleanFromClient("Это действие нельзя будет отменить." +"\n"+
                "Вы уверены, что хотите его совершить?(д/н)"
        );
    }
}
