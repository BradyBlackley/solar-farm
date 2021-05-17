package learn.solarfarm.ui;

import learn.solarfarm.models.MaterialType;

import java.util.Locale;
import java.util.Scanner;

public class ConsoleIO implements TextIO {

    private final Scanner console = new Scanner(System.in);

    @Override
    public void println(Object value) {
        System.out.println(value);
    }

    @Override
    public void print(Object value) {
        System.out.print(value);
    }

    @Override
    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return console.nextLine();
    }

    @Override
    public boolean readBoolean(String prompt) {
        String result = readString(prompt);
        return result.equalsIgnoreCase("y");
    }

    @Override
    public int readInt(String prompt) {
       while (true) {
           String value = readString(prompt);
           try {
               int result = Integer.parseInt(value);
               return result;
           }catch(NumberFormatException ex){
               printf("'%s' is not a valid number.%n", value);
           }
       }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if(value >= min && value <= max) {
                return value;
            }
            printf("Value must be between %s and %s.%n", min, max);
        }
    }

    public MaterialType readMaterialType(String prompt){

            while(true) {
                String value = readString(prompt).toLowerCase().trim().replace(" ", "");
                switch (value) {
                    case "multicrystallinesilicon":
                        return MaterialType.MULTICRYSTALLINE_SILICON;
                    case "monocrystallinesilicon":
                        return MaterialType.MONOCRYSTALLINE_SILICON;
                    case "amorphoussilicon":
                        return MaterialType.AMORPHOUS_SILICON;
                    case "cadmiumtelluride":
                        return MaterialType.CADMIUM_TELLURIDE;
                    case "copperindiumgalliumselenide":
                        return MaterialType.COPPER_INDIUM_GALLIUM_SELENIDE;
                    default :
                    printf("Value must be in [%s, %s, %s, %s, %s]",
                            MaterialType.MULTICRYSTALLINE_SILICON.name(),
                            MaterialType.MONOCRYSTALLINE_SILICON.name(),
                            MaterialType.AMORPHOUS_SILICON.name(),
                            MaterialType.CADMIUM_TELLURIDE.name(),
                            MaterialType.COPPER_INDIUM_GALLIUM_SELENIDE.name());
                }
            }

    }
}
