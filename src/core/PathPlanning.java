package core;

import java.util.LinkedList;

import map.Map;
import map.Point;
import map.Segment;
import state_machine.StateMachine;
import utils.Tree;
import utils.TreeNode;

public class PathPlanning {
    private static final int MAXLENGTH = 5;
	StateMachine sm;
    StateEstimator se;
    LinkedList<Point> path;

    public PathPlanning(StateMachine sm, StateEstimator se) {
        this.sm = sm;
        this.se = se;
    }

    public void step() {
    }

    public void findPath() {
    	Point goal = sm.goal;
    	Point start = new Point(se.map.bot.center.x, se.map.bot.center.y);
    	TreeNode root = new TreeNode(start);
    	Tree rrt = new Tree(root);
    	
    	Point p;	
    	TreeNode closest, newNode, goalNode;
    	Segment seg;
    	
    	
    	while (true){
    		p = se.map.randomPoint();
    		closest = root;
    		for (TreeNode node : rrt.nodes){
    			if (node.loc.distance(p) < closest.loc.distance(p)){
    				closest = node;
    			}
    		}
    		
    		seg = new Segment(closest.loc, p);
    		seg = seg.trim(MAXLENGTH);
    		
    		if (!se.map.checkSegment(seg)){
    			continue;
    		}
    		
    		newNode = new TreeNode(goal);
    		closest.addChild(newNode);
    		closest = root;
    		for (TreeNode node : rrt.nodes){
    			if (node.loc.distance(goal) < closest.loc.distance(goal)){
    				closest = node;
    			}
    		}
    		
    		seg = new Segment(closest.loc, p);
    	
    		if (!se.map.checkSegment(seg) || closest.loc.distance(goal) > MAXLENGTH){
    			continue;
    		}
    		
    		goalNode = new TreeNode(goal);
    		closest.addChild(goalNode);
    		break;
    	}
    	
    	TreeNode curNode = goalNode;
    	Boolean pathcomplete = false;
    	path = new LinkedList<Point>();
    	while (!pathcomplete){
    		path.add(0, curNode.loc);
    		curNode = curNode.parent;
    	}
    	
    }
}
