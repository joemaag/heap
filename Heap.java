/*************************

Joseph Maag

COP 3503 - 0001

Assignment 5: Heaps

4/18/14

***************************/

import java.io.*;
import java.util.*;
import java.io.File;

public class Heap{


	static ArrayList<Integer> load(Scanner sc){

		ArrayList<Integer> values = new ArrayList<Integer>();
		values.add(0);
		//all methods assume index 0 is empty

		while(sc.hasNextInt()){
			values.add(sc.nextInt());
		}

		return values;
	}

	static void print(ArrayList<Integer> values){

		if(values.size() == 1){
			//size == 1 means the heap is empty. That one element in the array is the empty index 0
			System.out.printf("(empty)\n");
			return;
		}

		for(int i=1;i<values.size();i++){
			System.out.printf("%d ", values.get(i));
		}
		System.out.printf("\n");
	}

	//Pre-condition: Assumes index 0 is empty in the array
	//Post-condition: Constructs a heap from an array using the bottom-up algorithim
	static ArrayList<Integer> buildHeap(ArrayList<Integer> values){

		int nodeCount = values.size()-1;

		for (int i=nodeCount/2; i>=1;i--){
			//loops through each parental node
			int parentIndex =i;
			int parentValue = values.get(parentIndex);

			boolean isHeap = false;

			while(!isHeap && (2*parentIndex)<=nodeCount){
				int childIndex = 2*parentIndex;
				if(childIndex<nodeCount){
					//there are two children for the current parentNode (k). 
					//Determine which child has the lesser value
					if(values.get(childIndex) < values.get(childIndex+1)){
						childIndex = childIndex+1;
					}
				}

				if(parentValue>=values.get(childIndex)){
					//If parent is greater than child, its a proper heap
					isHeap = true;
				}else{
					//If the child is greater than the parent, the child needs to be moved up and swicthed with it's parent
					int parentTemp = values.get(parentIndex);
					values.set(parentIndex,values.get(childIndex));
					values.set(childIndex,parentTemp);
					parentIndex = childIndex;
				}

			}

			values.set(parentIndex,parentValue);

		}

		return values;

	}

	//Post-condition: Deletes the largest value in the heap, AKA the root
	static ArrayList<Integer> deleteMax(ArrayList<Integer> values){

		int rootValue = values.get(1); //rootValue = max value
		int lastIndex = values.size()-1;
		//Switch the last element into the root, and the root value into the last elemetn position
		values.set(1,values.get(lastIndex));
		values.set(lastIndex,rootValue);
		//Now delete the last element
		values.remove(lastIndex);

		//Now heapify the list
		return buildHeap(values);
	}

	//Post-condition: Inserts value into the heap
	static ArrayList<Integer> insert(ArrayList<Integer> values, int value){

		//Simply add the value to the array (add node as last child), then re-heapify the tree
		values.add(value);
		return buildHeap(values);
	}

	//Post-condition: Performs the heap sort algorithm based on n-1 root deletions, and returns heap values from greatest to least
	static ArrayList<Integer> heapSort(ArrayList<Integer> values){
		int count = values.size()-1;
		for (int x=0; x<count; x++){
			//performs n-1 deletions
			//Print each root before deleting it
			System.out.printf("%d ", values.get(1));
			values = deleteMax(values);
			//we don't need to re-heapify because its done in the deleteMax method
		}

		System.out.printf("\n");
		//return empty heap
		return values;
	}


	public static void main(String [] args) throws Exception{

		Scanner sc = new Scanner(new File("heapops.txt"));
		ArrayList<Integer> values = new ArrayList<Integer>();
		//intializing the array isn't really necessary since a new one is created when we load in values for the array, and a load is guranteed before operations. 
		//But it has to be to compile.

		while(sc.hasNext()){
			String nextOp = sc.next();
			switch(nextOp){
				case "load":
				values = load(sc);
				break;

				case "print":
				print(values);
				break;

				case "build-heap":
				buildHeap(values);
				break;

				case "delete-max":
				deleteMax(values);
				break;

				case "insert":
				int valueToInsert = sc.nextInt();
				insert(values, valueToInsert);
				break;

				case "heapsort":
				heapSort(values);
				break;

				default: System.out.printf("INVALID COMMAND");
                break;
			}
		}

	}
	

}