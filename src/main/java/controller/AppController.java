package controller;

import controller.shared.InputHandler;
import model.entity.User;
import model.service.SessionService;

public class AppController implements Runnable{

    private final AuthController authController;
    private final ManufacturerController manufacturerController;
    private final OrderController orderController;
    private final ProductController productController;
    private final UserController userController;
    private final WarehouseController warehouseController;
    private final SessionService sessionService;

    private User currentUser = null;
    private boolean userWasChanged = false;
    private final static String WELCOME_MESSAGE = "Вы зашли в пприложения интернет магазина инструментов и товаров для ремонта.";
    private final static String TEXT_DIVIDER = "~".repeat(75) + "|";
    public AppController(AuthController authController, ManufacturerController manufacturerController,
                         OrderController orderController, ProductController productController,
                         UserController userController, SessionService sessionService,
                         WarehouseController warehouseController) {
        this.authController = authController;
        this.manufacturerController = manufacturerController;
        this.orderController = orderController;
        this.productController = productController;
        this.userController = userController;
        this.sessionService = sessionService;
        this.warehouseController = warehouseController;
    }

    @Override
    public void run() {

        while(true){
            try{
                if (currentUser == null || ! sessionService.isLogged(currentUser.getId())){
                    System.out.println(WELCOME_MESSAGE);
                    System.out.println(TEXT_DIVIDER);
                    authController.run();


                    currentUser = authController.getCurrentUser();
                    if (currentUser == null)
                        return;

                    userWasChanged = true;
                }

                if (userWasChanged){
                    manufacturerController.setUserRole(currentUser.getRole());
                    orderController.setCurrentUser(currentUser);
                    productController.setCurrentUser(currentUser);
                    userController.setCurrentUser(currentUser);
                    warehouseController.setUserRole(currentUser.getRole());
                    userWasChanged = false;
                }

                String message = """
               Вы успешно зашли в систему!
               Выберите, куда бы вы хотели переместиться
               1. Страница товаров
               2. Страница заказов
               3. Страница производителей
               4. Страница складов
               5. Страница пользователей
               6. Закрыть приложение""";

                int choice = InputHandler.getIntFromClient(message);

                if (choice == 6)
                    return;

                if (choice < 1 || choice > 6){
                    System.out.println("Такого номера нет в списке! Попробуйте снова");
                    continue;
                }

                System.out.println(TEXT_DIVIDER);
                switch(choice){
                    case 1 -> productController.run();
                    case 2 -> orderController.run();
                    case 3 -> manufacturerController.run();
                    case 4 -> warehouseController.run();
                    case 5 -> userController.run();
                }
                System.out.println(TEXT_DIVIDER);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
