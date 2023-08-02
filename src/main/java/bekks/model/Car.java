package bekks.model;

import lombok.*;

@Setter@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private Long id;
    private String brand;
    private String model;
    private int year;
    private Color color;
    private int price;

    public Car(String brand, String model, int year, Color color, int price) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.price = price;
    }
}
