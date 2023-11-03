package learn.venus.ui;

import java.util.Scanner;

public class View {

    private final Scanner console = new Scanner(System.in);
    public MenuOption displayMenuAndSelect(){
        MenuOption[] values = MenuOption.values();
        printHeader("Main Menu");
        for(int i = 0; i < values.length; i++) {
            System.out.printf("%s: %s%n", i, values[i].getTitle());
        }
        return MenuOption.EXIT;
    }

    public void printHeader(String message){
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    private String readString(String prompt){
        System.out.print(prompt);
        return console.nextLine();
    }

    private String readRequiredString(String prompt){
        String result = null;
        do {
            result = readString(prompt).trim();
            if(result.isBlank()){
                System.out.println("input is required");
            }
        } while(!result.isBlank());
        return result;
    }

    private int readInt(String prompt){
        int result = 0;
        boolean isValid = false;
        do {
            String value = readRequiredString(prompt);
            try{
                result = Integer.parseInt(value);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.println("Value must be a number.");
            }

        } while (!isValid);

        return result;
    }

    private int readInt(String prompt, int min, int max){
        int result = 0;
        do {
            result = readInt(prompt);
            if(result < min || result > max){
                System.out.printf("Value must be between %s and %s%n", min, max);
            }
        } while (result < min || result > max);

        return result;
    }

}
