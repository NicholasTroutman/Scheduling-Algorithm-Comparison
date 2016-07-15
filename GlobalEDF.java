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
public class GlobalEDF {

    LinkedList TempList;
    Processor[] servers;
    int numProcessors;
    int totCompleted, totFailed;
    double totReward;
    int preemptions;
    double stretch=0;
    double numJobs;
    //LinkedList processorOrder[];

    GlobalEDF(int numProcessors2) {
        this.numProcessors = numProcessors2;
        this.preemptions = 0;
        this.totReward = 0;
        stretch=0;
        numJobs=0;

        TempList = new LinkedList();

        servers = new Processor[numProcessors];

        //ADDED BECAUSE SOME CAN FAIL IN THE TEMPLIST
        totFailed = 0;

        for (int i = 0; i < numProcessors; i++) {
            servers[i] = new Processor();

        }

    }

    void add(Job newTest) //adds a new job,with the time parameter
    {
        //TEMPORARILY
        //System.out.println("\n Adding new Task");
        numJobs++;
        TempList.add(newTest); //add to TempList

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
            //TempList.calculateD(t);
            TempList.insertionSortDeadline(TempList.front, t); //order the TempList
        }

        //check for an empty procesor, and count them (count)
        for (int i = 0; i < numProcessors; i++) {
            if (servers[i].executing.top == null) //if nothing, aka empty
            {
                index[count] = i;
                count++;
            }
        } //done counting empty processors
        if (count > 0) { //find the empty processors, there is no pool

            for (int i = 0; i < count; i++) { //loop through the number of empty processors, adding programs with smallest deadline 

                if (TempList.back != null) {
                    temp = TempList.remove(); //remove lowest deadline program from templist
                    servers[index[i]].executing.top = temp; //add to the EMPTY stack

                }

            }
        }//end of empty stack processe
        else if (count == 0) //else no empty stack
        {

            int i = 0; //processor thingy
            while (TempList.front != null && i < numProcessors) {
                if (TempList.front.deadline < servers[i].executing.top.deadline) { //if earlier deadline, preempt

                    preemptions++; //increase preemptions count

                    temp = servers[i].executing.top; //save processor task
                    servers[i].executing.top = TempList.remove(); //remove it how it is, add to tempList.
                    TempList.add(temp); //put saved templist 
                    TempList.insertionSortDeadline(TempList.front, t); //reorder it

                    } //end if statment

                i++; //move through processors
            }
        }

  
    }

    void Ending(long t) //to be called after each second, decrements
    {
        boolean check = false;
        for (int i = 0; i < numProcessors; i++) {
            if (servers[i].checkDeadlineEDF(t) == true) { //if something empties
                check = true; //call event later
            }
        }

        Job temp = TempList.front;
        Job behindTemp = null;

        
        while (temp != null) //cycle thru until it is done
        {
            temp.deadline--; //decrement

            if (temp.deadline < 1) {
                //if finished
                this.totFailed++; //count it

                if (TempList.front == temp && TempList.back != temp) { //front of list, not back

                    behindTemp = temp;
                    temp = temp.next;

                    behindTemp.next = null;
                    TempList.front = temp;
                    TempList.count--; //record it in templist

                } else if (TempList.front != temp && TempList.back == temp) { //back of list, not front

                    temp = temp.next; //should be null
                    TempList.remove(); //remove that last one
                    //dont need to change count, remove does that

                } else if (TempList.front == temp && TempList.back == temp) { //back and front of list only element
                    //1 element, which is front and back
                    TempList.front = null;
                    TempList.back = null;
                    TempList.count--; //record it in templist
                } else if (TempList.front != temp && TempList.back != temp) //if middle of the pack
                {
                    //then we assume behindTemp exsists, because it had to pass one, and behind temp will be the last one passed.
                    temp = temp.next;
                    behindTemp.next = temp;
                    TempList.count--; //record it in templist

                }//end 

            } else if (temp.deadline > 0) {
                behindTemp = temp;
                temp = temp.next;

            }

        } //while Temp  != null
        
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
    
}//END MODEL

