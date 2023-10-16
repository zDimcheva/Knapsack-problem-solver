package comp26120;

import java.util.ArrayList;

public class dp_kp extends KnapSack {
    public dp_kp(String filename) {
	super(filename);
    }

    public void DP(ArrayList<Boolean> solution) {
	ArrayList<Integer> v = item_values;
	ArrayList<Integer> wv = item_weights;
	int n = Nitems;
	int W = Capacity;
	
	// the dynamic programming function for the knapsack problem
	// the code was adapted from p17 of http://www.es.ele.tue.nl/education/5MC10/solutions/knapsack.pdf

	// v array holds the values / profits / benefits of the items
	// wv array holds the sizes / weights of the items
	// n is the total number of items
	// W is the constraint (the weight capacity of the knapsack)
	// solution: a 1 in position n means pack item number n+1. A zero means do not pack it.

	ArrayList<ArrayList<Integer>> V = new ArrayList<ArrayList<Integer>>(n + 1);
	ArrayList<ArrayList<Boolean>> keep = new ArrayList<ArrayList<Boolean>>(n + 1);;// 2d arrays for use in the dynamic programming solution
	// keep[][] and V[][] are both of size (n+1)*(W+1)

	int i, w, K;

	// Initialise V and keep with null objects
	/* ADD CODE HERE */
    for(int j = 0; j < n + 1; j++){
        V.add(new ArrayList<Integer>(W + 1));
        keep.add(new ArrayList<Boolean>(W + 1));
        solution.add(false);

        for(int k = 0; k < W + 1; k++){
            V.get(j).add(null);
            keep.get(j).add(null);
        }
    }
 
	//  set the values of the zeroth row of the partial solutions table to zero
	/* ADD CODE HERE */
    for(int k = 0; k < W + 1; k++){
        V.get(0).set(k, 0);
        keep.get(0).set(k, false);            
    }

	// main dynamic programming loops , adding one item at a time and looping through weights from 0 to W
	/* ADD CODE HERE */
    for(i = 1; i < n + 1; i++){
        for(w = 0; w < W + 1; w++){
            if((wv.get(i) <= w) && (v.get(i) + V.get(i - 1).get(w - wv.get(i)) > V.get(i - 1).get(w))){
                V.get(i).set(w, v.get(i) + V.get(i - 1).get(w - wv.get(i)));
                keep.get(i).set(w, true);
            } 
            else{
                V.get(i).set(w, V.get(i - 1).get(w));
                keep.get(i).set(w, false);
            }                 
        }
    }

	// now discover which items were in the optimal solution
	/* ADD CODE HERE */
    K = W;
    for(i = n; i >= 1; i--){
        if(keep.get(i).get(K) == true){
            solution.set(i, true);
            K = K - wv.get(i);
        }
    }	

    }
    
    public static void main(String[] args) {
	ArrayList<Boolean> solution;

	dp_kp knapsack = new dp_kp(args[0]);
	solution = new ArrayList<Boolean>(knapsack.Nitems+1);
	solution.add(null); // C implementation has null first object

	knapsack.DP(solution);
	knapsack.check_evaluate_and_print_sol(solution);
    }
}
