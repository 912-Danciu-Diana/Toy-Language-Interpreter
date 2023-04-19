package View;

import Controller.Controller;

import java.util.Objects;
import java.util.Scanner;

public class RunExampleCommand extends Command{
    private Controller ctr;

    public RunExampleCommand(String key, String desc, Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }

    @Override
    public void execute() {
        try
        {
            System.out.println("Do you want to display the steps?[Y/n]");
            Scanner readOption = new Scanner(System.in);
            String option = readOption.next();
            ctr.setDisplayFlag(Objects.equals(option, "Y"));
            ctr.allStep();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
