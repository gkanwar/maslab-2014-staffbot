package state_machine;

public abstract class State {
    StateMachine machine;
    long startTime;
    
    public State(StateMachine sm) {
        this.machine = sm;
        startTime = System.currentTimeMillis();
    }
    
    public State step() {
        State next = this.transition();
        next.run();
        return next;
        
    }
    
    protected abstract State transition();
    protected abstract void run();
}
