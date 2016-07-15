/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realtimemodel;

/**
 *
 * @author Nick
 */
public class LBBA {

    LinkedList TempList;
    Processor[] servers;
    int numProcessors;
    int totCompleted, totFailed;
    double totReward;
    int preemptions;
    LinkedList processorOrder[];
    double flow;
    double stretch;
    double numJobs;
    
    
   
    
    
    

    LBBA(int numProcessors2) //initializes the number of processors
    {
        numJobs=0;
        preemptions = 0;
        TempList = new LinkedList();
        stretch=0;

        numProcessors = numProcessors2;
        //initalize processors AKA servers here
        servers = new Processor[numProcessors]; //have enough processors
        processorOrder = new LinkedList[numProcessors]; //ordering 'em

        for (int i = 0; i < numProcessors; i++) {
            servers[i] = new Processor();
            processorOrder[i] = new LinkedList();
        }

    }

    int minProcessor() //finds the least-busy workload
    {
        int minW = servers[0].totW;
        int index = 0;
        for (int i = 1; i < numProcessors; i++) {
            if (servers[i].totW < minW) {
                minW = servers[i].totW;
                index = i;
            }

        }

        return (index);
    }

    void add(Job newTest) //adds a new job,with the time parameter
    {
        //TEMPORARILY
        //System.out.println("\n Adding new Task");
        TempList.add(newTest); //add to TempList
this.numJobs++;
        //TODO may have to reinstate calculateD
        //TempList.calculateD(t); //calculates the D of everyhing
        //TempList.insertionSortD(TempList.back,t); //sorts the list
    }

    void event(long t) {

        boolean flag = false;
        int[] index;
        index = new int[numProcessors];
        int count = 0;
        Job temp;

        if (TempList.front != null) {
            TempList.calculateD(t);
            TempList.insertionSortD(TempList.front, t);
        }

        //check for an empty procesor, and count them (count)
        for (int i = 0; i < numProcessors; i++) {
            if (servers[i].isEmpty() == true) //if nothing executing
            {
                index[count] = i;
                count++;
            }
        } //done counting empty processors
        if (count > 0) { //find the empty processors

            for (int i = 0; i < count; i++) { //loop through the processors, adding programs so them

                if (servers[index[i]].pool.back == null && TempList.back != null) {
                    temp = TempList.remove(); //remove program from templist
                    servers[index[i]].addToExecuting(temp, t); //add to the stack

                } else if (servers[index[i]].pool.back != null && TempList.back == null) { //check if pool is not empty and templist is
                    temp = servers[index[i]].removeFromPool(); //remove from pool
                    servers[index[i]].addToExecuting(temp, t); //add to the stack 

                } //if both have stuff
                else if (servers[index[i]].pool.back != null && TempList.back != null) {
                    if (TempList.back.getD() > servers[index[i]].pool.back.getD()) { //if the templist biggest is bigger thatn the biggest pool
                        temp = TempList.remove(); //remove from templist
                        servers[index[i]].addToExecuting(temp, t); //add to the stack
                    } else if (TempList.back.getD() < servers[index[i]].pool.back.getD()) { //pool >templist
                        temp = servers[index[i]].removeFromPool(); //remove from pool
                        servers[index[i]].addToExecuting(temp, t); //added to to stack

                    }
                }

            }
        }//end of empty stack processe
        else if (count == 0) //else no empty stack
        {

            int i = 0; //processor thingy
            while (TempList.front != null && i < numProcessors) {
                if (TempList.front.getD() > (4 * servers[i].executing.top.getD())) { //nullpoint exception

                    preemptions++;
                    temp = TempList.remove(); //remove it how it is, add to list.
                    servers[i].addToExecuting(temp, t);

                    index[count] = i; //indexing how many processors have d under the threshold
                    count++; //counting how many proccessors have d under the threshold of temp d/4

                } //end if statment

                i++; //move through processors
            }
        }

        //decide which processor to push the tempList object onto, partition
        while (this.TempList.front != null) {
          
            indexer[] serverIndex = makeIndexers();
            this.quickSort(serverIndex, 0, this.numProcessors - 1); //sorts it

            for (int i = 0; i < numProcessors; i++) {
                if (TempList.front != null) {
                    temp = TempList.remove();
                    servers[serverIndex[i].index].addToPool(temp);

                }

            }

        }

    }

    void Ending(long t) //to be called after each second, decrements
    {
        boolean check = false;
        for (int i = 0; i < numProcessors; i++) {
            if (servers[i].checkDeadline(t) == true) { //if something empties
                check = true;
            }
        }

        if (check == true) {
            event(t);
        }

    }

void findTotalReward() {
        totReward = 0;
        totCompleted = 0;
        totFailed = 0;
        for (int i = 0; i < numProcessors; i++) {
            totReward = totReward + servers[i].reward;
            totCompleted = totCompleted + servers[i].numCompleted;
            totFailed = totFailed + servers[i].numFailed;
            stretch =stretch+servers[i].stretch;
            
        }
        double stretchCount=0;
        
          for (int i = 0; i < numProcessors; i++) {
        stretchCount=servers[i].stretchCount+stretchCount;
          }
          
          stretch=stretch/stretchCount;
        
        
        System.out.println("");
        System.out.println("TOTAL STRETCH IS");
        System.out.println(stretch);
        System.out.println("");
        }

    //for load balancing stuff
    public indexer[] makeIndexers() {
        indexer[] indexers = new indexer[this.numProcessors];

        for (int i = 0; i < this.numProcessors; i++) {
            indexers[i] = new indexer(this.servers[i].totW, i);

        }
        return (indexers);
    }

   public static void quickSort(indexer[] arr, int low, int high) {
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		// pick the pivot
		int middle = low + (high - low) / 2;
		indexer pivot = arr[middle];
 
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (arr[i].w < pivot.w) {
				i++;
			}
 
			while (arr[j].w > pivot.w) {
				j--;
			}
 
			if (i <= j) {
				indexer temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (low < j)
			quickSort(arr, low, j);
 
		if (high > i)
			quickSort(arr, i, high);
	}
   
   
    public class indexer {

        int index;
        int w;

        public indexer(int totW, int index2) {
            index = index2;
            w = totW;
        }

    }

}//END MODEL
