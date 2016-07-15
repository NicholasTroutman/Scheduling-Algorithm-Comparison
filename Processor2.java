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
public class Processor2 {

    LinkedList pool;
    Stack executing;
    int totW, poolW, executeW;
    int numCompleted, numFailed;
    double reward;
    double stretch,stretchCount;

    public Processor2() {
        pool = new LinkedList();
stretch=0;
stretchCount=0;
        // pool.add(null);
        executing = new Stack();
        poolW = 0;
        executeW = 0;
        totW = 0;
        reward = 0;
        numCompleted = 0;
        numFailed = 0;
    }

    Job removeFromPool() {
        Job temp = pool.remove();

        poolW = poolW - temp.getW();
        totW = poolW + executeW;
        return (temp);

    }

    void addToPool(Job newProgram) {
        pool.add(newProgram);

        poolW = newProgram.getW() + poolW;
        totW = poolW + executeW;
    }

    void addToExecuting(Job newProgram, long t) {
        newProgram.setS(t);
        executing.add(newProgram, t);

        executeW = executeW + newProgram.getW();
        totW = totW + newProgram.getW();
    }

    
    
    
    boolean checkDeadline(long t,FinishedList2 jobsCompleted[] ) {
        if (executing.top != null) { //if something is in stack

            //decrement time left, and check if it finishes
            executing.top.timeleft--; //increment timeleft down
            executeW = executeW - 1; //NOW THE WORKLOAD IS LESS!!
            totW = executeW + poolW;

            if (executing.top.timeleft == 0) {//if its doene 
                stretch=stretch+executing.top.Stretch(t);
                   this.stretchCount++;
                double tempReward = executing.top.getReward(t);

                 //UNIQUE TO V2
                jobsCompleted[executing.top.index].SetCompletionNum(executing.top.jobNumber);

                reward = reward + tempReward; //collect points

                //System.out.println("\nReward:");
                //System.out.println(tempReward);
                //System.out.println("");
                
                if (executing.top.timeleft>-1) {
                numCompleted++;
                }
                executing.remove();

                //count that it was completed
                

                return (true); //removed something
                
                //UNIQUE
            //deadline 1: if the job(n+1) has been completed, break UNIQUE
                //deadline 2: if 2*w+s has been reached, break
            } else if ((jobsCompleted[executing.top.index].completionNum > executing.top.jobNumber) || (executing.top.getW() * 2 + executing.top.s <= t)) {
                //out of time, not finished
                // System.out.println("\nFailed\nTime Left:");
                // System.out.println(executing.top.timeleft);
                //System.out.println("\nDeadlines:");
                // System.out.println(executing.top.getR() + executing.top.p);
                //System.out.println(executing.top.getW() * 2 + executing.top.s);

                //remove workload
                executeW = executeW - executing.top.timeleft; //timeleft not w
                totW = executeW + poolW;
                //pop it
                executing.remove();
                numFailed++;

                //no reward :'C'
                return (true); //empty stack

            }
        } //end if (executing.top !=null) AKA. end what to do if its not empty

        return (false);//no stack emptied

    }

    
    //OLD VERSION
    
    /*
    
    
    
    boolean checkDeadline(int t, FinishedList2 jobsCompleted[]) {
        if (executing.top != null) { //if something is in stack

            //decrement time left, and check if it finishes
            executing.top.timeleft--; //increment timeleft down
            executeW = executeW - 1; //NOW THE WORKLOAD IS LESS!!
            totW = executeW + poolW;

            if (executing.top.timeleft == 0) {//if its doene 
                double tempReward = executing.top.getReward(t);

                reward = reward + tempReward; //collect points

                System.out.println("\nReward:");
                System.out.println(tempReward);
                System.out.println("");

                //UNIQUE TO V2
                jobsCompleted[executing.top.index].SetCompletionNum(executing.top.jobNumber);

                totW = executeW + poolW;
                //remove testProgram
                executing.remove();

                //count that it was completed
                numCompleted++;
                if (executing.top == null) {
                    return (true); //removed something
                } //deadline 1: if the job(n+1) has been completed, break UNIQUE
                //deadline 2: if 2*w+s has been reached, break
            } else if ((jobsCompleted[executing.top.index].completionNum > executing.top.jobNumber) || (executing.top.getW() * 2 + executing.top.s <= t)) {
                //out of time, not finished
                System.out.println("\nFailed\nTime Left:");
                System.out.println(executing.top.timeleft);
                System.out.println("\nDeadlines:");
                System.out.print(jobsCompleted[executing.top.index].completionNum);
                System.out.println(executing.top.jobNumber);
                System.out.println(executing.top.getR() + executing.top.p);
                System.out.print("W*2+S:   ");
                System.out.println(executing.top.getW() * 2 + executing.top.s);

                //remove workload
                executeW = executeW - executing.top.timeleft; //timeleft not w
                totW = executeW + poolW;
                //pop it
                executing.remove();
                numFailed++;

                //no reward :'C'
                if (executing.top == null) {
                    return (true); //empty stack
                }
            }
        } //end if (executing.top !=null) AKA. end what to do if its not empty
        return (false);//no stack emptied
    }
    
    */ //end old version

   boolean isEmpty() {
        if (this.executing.top == null) {
            return (true);
        } else {
            return false;
        }
    }

}
