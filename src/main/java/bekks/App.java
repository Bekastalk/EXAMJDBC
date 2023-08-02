package bekks;

import bekks.config.Config;
import bekks.model.Car;
import bekks.model.Color;
import bekks.service.CarService;
import bekks.service.CarServiceImpl;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CarService carService = new CarServiceImpl();
//        Config.getConnection();
        while (true) {
            System.out.println("""
                     1 create enum
                     2 create table car
                     3 save car
                     4 get by id car
                     5 update car
                     6 delete by id
                     7 get cars by search
                     8 getCarsByPriceRange
                    """);
            switch (new Scanner(System.in).nextLine()) {
               case "1"-> {
                   carService.createEnumColor();
               }
               case "2"->{
                   carService.createTableCar();
               }
               case "3"->{
                   carService.saveCar(new Car("Audi","TT",2010, Color.RED,74123));
               }
               case "4"->{
                   System.out.println(carService.getCarById(2L));
               }
               case "5"->{
                   System.out.println(carService.updateCar(1L, new Car("Ford", "Mustang", 2019, Color.RED, 104123)));
               }
               case "6"->{
                   System.out.println(carService.deleteCar(7L));
               }
               case "7"->{
                   System.out.println(carService.getCarsBySearch("Ford", "Mustang"));
               }
               case "8"->{
                   carService.getCarsByPriceRange(20000,100000).forEach(System.out::println);
               }
            }
        }
    }
}
