package controller;

import controller.shared.InputHandler;
import model.entity.Manufacturer;
import model.enums.UserRole;
import model.service.ManufacturerService;
import model.vo.Id;

import java.util.List;

public class ManufacturerController implements Runnable{

    private final ManufacturerService manufacturerService;
    private final static String WELCOME_MESSAGE = """
            Выберите, что хотите сделать:
            1. Создать нового производителя
            2. Обновить информацию о производителе
            3. Пометить производителя как удалённого
            4. Удалить помеченных производителей
            5. Выполнить поиск по производителям
            6. Выйти в главное меню""";

    private UserRole userRole;
    public ManufacturerController(UserRole currentUserRole, ManufacturerService manufacturerService) {
        this.userRole = currentUserRole;
        this.manufacturerService = manufacturerService;
    }

    @Override
    public void run() {

        if (userRole == null)
            throw new IllegalArgumentException(
                    "Роль пользователя должна быть определена до запуска страницы"
            );

        if (userRole != UserRole.ADMIN){
            throw new IllegalArgumentException(
                    "Взаимодействовать с производителями может только администратор!"
            );
        }

        while(true){
            int choice = InputHandler.getIntFromClient(WELCOME_MESSAGE);

            if (choice == 6)
                return;

            if (choice < 1 || choice > 6){
                System.out.println("Такой кнопки нет в меню! Попробуйте снова");
                continue;
            }

            switch (choice){
                case 1 -> runCreatingPage();
                case 2 -> runUpdatingPage();
                case 3 -> runMarkingAsDeletedPage();
                case 4 -> runCleaningDeletedPage();
                case 5 -> runMainSearchPage();
            }
        }
    }

    public void setUserRole(UserRole userRole){
        if (userRole == null)
            throw new IllegalArgumentException(
                    "Роль пользователя не может быть пустой!"
            );
        this.userRole = userRole;
    }

    private void runCreatingPage(){
        manufacturerService.create(InputHandler.getManufacturerFromClient());
    }

    private void runUpdatingPage(){
        manufacturerService.updateInfo(
                InputHandler.getContactInfoFromClient(),
                InputHandler.getFullAddressFromClient(),
                InputHandler.getStringFromClient("Введите специализацию:"),
                InputHandler.getIdFromClient("Введите айди производителя:")
        );
    }

    private void runMarkingAsDeletedPage(){
        Id id = InputHandler.getIdFromClient("Введите айди нужного производителя:");
        Manufacturer manufacturer = manufacturerService.getById(id);
        System.out.println("Информация о производителе:");
        System.out.println(mapManufacturerToString(manufacturer));
        boolean agreement = InputHandler.getBooleanFromClient(
                "Вы точно хотите безврозвратно удалить её?(д/н)"
        );

        if (! agreement){
            return;
        }

        manufacturerService.markAsDeleted(id);
    }

    private void runCleaningDeletedPage(){
        boolean agreement = InputHandler.getBooleanFromClient(
                "Вы точно хотите очистить базу данных от помеченных производителей?(д/н)"
        );

        if (! agreement){
            return;
        }

        System.out.println("Было удалено:" + manufacturerService.cleanDeleted());
    }

    protected static String mapManufacturerToString(Manufacturer manufacturer){
        return "{type: manufacturer, id: %d, name: %s, %s, %s, specialization: %s}".formatted(
                manufacturer.getId().getValue(), manufacturer.getName(), manufacturer.getContactInfo(),
                manufacturer.getFullAddress(), manufacturer.getSpecialization()
        );
    }

    private void runMainSearchPage(){
        String message = """
                Выберите, что вы хотите сделать:
                1. Найти произвоедителя по индентификационному номеру
                2. Найти производителя по адресу эл. почты
                3. Найти производителя по номеру телефона
                4. Найти производителя по точному адресу
                5. Найти всех производителей в указанной стране
                6. Найти всех производителей в указанном регионе
                7. Найти всех производителей в указанном городе
                8. Найти всех производтилей по специализации
                9. Найти всех удалённых производителей
                10. Выйти в главное меню
                """;


        while(true){
            Manufacturer manufacturer = null;
            List<Manufacturer> manufacturers = null;

            int choice = InputHandler.getIntFromClient(message);

            if (choice == 10)
                return;

            if (choice < 1 || choice > 10){
                System.out.println("Неверный индекс! Попробуйте снова");
                continue;
            }

            switch(choice){
                case 1 -> manufacturer = manufacturerService.getById(
                        InputHandler.getIdFromClient("Введите айди производителя:")
                );
                case 2 -> manufacturer = manufacturerService.getByEmail(
                                InputHandler.getEmailFromClient()
                );
                case 3 -> manufacturer = manufacturerService.getByPhoneNumber(
                                InputHandler.getPhoneNumberFromClient()
                );
                case 4 -> manufacturer = manufacturerService.getByFullAddress(
                                InputHandler.getFullAddressFromClient()
                );
                case 5 -> manufacturers =
                        manufacturerService.getAllByCountry(
                                InputHandler.getStringFromClient("Введите страну:")
                );
                case 6 -> manufacturers = manufacturerService.getAllByRegion(
                                InputHandler.getStringFromClient("Введите регион:")
                );
                case 7 -> manufacturers = manufacturerService.getAllByCity(
                                InputHandler.getStringFromClient("Введите город:")
                );
                case 8 -> manufacturers = manufacturerService.getAllBySpecialization(
                                InputHandler.getStringFromClient("Введите специализацию")
                );
                case 9 -> manufacturers = manufacturerService.getAllDeleted();
            }

            System.out.println("Результат:");

            if (manufacturer != null){
                System.out.println(mapManufacturerToString(manufacturer));
                break;
            }

            if (manufacturers != null && ! manufacturers.isEmpty()){
                int i = 1;
                for (Manufacturer m : manufacturers){
                    System.out.println(i++ + "." + " " + mapManufacturerToString(m));
                }
                break;
            }

            System.out.println("По вашему запросу ничего не найдено! Попробуйте снова");
        }
    }

}
