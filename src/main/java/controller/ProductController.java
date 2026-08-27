package controller;

import controller.shared.InputHandler;
import model.entity.Product;
import model.entity.User;
import model.enums.UserRole;
import model.service.ProductService;
import model.vo.Id;

import java.util.List;

public class ProductController implements Runnable{

    private  User currentUser;
    private final ProductService productService;

    public ProductController(User currentUser, ProductService productService){
        this.currentUser = currentUser;
        this.productService = productService;
    }

    @Override
    public void run() {
        if (currentUser == null)
            throw new IllegalArgumentException(
                    "Текущий пользователь должен быть инициализирован до запуска!"
            );

        if (currentUser.getRole() == UserRole.ADMIN){
            runAdmin();
        }else {
            runDefaultSearchingPage();
        }
    }

    public void setCurrentUser(User currentUser){
        if (currentUser == null)
            throw new IllegalArgumentException(
                    "Текущий пользователь не может быть установлен как null!"
            );
        this.currentUser = currentUser;
    }

    private void runAdmin(){
        String message = """
                Введите, чтобы вы хотели сделать:
                1. Создать товар
                2. Обновить информацию о товаре
                3. Пометить товар как удалённый
                4. Очистить от удалённых товаров
                5. Выполнить поиск среди товаров
                6. Выйти в меню""";

        while(true){

            int choice = InputHandler.getIntFromClient(message);

            if (choice == 6)
                return;

            if (choice < 1 || choice > 6){
                System.out.println("Такого номера нет в списке! Попробуйте снова");
                continue;
            }

            switch (choice){
                case 1 -> runCreationPage();
                case 2 -> runUpdateInfoPage();
                case 3 -> runMarkingAsDeletedPage();
                case 4 -> runCleanDeletedPage();
                case 5 -> runAdminSearchingPage();
            }
        }
    }

    private void runCreationPage(){
        if (currentUser.getRole() != UserRole.ADMIN){
            throw new IllegalArgumentException(
                    "Создавать товары может только администратор!"
            );
        }

        productService.create(InputHandler.getProductFromClient());
    }

    private void runUpdateInfoPage(){
        if (currentUser.getRole() != UserRole.ADMIN)
            throw new IllegalArgumentException(
                    "Обновлять информацию о товарах может только администратор!"
            );

        System.out.println("Если вы хотите оставить какое-то поле неизменным - вводите прежденее значение");
        productService.updateInfo(
                InputHandler.getIdFromClient("Введите айди склада:"),
                InputHandler.getIdFromClient("Введите айди производителя:"),
                InputHandler.getStringFromClient("Введите наименование товара:"),
                InputHandler.getBigDecimalFromClient(
                        "Введите цену за товар:",
                        15,
                        2
                ),
                InputHandler.getBigDecimalFromClient(
                        "Введите скидку:",
                        3,
                        2
                ),
                InputHandler.getIdFromClient("Введите айди продукта:")
        );
    }

    private void runMarkingAsDeletedPage(){
        if (currentUser.getRole() != UserRole.ADMIN){
            throw new IllegalArgumentException(
                    "Помечать товары как удалённые может только администратор!"
            );
        }

        Id productId = InputHandler.getIdFromClient("Введите айди товара:");

        Product product = productService.getById(productId);
        System.out.println(mapProductToString(product));
        boolean agreement = InputHandler.getBooleanFromClient(
                "Вы уверены, что хотите безврозвратно удалить этот товар?(д/н)"
        );

        if (! agreement)
            return;

        productService.markAsDeleted(productId);
    }

    private void runCleanDeletedPage(){
        boolean agreement = InputHandler.getBooleanFromClient(
                "Вы уверены, что хотите безвозвратно очистить базу данных от помеченных товаров?(д/н)"
        );

        if (! agreement)
            return;

        System.out.println(productService.cleanDeleted() + "- было удалено из бд");
    }


    private void runAdminSearchingPage(){
        if (currentUser.getRole() != UserRole.ADMIN)
            throw new IllegalArgumentException(
                    "Только для админа!"
            );

        String message = """
                Выбирете, что хотите сделать:
                1. Найти товар по айди
                2. Получить список товар по айди производителя
                3. Получить спиcок товар по айди склада
                4. Получить список удалённых товаров
                5. Получить список товаров по имени
                6. Выйти в меню
                """;

        while(true){

            int choice = InputHandler.getIntFromClient(message);

            if (choice == 6)
                return;

            if (choice < 1 || choice > 6){
                System.out.println("Такого номера нет в списке! Попробуйте снова");
                continue;
            }

            Product product = null;
            List<Product> products = null;

            switch (choice){
                case 1 -> product = productService.getById(
                        InputHandler.getIdFromClient("Введите айди товара:")
                );
                case 2 -> products = productService.getAllByManufacturerId(
                        InputHandler.getIdFromClient("Введите айди производителя:"
                ));
                case 3 -> products = productService.getAllByWarehouseId(
                            InputHandler.getIdFromClient("Введите айди склада:")
                );
                case 4 -> products = productService.getDeleted();
                case 5 -> products = productService.getAllLikeName(
                        InputHandler.getStringFromClient("Введите наименование товара:")
                );
            }

            if (product != null){
                System.out.println(mapProductToString(product));
                break;
            }

            if ( products != null && ! products.isEmpty()){
                int i = 1;
                for (Product p : products){
                    System.out.println(i++ + ". " + mapProductToString(p));
                }
                break;
            }

            System.out.println("По вашему запросу ничего не найдено! Попробуйте снова");
        }
    }

    private void runDefaultSearchingPage(){
        String message = """
                Выбирете, что хотите сделать:
                1. Найти товар по айди
                2. Получить список товаров по айди производителя
                3. Получить список товаров по имени
                4. Выйти в меню
                """;

        while(true){
            int choice = InputHandler.getIntFromClient(message);

            if (choice == 4)
                return;

            if (choice < 1 || choice > 4){
                System.out.println("Такого числа нет в списке!Попробуйте снова");
                continue;
            }

            Product product = null;
            List<Product> products = null;

            switch(choice){
                case 1 -> product = productService.getById(
                        InputHandler.getIdFromClient("Введите айди товара:")
                );
                case 2 -> products = productService.getAllByManufacturerId(
                        InputHandler.getIdFromClient("Введите айди производителя:")
                );
                case 3 -> products = productService.getAllLikeName(
                        InputHandler.getStringFromClient("Введите наименование товара:")
                );
            }

            if (product != null){
                System.out.println(mapProductToString(product));
                break;
            }

            if ( products != null && ! products.isEmpty()){
                int i = 1;
                for (Product p : products){
                    System.out.println(i++ + ". " + mapProductToString(p));
                }
                break;
            }

            System.out.println("По вашему запросу ничего не найдено! Попробуйте снова");
        }
    }

    protected static String mapProductToString(Product product){
        return ("{type: Товар, id: %d, name: %s, warehouse: %s," +
                "manufacturer: %s, price: %.2f, discount: %.2f}").formatted(
                        product.getId().getValue(), product.getName(), WarehouseController.mapWarehouseToString(product.getWarehouse()),
                    ManufacturerController.mapManufacturerToString(product.getManufacturer()), product.getPrice(), product.getDiscount()
        );
    }
}
