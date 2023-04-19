package Repository;

import Model.ProgramState;

import java.util.List;

public interface IRepo {
    void logPrgStateExec(ProgramState programState) throws Exception;
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> newList);
    void addProgram(ProgramState program);
    List<ProgramState> getProgramList();
    void setProgramStates(List<ProgramState> programStates);
}
