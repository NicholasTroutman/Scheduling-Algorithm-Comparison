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
public class Processor3 {

    LinkedList pool;
    Stack executing;
    int totW, poolW, executeW;
    int numCompleted, numFailed;
    double reward;
    // double utilization;
    // int totP, poolP,executeP;
    double stretch, stretchCount;

    double getUtilization() {
        double totU = 0;
        Job ptr = pool.front;
        if (ptr != null) {
            while (ptr != null) {

                totU = totU + (double) ptr.timeleft / (double) ptr.p;
                ptr = ptr.next;
            }
        }

        ptr = executing.bottom;
        while (ptr != null) {

            totU = totU + (double) ptr.timeleft / (double) ptr.p;
            ptr = ptr.next;
        }

        return (totU);

    }

    int getP() {
        int totP = 0;
        Job ptr = pool.front;
        if (ptr != null) {
            while (ptr != null) {

                totP = totP + ptr.p;
                ptr = ptr.next;
            }
        }

        ptr = executing.bottom;
        while (ptr != null) {

            totP = totP + ptr.p;
            ptr = ptr.next;
        }

        return (totP);

    }

    public Processor3() {
        pool = new LinkedList();

        // pool.add(null);
        executing = new Stack();
        poolW = 0;
        executeW = 0;
        totW = 0;
        reward = 0;
        numCompleted = 0;
        numFailed = 0;
        stretch = 0;
        stretchCount = 0;
        //utilization=0;
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

    void addToExecutingEDF(Job newProgram, long t) {
        //newProgram.setS(t);
        executing.top = newProgram;

        //executeW = executeW + newProgram.getW();  doesnt matter
        //totW = totW + newProgram.getW();          DOESN'T MATTER
    }

    boolean checkDeadline(long t) {
        if (executing.top != null) { //if something is in stack

            //decrement time left, and check if it finishes
            executing.top.timeleft--; //increment timeleft down
            executeW = executeW - 1; //NOW THE WORKLOAD IS LESS!!
            totW = executeW + poolW;

            if (executing.top.timeleft == 0) {//if its doene 
                stretch = +stretch;
                this.stretchCount++;
                double tempReward = executing.top.getReward(t);

                reward = reward + tempReward; //collect points
//System.out.println("COMPLETED 1:");
                // System.out.println(tempReward);
                //System.out.println("\nReward:");
                //System.out.println(tempReward);
                //System.out.println("");
                executing.remove();

                //count that it was completed
                numCompleted++;

                return (true); //removed something

            } else if ((executing.top.getR() + executing.top.p <= t) || (executing.top.getW() * 2 + executing.top.s <= t)) {
                //out of time, not finished
                /*System.out.println("\nFailed\nTime Left:");
                 System.out.println(executing.top.timeleft);
                System.out.println("\nDeadlines:");
                 System.out.println(executing.top.getR() + executing.top.p);
                System.out.println(executing.top.getW() * 2 + executing.top.s);
                 */
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

    boolean checkDeadlineEDF(long t) { //replace timeleft with Deadline
        if (executing.top != null) { //if something is in stack

            //decrement time left, and check if it finishes
            executing.top.timeleft--; //increment timeleft down
            executing.top.deadline--;
            //executeW = executeW - 1; //NOW THE WORKLOAD IS LESS!!
            //totW = executeW + poolW;

            if (executing.top.timeleft == 0) {//if its doene 
                stretch = stretch + executing.top.Stretch(t);
                double tempReward = executing.top.getReward(t);

                reward = reward + tempReward; //collect points
//System.out.println("COMPLETED 1:");
                // System.out.println(tempReward);
                //System.out.println("\nReward:");
                //System.out.println(tempReward);
                //System.out.println("");
                //executing.remove(); NOT NEEDED
                executing.top = null;

                //count that it was completed
                if (executing.top.timeleft > -1) {
                    numCompleted++;
                }

                return (true); //removed something

            } else if (executing.top.deadline < 1) { //out of time
                //out of time, not finished
                /*System.out.println("\nFailed\nTime Left:");
                 System.out.println(executing.top.timeleft);
                System.out.println("\nDeadlines:");
                 System.out.println(executing.top.getR() + executing.top.p);
                System.out.println(executing.top.getW() * 2 + executing.top.s);
                 */
                //remove workload
                //executeW = executeW - executing.top.timeleft; //timeleft not w
                //totW = executeW + poolW;
                //pop it
                //executing.remove();
                executing.top = null; //ez way of getting rid of it
                numFailed++;

                //no reward :'C'
                return (true); //empty stack

            }
        } //end if (executing.top !=null) AKA. end what to do if its not empty

        return (false);//no stack emptied

    }

    boolean isEmpty() {
        if (this.executing.top == null) {
            return (true);
        } else {
            return false;
        }
    }

}
