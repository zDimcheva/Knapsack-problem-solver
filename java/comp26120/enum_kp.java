package comp26120;

import java.util.ArrayList;

public class enum_kp extends KnapSack {
    public enum_kp(String filename) {
	super(filename);
    }

    
    public static void main(String[] args) {
	enum_kp knapsack = new enum_kp(args[0]);

	knapsack.print_instance();
	knapsack.enumerate();
    }

    public void enumerate() {
	// Do an exhaustive search (aka enumeration) of all possible ways to pack
	// the knapsack.
	// This is achieved by creating every binary solution vector of length Nitems.
	// For each solution vector, its value and weight is calculated.

	int i; // item index
	ArrayList<Boolean> solution = new ArrayList<Boolean>(Nitems + 1); // Solution vector representing items packed
	solution.add(null); // C implementation has null first object
	    
	ArrayList<Boolean> best_solution = new ArrayList<Boolean>(Nitems + 1); // Solution vector for best solution found
	best_solution.add(null);

	int best_value; // total value packed in the best solution
	double j = 0;
	int infeasible; // 0 means feasible; -1 means infeasible (violates the capacity constraint)

	// set the knapsack initially empty
	for (i = 1; i <= Nitems; i++) {
	    solution.add(false);
	    best_solution.add(false);
	}

	QUIET = false;
	best_value = 0;

    int value = 0;
    int solutions_count = (int) Math.pow(2, Nitems); 
    int solution_number = 0;
    int procent = 0;
    int old_pro = 0;

	while (!(next_binary(solution, Nitems))) {
	    /* ADD CODE IN HERE TO KEEP TRACK OF FRACTION OF ENUMERATION DONE */
        solution_number++;
        procent = 10 * solution_number / solutions_count;             

	    // calculates the value and weight and feasibility:
        QUIET = true;
	    infeasible=check_evaluate_and_print_sol(solution); 
 
	    /* ADD CODE IN HERE TO KEEP TRACK OF BEST SOLUTION FOUND*/
        if(infeasible == 0){
            value = price(solution, item_values);
            if(best_value < value){
                best_value = value;
                for(int k = 1; k < Nitems + 1; k++){
                    best_solution.set(k, solution.get(k));
                }
                System.out.print("[");
                for(int progress = 0; progress < 10; progress++){
                    if(progress < procent){                        
                        System.out.print("=");
                    }
                    else{
                        System.out.print("-");
                    }
                }
                System.out.println("]" + (procent * 10) + "%");
	            QUIET = false;
                check_evaluate_and_print_sol(best_solution);
            }
        }

        old_pro = procent;
	}

	/* ADD CODE TO PRINT OUT BEST SOLUTION */
    System.out.println("[==========]100%");
	QUIET = false;
    check_evaluate_and_print_sol(best_solution); 
	QUIET = true;
    System.out.println(best_value);
    }

    public int price(ArrayList<Boolean> item_inside, ArrayList<Integer> values){

        int sum = 0;

        for(int i = 1; i < Nitems + 1; i++){
            if(item_inside.get(i) == true){
                sum = sum + values.get(i); 
            }
        }

        return sum;

    }

    public boolean next_binary(ArrayList<Boolean> str, int Nitems) {
	// Called with an ArrayList of 0/1 entires with length Nitems
	// (0th item is null for some reason inherited from C implementation - I expect
	// because in C the array is here treated as a pointer to a string).
	// If we treat this array list as a binary number, then this
	// Function adds "1" - e.g., {0,0,0,1} would turn to {0,0,1,0}
	// It returns true when there are no possible binary numbers left 
	int i = Nitems;
	while(i>=1) {
	    if (str.get(i) == true) {
		str.set(i, false);
		i--;
	    } else {
		str.set(i, true);
		break;
	    }
	}
	if (i == 0) {
	    return true;
	} else {
	    return false;
	}
    }
}
