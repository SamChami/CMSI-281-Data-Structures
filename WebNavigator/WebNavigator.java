//package web_nav;


import java.util.LinkedList;
import java.util.Scanner;

public class WebNavigator {

    // Fields
    private String current; // Tracks currently visited site
    private LinkedList<String> backwardHistory;
    private LinkedList<String> forwardHistory;

    // Constructor
    WebNavigator () {
    	backwardHistory = new LinkedList<String>();
    	forwardHistory = new LinkedList<String>();
    }
    
    // Methods
    public boolean getNextUserCommand (Scanner input) {
        String command = input.nextLine();
        String[] parsedCommand = command.split(" ");
        
        // Switch on the command (issued first in input line)
        switch(parsedCommand[0]) {
        case "exit":
            System.out.println("Goodbye!");
            return false;
        case "visit":
            visit(parsedCommand[1]);
            break;
        case "back":
            back();
            break;
        case "forward":
            forw();
            break;
        default:
            System.out.println("[X] Invalid command, try again");
        }
        
        System.out.println("Currently Visiting: " + current);
        
        return true;
    }
    
    /*
     *  Visits the current site, clears the forward history,
     *  and records the visited site in the back history
     */
    public void visit (String site) {
        backwardHistory.add(current);
        current = site;
        forwardHistory.clear();
    }
    
    /*
     *  Changes the current site to the one that was last
     *  visited in the order on which visit was called on it
     */
    public void back () {
    	if(backwardHistory.size() > 1) {
	    	forwardHistory.addFirst(current);
	        current = backwardHistory.getLast();
	        backwardHistory.removeLast();
    	}
    }
    
    public void forw () { 
    	if (forwardHistory.size() > 0) {
			backwardHistory.add(current);
		    current = forwardHistory.getFirst();
		    forwardHistory.removeFirst();
    	}
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        WebNavigator navi = new WebNavigator();
        
        System.out.println("Welcome to ForneyFox, enter a command from your ForneyFox user manual!");
        while (navi.getNextUserCommand(input)) {}
        System.out.println("Goodbye!");
    }

}