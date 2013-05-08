package core;

import java.util.ArrayList;

import map.Point;
import firmware_interfaces.DeltaInterface;

public class Delta {
    private static Delta instance;
    
    private DeltaInterface di;
    
    private long[] position = new long[3];
    private ArrayList<int[]> moves;
    
    private int heldBlock = 0;
    private boolean pneumaticOut = false;
    private boolean midMove = false;
    private boolean isZeroed = false;
    
    private Delta() {
        di = new DeltaInterface();
        di.run();
    }
    
    public static Delta getInstance() {
        if (instance == null)
            instance = new Delta();
        return instance;
    }
    
    
    public void loadData() {
        
    }
    
    public void getPosition() {
        
    }
    
    public double stepsToZ() {
        return 0.0;
    }
    
    public int[] computeSteps(double x, double y, double z) {
        return null;
    }
    
    public void goToPosition(double x, double y, double z) {
        double[] deltas = {0,0,0};
        Point pos = new Point(x,y);
        int[] steps= {0,0,0};
        for (int i = 0; i< deltas.length; i++){
        	double d = pos.distance(Config.DELTA_POSITION[i]);
        	deltas[i] = Math.sqrt(Math.pow(Config.DELTA_STEP_MAX, 2)-Math.pow(d, 2));
        	steps[i] = (int) Math.round(deltas[i]*Config.DELTA_MICROSTEPS_PER_CM);
        }
        
        moves.add(steps);
     
    }

	public void step() {
		midMove = di.ready;
		if (!moves.isEmpty() && !midMove){
			this.move(moves.get(0));
			moves.remove(0);
		}
	}
	
	public void move(int[] steps) {
	    midMove = true;
	    di.move(steps);
	    
	    for (int i = 0; i < steps.length; i++) {
	        position[i] += steps[i];
	        if (position[i] < 0)
                position[i] = 0;
	        if (position[i] > Config.DELTA_STEP_MAX)
                    position[i] = Config.DELTA_STEP_MAX;
	    }
	}
	
	public void topOut() {
	    isZeroed = true;
	    move(new int[] {9999,9999,9999});
	}
	
	public void firePneumatic() {
	}
	
	public void retractPneumatic() {
	}
	
	public void collectBlock() {	    
	}
	
	public void placeBlock() {
	    
	}

	public void placeNextBlock() {
	}

	public void grabNextBlock() {
	}

	public void PutBlockInBin() {
	}
	public static void main(String[] args) throws Exception {
    	Delta main = new Delta();
        System.out.println("Started");
        while(true){
        	main.goToPosition(3, 3, 0);
        	Thread.sleep(5000);
        	main.goToPosition(0, 0, 0);
        	Thread.sleep(5000);
        }
 	}
}
