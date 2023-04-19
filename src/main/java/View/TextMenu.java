package View;

import Model.ADT.MyDict;
import Model.ADT.MyIDict;

import java.util.Scanner;

public class TextMenu {
    private MyIDict<String, Command> commands;

    public TextMenu()
    {
        commands = new MyDict<>();
    }

    public void addCommand(Command c){
        commands.put(c.getKey(),c);
    }

    private void printMenu(){
        for(Command com : commands.values()){
            String line = String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key = scanner.nextLine();
            try {
                Command com = commands.lookUp(key);
                if (com == null) {
                    System.out.println("Invalid Option");
                    continue;
                }
                com.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
