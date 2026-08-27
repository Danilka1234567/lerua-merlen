package controller.shared;

import controller.exception.StoppedByUserException;
import model.entity.*;
import model.enums.UserRole;
import model.vo.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.function.Function;

public class InputHandler {

    private final static Scanner scanner = new Scanner(System.in);
    private final static String TRY_AGAIN_MESSAGE = "Попробуйте ввести снова." +
            " Если хотите прекратить ввод - введите 'стоп'";
    private final static String ASK_FOR_EMAIL_MESSAGE = "Введите адресс электронной почты:";
    private final static String ASK_FOR_PHONE_NUMBER_MESSAGE = "Введите номер телефона:";
    private final static String ASK_FOR_NAME = "Введите наименование:";
    private final static String ASK_FOR_PASSWORD = "Введите пароль:";
    private final static String ASK_FOR_COUNTRY = "Введите страну:";
    private final static String ASK_FOR_REGION = "Введите регион:";
    private final static String ASK_FOR_CITY = "Введите город:";

    public static String getStringFromClient(String welcomeMessage){
        System.out.println(welcomeMessage);
        return scanner.nextLine();
    }

    public static int getIntFromClient(String welcomeMessage){
        while(true){
            String input = getStringFromClient(welcomeMessage);
            if (input.equals("стоп")){
                throw new StoppedByUserException();
            }

            try{
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат числа. " + TRY_AGAIN_MESSAGE + "\n");
            }
        }
    }

    public static boolean getBooleanFromClient(String welcomeMessage){
        String input = getStringFromClient(welcomeMessage);
        if (input.equals("стоп")){
            throw new StoppedByUserException();
        }

        return input.toLowerCase().trim().equals("д");
    }

    private static BaseVO<String> getStringVoFromClient(String welcomeMessage,
                                                       Function<String, BaseVO<String>> voFactory){
        while(true){
            String input = getStringFromClient(welcomeMessage);
            if (input.equals("стоп")){
                throw new StoppedByUserException();
            }

            try{
                return voFactory.apply(input);
            }catch (IllegalArgumentException e){
                System.out.println("Неверный формат ввода." + TRY_AGAIN_MESSAGE + "\n" +
                        "Все нарушения:/\n" + e.getMessage() + "\n");
            }
        }
    }

    public static Email getEmailFromClient(){
        return (Email) getStringVoFromClient(ASK_FOR_EMAIL_MESSAGE, Email::new);
    }

    public static Password getPasswordFromClient(){
        return (Password) getStringVoFromClient(ASK_FOR_PASSWORD, Password::new);
    }

    public static FullAddress getFullAddressFromClient(){
        return new FullAddress(
                getStringFromClient(ASK_FOR_COUNTRY),
                getStringFromClient(ASK_FOR_REGION),
                getStringFromClient(ASK_FOR_CITY),
                (StreetAddress) getStringVoFromClient(
                        "Введите улицу и номер дома", StreetAddress::new
                )
        );
    }

    public static PhoneNumber getPhoneNumberFromClient(){
        return (PhoneNumber)   getStringVoFromClient(ASK_FOR_PHONE_NUMBER_MESSAGE, PhoneNumber::new);
    }

    public static Id getIdFromClient(String welcomeMessage){
            while(true){
                System.out.println(welcomeMessage);
                String input = getStringFromClient(welcomeMessage);
                if (input.equals("стоп"))
                    throw new StoppedByUserException();

                try{
                    return new Id(Long.parseLong(input));
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат числа. " + TRY_AGAIN_MESSAGE);
                } catch (IllegalArgumentException e){
                    System.out.println("Неверный формат айди. " + TRY_AGAIN_MESSAGE + "\n" +
                            "Все нарушения:\n" + e.getMessage() + "\n");
                }
            }
    }

    public static ContactInfo getContactInfoFromClient(){

        PhoneNumber phoneNumber;
        try{
             phoneNumber = getPhoneNumberFromClient();
        }catch (StoppedByUserException e){
            phoneNumber = null;
        }

        return new ContactInfo(
                phoneNumber,
                getEmailFromClient()
        );
    }

    public static BigDecimal getBigDecimalFromClient(String welcomeMessage, int precision, int scale){
        while(true){

            String input = getStringFromClient(welcomeMessage);

            if (input.equals("стоп"))
                throw new StoppedByUserException();

            input = input.replace(",", ".");

            try{
                BigDecimal bigDecimal = new BigDecimal(input);

                bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);
                if (bigDecimal.precision() > precision)
                    throw new IllegalArgumentException(
                            "Данное число должно содержать не более %d знаков".formatted(
                                    precision
                            )
                    );

                return bigDecimal;
            } catch (NumberFormatException e) {
                System.out.println("Не число");
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static Product getProductFromClient(){
        System.out.println("Создание нового товара...");
        return Product.createNew(
                getIdFromClient("Введите айди склада:"),
                getIdFromClient("Введите айди производителя:"),
                getStringFromClient(ASK_FOR_NAME),
                getBigDecimalFromClient(
                        "Введите цену (максимум 15 знаков, 2 после запятой):",
                        15, 2
                ),
                getBigDecimalFromClient(
                        "Введите скидку(число от 0 до 1, 2 знака после запятой):",
                        3, 2)
                );
    }

    public static UserRole getUserRoleFromClient(){
        while (true){

            String input = getStringFromClient("Введите роль пользователя:");

            try{
                return UserRole.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Не существующая роль " + TRY_AGAIN_MESSAGE + "\n");
            }
        }
    }

    public static User getUserEntityFromClient(){
        return User.createNew(
                getContactInfoFromClient(),
                getStringFromClient("Введите имя пользователя: "),
                getPasswordFromClient(),
                getUserRoleFromClient()
        );
    }

    public static Warehouse getWarehouseFromClient(){
        System.out.println("Создание нового склада...");
        return Warehouse.createNew(
                getContactInfoFromClient(),
                getFullAddressFromClient(),
                getIntFromClient("Введите вместимость склада(в шт. товаров):")
        );
    }

    public static Manufacturer getManufacturerFromClient(){
        System.out.println("Создание нового производителя...");
        return Manufacturer.createNew(
                getContactInfoFromClient(),
                getFullAddressFromClient(),
                getStringFromClient(ASK_FOR_NAME),
                getStringFromClient("Введите специализацию производетеля:")
        );
    }

    public static Order getOrderFromClient(Id currentUserId){
        System.out.println("Создание нового заказа...");
        return Order.createNew(
                currentUserId,
                getIdFromClient("Введите артикул товара"),
                Integer.MAX_VALUE,
                getFullAddressFromClient()
        );
    }
}
