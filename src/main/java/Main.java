import controller.*;
import infrastructure.config.ConnectionManager;
import infrastructure.config.DbInitializer;
import infrastructure.repositoryImpl.*;
import model.enums.UserRole;
import model.service.*;
import model.vo.Id;

public class Main {


    public static void main(String[] args) {


        DbInitializer.init();

        ManufacturerService manufacturerService = new ManufacturerService(new ManufacturerRepositoryImpl());
        OrderService orderService = new OrderService(new OrderRepositoryImpl());
        ProductService productService = new ProductService(new ProductRepositoryImpl(), new WarehouseRepositoryImpl());
        SessionService sessionService = new SessionService(new SessionRepositoryImpl());
        UserService userService = new UserService(new UserRepositoryImpl(), sessionService);
        WarehouseService warehouseService = new WarehouseService(new WarehouseRepositoryImpl());

        AuthController authController = new AuthController(userService);
        ManufacturerController manufacturerController = new ManufacturerController(null, manufacturerService);
        OrderController orderController = new OrderController(null, orderService);
        ProductController productController = new ProductController(null, productService);
        UserController userController = new UserController(null, userService);
        WarehouseController warehouseController = new WarehouseController(null, warehouseService);

        AppController appController = new AppController(
                authController,
                manufacturerController,
                orderController,
                productController,
                userController,
                sessionService,
                warehouseController
        );

        appController.run();
    }
}
