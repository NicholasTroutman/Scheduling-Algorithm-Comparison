/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realtimemodel;
//package algorithm.sort;

import java.util.Scanner;

/**
 *
 * @author Nick
 */
public class Menu2 {

    LBBA2 lbbaProgram;
    TaskMaker2 tasksMade;
    long lcm;
    int numProcessors;

    Menu2(Menu menuV1) {
        //make the programs. PROGRAMMAKER(p,w) 
       // System.out.println("How many Processors?");
       // Scanner sc = new Scanner(System.in);
        // numProcessors = sc.nextInt();

        //tasks = new Task[3];
        //tasks[0] = new Task(5, 3); //should finish at t=1
        //tasks[1] = new Task(3, 2); //should finish at t=2
        //tasks[2] = new Task(10, 7);
         numProcessors=menuV1.numProcessors;
        
        tasksMade = new TaskMaker2(menuV1.tasksMade);
          //GETLCM
       // lcm = (int) tasksMade.getLCM();// will find LCM (hopefully)
        this.lcm=menuV1.lcm;
        
        lbbaProgram = new  LBBA2( numProcessors, tasksMade.count, tasksMade.randomTasks); //initializes the number of processors
    }

    void run() {
        boolean triggerEvent = false;

        //GETLCM
       // int lcm = (int) tasksMade.getLCM();// will find LCM (hopefully)
       // System.out.print("LCM is ");
       // System.out.print(lcm);
       // Scanner sc = new Scanner(System.in);
        //int numProcessors = sc.nextInt();

        Job temp;
       // System.out.println("Second:");
        //System.out.println("0");

        for (int j = 0; j < this.tasksMade.count; j++) { //change 3 with num processors in future
            {
                temp = tasksMade.randomTasks[j].TestProgamMaker(0);
                lbbaProgram.add(temp); //add em
            }
        }
        
        lbbaProgram.event(0); //trigger first event
        
        /*
        System.out.println("\nTempList count:");
        System.out.println(lbbaProgram.TempList.count);
        System.out.println("");

        for (int k = 0; k < lbbaProgram.numProcessors; k++) {
            System.out.print("Processor[");
            System.out.print(k);
            System.out.print("] TotalWorkload:\n");
            System.out.println(lbbaProgram.servers[k].totW);

            System.out.print("Processor[");
            System.out.print(k);
            System.out.print("].pool count:\t");
            System.out.println(lbbaProgram.servers[k].pool.count);
            System.out.print("Processor[");
            System.out.print(k);
            System.out.print("].servers count:\t");
            System.out.println(lbbaProgram.servers[k].executing.count);
        }
        */

        lbbaProgram.Ending(1); //after end of the second, increment time left

        //DONE WITH Time 0, and the ending from 0-1, 1 sec has elapsed
        //START LOOP
        for (long i = 1; i <= lcm; i++) { //run from 1-30, since 0 has already happened

            //System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            //System.out.println("Second:");
           // System.out.println(i);

            for (int j = 0; j < tasksMade.count; j++) {
                if (i % tasksMade.randomTasks[j].p == 0) //TIME 2 RELEASE!!!
                {
                    temp = tasksMade.randomTasks[j].TestProgamMaker(i);
                    lbbaProgram.add(temp); //add em
                    //lbbaProgram.event(i); //trigger event
                    triggerEvent = true;
                }
            }
            
            if (triggerEvent == true) {
                lbbaProgram.event(i); //eventTIME!
            triggerEvent=false;
            }

            /*
                    System.out.println("\nTempList count:");
            System.out.println(lbbaProgram.TempList.count);
            System.out.println("");

            for (int k = 0; k < lbbaProgram.numProcessors; k++) {
                System.out.print("Processor[");
                System.out.print(k);
                System.out.print("] TotalWorkload:\n");
                System.out.println(lbbaProgram.servers[k].totW);
                System.out.print("Processor[");
                System.out.print(k);
                System.out.print("].pool count:\t");
                System.out.println(lbbaProgram.servers[k].pool.count);
                System.out.print("Processor[");
                System.out.print(k);
                System.out.print("].servers count:\t");
                System.out.println(lbbaProgram.servers[k].executing.count);
            }
                    */ 
            lbbaProgram.Ending(i + 1); //after end of the second, increment time left

        }//end second

        lbbaProgram.findTotalReward();
/*        System.out.println("\nTotal Reward:");
        System.out.println(lbbaProgram.totReward);

        System.out.println("Num Completed:");
        System.out.println(lbbaProgram.totCompleted);

        System.out.println("Num Failed:");
        System.out.println(lbbaProgram.totFailed);

        System.out.println("Processors");
        System.out.println(numProcessors);

        System.out.println("Total Utilization");
        System.out.println(this.tasksMade.usedUtilization);

        System.out.println("");
    */
        }

}

