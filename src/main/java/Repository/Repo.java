package Repository;

import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{

    int counter = 0;
    List<ProgramState> programStates = new ArrayList<>();
    private final String logFilePath;

    public Repo(ProgramState programState, String logFilePath)
    {
        this.counter++;
        programStates.add(programState);
        this.logFilePath = logFilePath;
    }

    @Override
    public void logPrgStateExec(ProgramState programState) throws Exception {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.toString());
        logFile.close();
    }

    @Override
    public List<ProgramState> getPrgList() {
        return this.programStates;
    }

    @Override
    public void setPrgList(List<ProgramState> newList) {
        this.programStates = newList;
    }

    @Override
    public void addProgram(ProgramState program) {
        this.programStates.add(program);
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.programStates;
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates = programStates;
    }
}
