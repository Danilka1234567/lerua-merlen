package controller;

import controller.shared.InputHandler;
import model.entity.Warehouse;
import model.enums.UserRole;
import model.service.WarehouseService;
import model.vo.Id;


import java.util.List;

public class WarehouseController implements Runnable{

    private final WarehouseService warehouseService;
    private final static String WELCOME_MESSAGE = """
            Вы вошли в панель управления скаладами. Выберите, что хотите сделать:
            1. Создать новый склад
            2. Обновить информацию о существующем складе
            3. Пометить склад как удалённый
            4. Удалить помеченные склады
            5. Выполнить поиск по складам
            6. Выйти в главное меню
            """;

    private UserRole userRole;

    public WarehouseController(UserRole currentUserRole, WarehouseService warehouseService){
        this.warehouseService = warehouseService;
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


            switch(choice){
                case 1 -> runCreatingPage();
                case 2 -> runUpdatingPage();
                case 3 -> runMarkAsDeletedPage();
                case 4 -> runCleanDeletedPage();
                case 5 -> runSearchingMainPage();
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
        warehouseService.create(InputHandler.getWarehouseFromClient());
    }

    private void runUpdatingPage(){
        warehouseService.updateInfo(
                InputHandler.getContactInfoFromClient(),
                InputHandler.getFullAddressFromClient(),
                InputHandler.getIntFromClient("Введите вместимость склада:"),
                InputHandler.getIdFromClient("Введите айди склада:")
        );
    }

    private void runMarkAsDeletedPage(){
        Id warehouseId = InputHandler.getIdFromClient(
                "Введите индентификационный номер склада:"
        );

        Warehouse warehouse = warehouseService.getById(warehouseId);
        System.out.println("Информация о складе:");
        System.out.println(mapWarehouseToString(warehouse));
        boolean agreement = InputHandler.getBooleanFromClient(
                "Вы уверены, что хотите удалить её безвовратно?(д/н)"
        );

        if (! agreement)
            return;

        warehouseService.markAsDeleted(warehouseId);
    }


    private void runCleanDeletedPage(){
        boolean agreement = InputHandler.getBooleanFromClient(
                "Вы уверены что хотите удалить все помеченные склады из базы данных(д/н)?" + '\n'+
                        "Информацию о них можно увидеть из главного меню!"
        );

        if (! agreement)
            return;

        System.out.println(warehouseService.cleanDeleted() + " - было удалено складов");
    }



    private void runSearchingMainPage(){
        String message = """
            Выберите, что хотите сделать:
            1. Найти склад по индентификационному номеру
            2. Найти склад по электронной почте
            3. Найти склад по номеру телефона
            4. Найти склад по адресу
            5. Получить список складов внутри указанной страны
            6. Получить список складов внутри указанного региона
            7. Получить спиок складов внутри указанного города
            8. Получить спиоск удалёных из системы складов
            9. Выйти в главное меню
            """;
        while (true){
            Warehouse warehouse = null;
            List<Warehouse> warehouses = null;

            int choice = InputHandler.getIntFromClient(message);

            if (choice == 9)
                return;

            if (choice < 1 || choice > 9){
                System.out.println("Неверный индекс. Попробуйте снова");
                continue;
            }


            switch(choice){
                case 1 -> warehouse = warehouseService.getById(
                        InputHandler.getIdFromClient("Введите инд. номер склада:"));
                case 2 -> warehouse = warehouseService.getByEmail(
                        InputHandler.getEmailFromClient());
                case 3 -> warehouse = warehouseService.getByPhoneNumber(
                        InputHandler.getPhoneNumberFromClient()
                );
                case 4 -> warehouse = warehouseService.getByFullAddress(
                        InputHandler.getFullAddressFromClient()
                );
                case 5 -> warehouses = warehouseService.getAllByCountry(
                        InputHandler.getStringFromClient("Введите нужную страну:")
                );
                case 6 -> warehouses = warehouseService.getAllByRegion(
                        InputHandler.getStringFromClient("Введите нужный регион:")
                );
                case 7 -> warehouses = warehouseService.getAllByCity(
                        InputHandler.getStringFromClient("Введите нужный город:")
                );
                case 8 -> warehouses = warehouseService.getDeleted();
            }

            System.out.println("Результат:");

            if (warehouse != null){
                System.out.println(mapWarehouseToString(warehouse));
                break;
            }

            if (warehouses != null &&  ! warehouses.isEmpty()){
                int i = 1;
                for (Warehouse w : warehouses){
                    System.out.println(i++ + "." + mapWarehouseToString(w));
                }
                break;
            }

            System.out.println("По вашему запросу ничего не найдено! Попробуйте ещё раз");
        }
    }

    protected static String mapWarehouseToString(Warehouse warehouse){
        return "{type: warehouse, id: %d, %s, %s, capacity: %d}".formatted(
                warehouse.getId().getValue(), warehouse.getContactInfo(),
                warehouse.getFullAddress(), warehouse.getCapacity()
        );
    }
}