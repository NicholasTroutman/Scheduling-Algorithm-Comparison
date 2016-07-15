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
public class TaskMaker { //this will

    int numProcessors;
    double utilization;
    double usedUtilization;
    Task[] randomTasks;
    int count;
    long[] periods;

    //test spot
    TaskMaker() {
        numProcessors = 4;
        count = 6;
        periods = new long[count];

        utilization = 4;
        usedUtilization = 4;

        randomTasks = new Task[count];
        randomTasks[0] = new Task(6, 5);//p, w
        randomTasks[1] = new Task(6, 4);
        randomTasks[2] = new Task(3, 2);
        randomTasks[3] = new Task(3, 2);
        randomTasks[4] = new Task(3, 2);
        randomTasks[5] = new Task(2, 1);

        for (int i = 0; i < count; i++) {
            periods[i] = randomTasks[i].p;
        }

    }

    TaskMaker(int numProcessors2, double idealUtilization) {
        //ideal Utilization must be between 0 and 1 EX:0.5
        // we are aiming for +/- 5 of idealUtilization

        //INITIALIZE FIRST
        utilization = numProcessors2 * idealUtilization;
        usedUtilization = 0;

        numProcessors = numProcessors2;
 
        Task current, front;
        current = null;
        front = null;
        count = 0;

        loop:
        while (usedUtilization == 0) {

            boolean closeEnoughToIdealUtilization = false;
            while (closeEnoughToIdealUtilization == false) {

                

                current = null;
                front = null;
                count = 1;
                usedUtilization = 0;

                //make first task
                double rand = Math.random();

                if (rand < 0.3) {//light utilization 0.001-0.1
                    double newRand = Math.random(); //decide what utilization is
                    newRand = newRand * 0.099;
                    newRand = newRand + 0.001;

                    current = new Task(newRand); //make new Task with given Utilization

                } else if (rand < 0.7) {//medium utiliation 0.1-0.4
                    double newRand = Math.random();
                    newRand = 0.3 * newRand;
                    newRand = newRand + 0.1;
                    current = new Task(newRand);
                } else {
                    //heavy utilization 0.5-0.9 LISTED IN THE PAPER, DON'T UNDERSTAND WHY 0.4-0.5 is not included??
                    double newRand = Math.random();
                    newRand = 0.4 * newRand;
                    newRand = newRand + 0.5;
                    current = new Task(newRand);
                }
                front = current; //beginning of linked list

                usedUtilization = current.utilization;

                while (usedUtilization <= utilization + 0.05 && closeEnoughToIdealUtilization == false) {//make random Utilization

                    count++;
                    rand = Math.random();
                    rand = rand * 100;
                    rand = (int) rand;

                    if (rand < 0.3) {//light utilization 0.001-0.1
                        double newRand = Math.random();
                        newRand = newRand * 0.099;
                        newRand = newRand + 0.001;

                        current.next = new Task(newRand); //make new thingy
                        current = current.next;

                    } else if (rand < 0.7) {//medium utiliation 0.1-0.4
                        double newRand = Math.random();
                        newRand = 0.3 * newRand;
                        newRand = newRand + 0.1;

                        current.next = new Task(newRand); //make new thingy
                        current = current.next;
                    } else {
                        //heavy utilization 0.5-0.9 LISTED IN THE PAPER, DON'T UNDERSTAND WHY 0.4-0.5 is not included??
                        double newRand = Math.random();
                        newRand = 0.4 * newRand;
                        newRand = newRand + 0.5;

                        current.next = new Task(newRand); //make new thingy
                        current = current.next;
                    }

                    usedUtilization = usedUtilization + current.utilization;
                    if (usedUtilization > utilization - 0.05 && usedUtilization < utilization + 0.05) {
                        closeEnoughToIdealUtilization = true;
                    }

                } //end while loop

            } //end close enough while loop

            periods = new long[count];
            randomTasks = new Task[count]; //initialize array of Tasks
            for (int i = 0; i < count; i++) {
                randomTasks[i] = front;

                // periods[i] = (long) front.p;
                periods[i] = (long) randomTasks[i].p;
                front = front.next; //move front afterwards
            }//finished making array of tasks
            if (usedUtilization != 0) {
                break;
            }

        } //check if used Utilization != 0

    }

    TaskMaker(int numProcessors2) {

        utilization = numProcessors2;
        usedUtilization = 0;
        loop:
        while (usedUtilization == 0) {
            numProcessors = numProcessors2;
            utilization = numProcessors;
            Task current, front;
            current = null;
            front = null;
            count = 0;
            usedUtilization = utilization;

            //make first task
            double rand = Math.random();

            if (rand < 0.3) {//light utilization 0.001-0.1
                double newRand = Math.random(); //decide what utilization is
                newRand = newRand * 0.099;
                newRand = newRand + 0.001;

                current = new Task(newRand); //make new Task with given Utilization

            } else if (rand < 0.7) {//medium utiliation 0.1-0.4
                double newRand = Math.random();
                newRand = 0.3 * newRand;
                newRand = newRand + 0.1;
                current = new Task(newRand);
            } else {
                //heavy utilization 0.5-0.9 LISTED IN THE PAPER, DON'T UNDERSTAND WHY 0.4-0.5 is not included??
                double newRand = Math.random();
                newRand = 0.4 * newRand;
                newRand = newRand + 0.5;
                current = new Task(newRand);
            }
            front = current; //beginning of linked list

            usedUtilization = current.utilization;

            while (usedUtilization <= utilization) {//make random Utilization

                count++;
                rand = Math.random();
                rand = rand * 100;
                rand = (int) rand;

                if (rand < 0.3) {//light utilization 0.001-0.1
                    double newRand = Math.random();
                    newRand = newRand * 0.099;
                    newRand = newRand + 0.001;

                    current.next = new Task(newRand); //make new thingy
                    current = current.next;

                } else if (rand < 0.7) {//medium utiliation 0.1-0.4
                    double newRand = Math.random();
                    newRand = 0.3 * newRand;
                    newRand = newRand + 0.1;

                    current.next = new Task(newRand); //make new thingy
                    current = current.next;
                } else {
                    //heavy utilization 0.5-0.9 LISTED IN THE PAPER, DON'T UNDERSTAND WHY 0.4-0.5 is not included??
                    double newRand = Math.random();
                    newRand = 0.4 * newRand;
                    newRand = newRand + 0.5;

                    current.next = new Task(newRand); //make new thingy
                    current = current.next;
                }

                usedUtilization = usedUtilization + current.utilization;

            } //end while loop
            usedUtilization = usedUtilization - current.utilization; //remove last one
            //count hasnt been updated, so we don't need to remove from count
            periods = new long[count];
            randomTasks = new Task[count]; //initialize array of Tasks
            for (int i = 0; i < count; i++) {
                randomTasks[i] = front;

                // periods[i] = (long) front.p;
                periods[i] = (long) randomTasks[i].p;
                front = front.next; //move front afterwards
            }//finished making array of tasks
            if (usedUtilization != 0) {
                break;
            }
        }
    }

    long getLCM() {
        //here is a very ineffient way to find LCM   
        long lcm = lcm(periods);

        for (int i = 0; i < count; i++) {
            if (lcm % periods[i] != 0) {
                System.out.println("\nERROR, NOT A PROPER LCM!!\n");
            }

        }//check that all of them are factors

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
        return ((a * b) / gcd(a, b));
    }

    private static long lcm(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) {
            result = lcm(result, input[i]);
        }
        return result;
    }

}
