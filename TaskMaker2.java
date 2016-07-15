/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realtimemodel;

/**
 *
 * @author moriahha
 */
public class TaskMaker2 {

    int numProcessors;
    double utilization;
    double usedUtilization;
    Task2[] randomTasks;
    int count;
    long[] periods;

   // public TaskMaker2(int numProcessors2, double utilization2, double usedUtilization2, Task[] randomTasks2, int count2, long[] periods2) {
    public TaskMaker2(TaskMaker oldTaskMaker) { 
    numProcessors = oldTaskMaker.numProcessors;
        utilization =oldTaskMaker.utilization;
        usedUtilization = oldTaskMaker.usedUtilization;
        count = oldTaskMaker.count;
        periods = oldTaskMaker.periods;
        
        //randomTasks=randomTasks2;
        randomTasks=new Task2[count];
        for ( int i=0 ; i<count ; i++ ) {
        randomTasks[i]=new Task2( 0,i, oldTaskMaker.randomTasks[i].w, oldTaskMaker.randomTasks[i].p, oldTaskMaker.randomTasks[i].utilization, null);
        
        }
        
    }

    TaskMaker2(int numProcessors2) {

        numProcessors = numProcessors2;
        utilization = numProcessors;
        Task2 current, front;
        count = 0;
        usedUtilization = utilization;

        //make first task
        double rand = Math.random();

        if (rand < 0.3) {//light utilization 0.001-0.1
            double newRand = Math.random(); //decide what utilization is
            newRand = newRand * 0.099;
            newRand = newRand + 0.001;

            current = new Task2(newRand, 0); //make new Task2 with given Utilization

        } else if (rand < 0.7) {//medium utiliation 0.1-0.4
            double newRand = Math.random();
            newRand = 0.3 * newRand;
            newRand = newRand + 0.1;
            current = new Task2(newRand, 0);
        } else {
            //heavy utilization 0.5-0.9 LISTED IN THE PAPER, DON'T UNDERSTAND WHY 0.4-0.5 is not included??
            double newRand = Math.random();
            newRand = 0.4 * newRand;
            newRand = newRand + 0.5;
            current = new Task2(newRand, 0);
        }
        front = current; //beginning of linked list

        usedUtilization = current.utilization;

        int tempIndex = 1;
        while (usedUtilization <= utilization) {//make random Utilization

            count++;
            rand = Math.random();
            rand = rand * 100;
            rand = (int) rand;

            if (rand < 0.3) {//light utilization 0.001-0.1
                double newRand = Math.random();
                newRand = newRand * 0.099;
                newRand = newRand + 0.001;

                current.next = new Task2(newRand, tempIndex); //make new thingy
                current = current.next;

            } else if (rand < 0.7) {//medium utiliation 0.1-0.4
                double newRand = Math.random();
                newRand = 0.3 * newRand;
                newRand = newRand + 0.1;

                current.next = new Task2(newRand, tempIndex); //make new thingy
                current = current.next;
            } else {
                //heavy utilization 0.5-0.9 LISTED IN THE PAPER, DON'T UNDERSTAND WHY 0.4-0.5 is not included??
                double newRand = Math.random();
                newRand = 0.4 * newRand;
                newRand = newRand + 0.5;

                current.next = new Task2(newRand, tempIndex); //make new thingy
                current = current.next;

            }
            tempIndex++;
            usedUtilization = usedUtilization + current.utilization;

        } //end while loop
        usedUtilization = usedUtilization - current.utilization; //remove last one
        //count hasnt been updated, so we don't need to remove from count
        periods = new long[count];
        randomTasks = new Task2[count]; //initialize array of Tasks
        for (int i = 0; i < count; i++) {
            randomTasks[i] = front;
            front = front.next;
            periods[i] = (long) front.p;
        }//finished making array of tasks

    }

    long getLCM() {
        //here is a very ineffient way to find LCM   
        long lcm = lcm(periods);

        /* boolean isLCM = false;
         int lcm = 0;
         while (isLCM == false) {
         lcm++;
         for (int i = 0; i < count; i++) {
         if (lcm%randomTasks[i].p == 0) { //is a multiple of task[i]
         isLCM = true;
         } else {
         isLCM = false;
         i = count; //break it.
         }

         }
            
         }//end and f
         */
        return (lcm);
    }

    
    
    private static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    private static long gcd(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) {
            result = gcd(result, input[i]);
        }
        return result;
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    private static long lcm(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) {
            result = lcm(result, input[i]);
        }
        return result;
    }

}
