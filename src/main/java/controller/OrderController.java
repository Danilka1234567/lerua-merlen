package controller;

import controller.shared.InputHandler;
import model.entity.Order;
import model.entity.User;
import model.enums.UserRole;
import model.service.OrderService;
import model.vo.Id;

import java.util.List;

public class OrderController implements Runnable{

    private final OrderService orderService;
    private User currentUser;

    public OrderController(User currentUser, OrderService orderService) {
        this.orderService = orderService;
        this.currentUser = currentUser;
    }

    @Override
    public void run() {

        if (currentUser == null)
            throw new IllegalArgumentException(
                    "Текущий пользователь должен быть определён до запуска!"
            );

        if (currentUser.getRole() == UserRole.ADMIN){
            adminRun();
        }else{
            defaultRun();
        }
    }

    public void setCurrentUser(User currentUser){

        if(currentUser == null)
            throw new IllegalArgumentException(
                    "Текуший пользователь не может быть установлен пустым!"
            );
        this.currentUser = currentUser;
    }

    private void defaultRun(){
        String message = """
                Выберите, что вы хотите сделать:
                1. Создать заказ на своё имя
                2. Посмотреть свои заказы
                3. Выйти в меню""";

        while(true){

            int choice = InputHandler.getIntFromClient(
                    message
            );

            if (choice == 3)
                return;

            if (choice < 1 || choice > 3){
                System.out.println("Такого номера нет в списке! Попробуйте снова");
                continue;
            }

            switch (choice){
                case 1 -> runCreationPage();
                case 2 -> runSearchCurrentUserOrdersPage();
            }
        }
    }

    private void runSearchCurrentUserOrdersPage(){

        List<Order> orders = orderService.getOrdersByProductId(currentUser.getId());

        if (orders.isEmpty()){
            System.out.println("У вас нет актуальных заказов");
            return;
        }

        int i = 1;
        for (Order order : orders){
            System.out.println(i++ + ". " + mapOrderToString(order));
        }
    }

    private void adminRun(){
        String message = """
                Выберите, что вы хотите сделать:
                1. Создать заказ на своё имя
                2. Поменять информацию о заказе
                3. Пометить заказ как удалённый
                4. Удалить помеченные заказы
                5. Выполнить поиск по заказам
                6. Выйти в меню""";

        while(true){

            int choice = InputHandler.getIntFromClient(message);

            if (choice == 7)
                return;

            if (choice < 1 || choice > 7){
                System.out.println("Такого номера нет в меню! Попробуйте снова");
                continue;
            }

            switch(choice){
                case 1 -> runCreationPage();
                case 2 -> runUpdatePage();
                case 3 -> runMarkingAsDeletedPage();
                case 4 -> runCleaningDeletedPage();
                case 5 -> runAdminSearchPage();
            }
        }
    }

    private void runCreationPage(){
        orderService.createOrder(InputHandler.getOrderFromClient(currentUser.getId()));
    }

    private void runUpdatePage(){
        if (currentUser.getRole() != UserRole.ADMIN){
            throw new IllegalArgumentException(
                    "Обновить информацию о заказе может только администратор!"
            );
        }

        orderService.updateOrderInfo(
                InputHandler.getIntFromClient("Укажите новый срок доставки:"),
                InputHandler.getFullAddressFromClient(),
                InputHandler.getIdFromClient("Введите айди заказа:")
        );
    }

    private void runMarkingAsDeletedPage(){
        if (currentUser.getRole() != UserRole.ADMIN){
            throw new IllegalArgumentException(
                    "Пометить заказ удалёным может только администратор!"
            );
        }

        Id orderId = InputHandler.getIdFromClient(
                "Введите айди заказа:"
        );

        Order order = orderService.getOrderById(orderId);

        System.out.println("Информация о заказе:");
        System.out.println(mapOrderToString(order));
        boolean agreement = InputHandler.getBooleanFromClient(
                "Согласны ли вы безвозвратно удалить этот заказ?(д/н)"
        );

        if (! agreement)
            return;

        orderService.markAsDeleted(orderId);
    }


    private void runCleaningDeletedPage(){

        if (currentUser.getRole() != UserRole.ADMIN)
            throw new IllegalArgumentException(
                    "Очищать базу данных может только администратор!"
            );

        boolean agreement = InputHandler.getBooleanFromClient(
          "Согласны ли вы безврозвратно очистить базу от помеченных заказов?(д/н)\n"+
           "(посмотреть информацию о них можно через главное меню)"
        );

        if (! agreement)
            return;

        orderService.cleanDeleted();
    }

    private void runAdminSearchPage(){
        String message = """
                Выберите, что хотите сделать:
                1. Найти заказ по айди
                2. Получить список заказов по айди пользователя
                3. Получить список заказов по айди продукта
                4. Получить 5 самый дорогих удалёных заказов
                5. Выйти в меню""";

        while(true){

            int choice = InputHandler.getIntFromClient(message);

            if (choice == 5)
                return;

            if (choice < 1 || choice > 5){
                System.out.println("Такого номера нет в меню! Попробуйте снова");
                continue;
            }

            Order order = null;
            List<Order> orders = null;

            switch (choice){
                case 1 -> order = orderService.getOrderById(
                                InputHandler.getIdFromClient("Введите айди заказа:")
                );
                case 2 -> orders = orderService.getOrdersByUserId(
                                InputHandler.getIdFromClient("Введите айди пользователя")
                );
                case 3 -> orders = orderService.getOrdersByProductId(
                                InputHandler.getIdFromClient("Введите айди товара:")
                );
                case 4 -> orders = orderService.getDeletedOrders();
            }

            if (order != null){
                System.out.println(mapOrderToString(order));
                break;
            }

            if (orders != null && ! orders.isEmpty()){
                int i = 1;
                for (Order o : orders){
                    System.out.println(i++ + ". " + mapOrderToString(o));
                }
                break;
            }

            System.out.println("По вашему запросу ничего не найдено! Попробуйте снова");
        }
    }

    protected static String mapOrderToString(Order order){
        return "{type: Заказ, id: %d, user: %s, product: %s, %s, deliveryPeriod: %d}".formatted(
                order.getId().getValue(), UserController.mapUserToString(order.getUser()),
                ProductController.mapProductToString(order.getProduct()) ,
                order.getDeliveryAddress(), order.getDeliveryPeriod()
        );
    }
}
