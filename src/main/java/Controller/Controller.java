package Controller;

import Model.ProgramState;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepo;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    IRepo repo;
    boolean displayFlag;
    ExecutorService executor;

    public Controller(IRepo repository) {
        this.repo = repository;
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddresses, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddressesFromSymTable(Collection<Value> symTableValues){
        ///We get a list with all the addreses of the referenceValues from the symbol table
        return  symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException, Exception{
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prgState-> {
            try {
                repo.logPrgStateExec(prgState);
                display(prgState);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //RUN concurrently one step for each of the existing PrgStates

        //prepare the list of callables
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of newly created PrgStates (namely threads)
        List<ProgramState> newPrgList = executor.invokeAll(callList). stream().map(future->{ try {
            try {
                return future.get();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (ExecutionException e) {
                System.out.println(e.getMessage());
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            }
            return null;
        }).filter(p -> p!=null).collect(Collectors.toList());

        //add the newly created threads to the list of existing threads
        prgList.addAll(newPrgList);

        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        //Save the current programs in the repository
        repo.setPrgList(prgList);

    }


    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList)
    {
        return inPrgList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> prgList = removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository.

        // update the repository state
        repo.setPrgList(prgList);
    }

    public void conservativeGarbageCollector(List<ProgramState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void display(ProgramState programState)
    {
        if (displayFlag) {
            System.out.println(programState.toString());
        }
    }

    public List<ProgramState> getProgramStates() {
        return this.repo.getProgramList();
    }

    public void setProgramStates(List<ProgramState> programStates) {
        this.repo.setProgramStates(programStates);
    }

    public void oneStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrg(repo.getProgramList());
        oneStepForAllPrg(programStates);
        conservativeGarbageCollector(programStates);
        //programStates = removeCompletedPrg(repository.getProgramList());
        executor.shutdownNow();
        //repository.setProgramStates(programStates);
    }
}
