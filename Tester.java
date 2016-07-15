/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realtimemodel;

import java.util.Scanner;


public class Tester {
    //runs 3 types of algorithms of the same stuff programs

    Menu simulation1; //LBBA
    Menu2 simulation2; //LBBA V2
    Menu3 simulation3;
    MenuGlobalEDF simulation4;
    MenuEDFOS simulation5;

    // IMPLEMENT THE NEWEST VERSION SOON
    double[] trialTiming;
    double[] totTiming;
    double[] meanTiming;

    double[] totCompletionRate; //find mean
    double[] meanCompletionRate;

    double[] totReward;         //find total or mean? 
    double[] meanReward;

    //added later
    long[] lcm;
    double[] lcmTrials;

    //tasks
    double[] tasks;
    double [] stretch;
    
    double[] preemptions;
    //Menu simulation3; //LBBA V3

    Tester() {
        totTiming = new double[5];
        meanTiming = new double[5];

        totCompletionRate = new double[5];
        meanCompletionRate = new double[5];

        totReward = new double[5];
        meanReward = new double[5];
        
        stretch=new double[5];
        lcm = new long[5];
        tasks = new double[5];
        preemptions=new double[5];

        for (int i = 0; i < 5; i++) {
            totTiming[i] = 0;
            meanTiming[i] = 0;

            totCompletionRate[i] = 0;
            meanCompletionRate[i] = 0;

            totReward[i] = 0;
            meanReward[i] = 0;

            lcm[i] = 0;
            tasks[i] = 0;
            
            preemptions[i]=0;
            stretch[i]=0; 

        }
       

    }

    void startModel() {
       
        
        System.out.println("How many Processors?");
        Scanner sc = new Scanner(System.in);
        int numProcessors = sc.nextInt();

        System.out.println("\nHow many Trials?");
        int trials = sc.nextInt();

        this.trialTiming = new double[trials];
        lcmTrials = new double[trials];
        double numJobs=0;
        //V1

        long startTime, endTime;
        
        
        double idealUtilization=-1;
          System.out.println("\nWhat is your desired Utilization +/-0.05 \n");
        while (idealUtilization>0.95 || idealUtilization<0.05) {
  
            idealUtilization = sc.nextDouble();
            
            if(idealUtilization<0.05 || idealUtilization>0.95)
            {
                System.out.println("Input is not allowed, must be between 0.05 and 0.95");   
                }
        
        }

        for (int i = 0; i < trials; i++) {
            
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
             System.out.println("\n\n Trial:");
            System.out.println(i);
            
            System.out.println("");
            simulation1 = new Menu(numProcessors,idealUtilization);
            startTime = System.currentTimeMillis();
            simulation1.run();

            endTime = System.currentTimeMillis();

            // System.out.println("Milli-Seconds Elapsed:");
            //System.out.println(endTime-startTime);
            //meanTiming[0]=meanTiming[0]+endTime-startTime; calculate l8tr sk8tr g8tr
            totTiming[0] = totTiming[0] + endTime - startTime;
            double temp=(double) simulation1.lbbaProgram.totCompleted / ((double) simulation1.lbbaProgram.totCompleted + (double) simulation1.lbbaProgram.totFailed);
            temp=temp/ (double) 0.00000001;
            int temp2=(int) temp;
            temp=temp2;
            temp=temp/100000000;
                   numJobs=simulation1.lbbaProgram.numJobs;
            totCompletionRate[0] = totCompletionRate[0] + temp;
            totReward[0] = simulation1.lbbaProgram.totReward/(double) numJobs + totReward[0];
            lcm[0] = simulation1.lcm + lcm[0] / (long) trials;
             tasks[0]=simulation1.tasksMade.count+tasks[0];
       
             
             preemptions[0]=preemptions[0]+  (double) simulation1.lbbaProgram.preemptions/(double)numJobs;
             stretch[0]=stretch[0]+simulation1.lbbaProgram.stretch;
            //System.out.println(simulation1.lbbaProgram.preemptions);
           
             //OUTPUT THE RESULTS FOR 1
             System.out.println("\n\nLCM IS:");
             System.out.println(simulation1.lcm);
             System.out.println("V1:");
             System.out.println("Completion rate V1:");
            System.out.println(temp);
            
               System.out.println("Reward Rate V1:");
            System.out.println(simulation1.lbbaProgram.totReward);
            
               System.out.println("Tasks Made V1:");
            System.out.println(simulation1.tasksMade.count);
            
            
               System.out.println("Preemptions V1:");
            System.out.println(simulation1.lbbaProgram.preemptions);
             
            if(temp<0.5)
            {
                System.out.println("\nERROR HERE I THINK!!!\n");   
            }
            
            
            
            
            
            //~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~
             
            simulation2 = new Menu2(simulation1);
            startTime = System.currentTimeMillis();
            simulation2.run();

            endTime = System.currentTimeMillis();

            //  System.out.println("Milli-Seconds Elapsed:");
            // System.out.println(endTime-startTime);
            totTiming[1] = totTiming[1] + endTime - startTime;
           
               temp=(double) simulation2.lbbaProgram.totCompleted / ((double) simulation2.lbbaProgram.totCompleted + (double) simulation2.lbbaProgram.totFailed);
            temp=temp/ (double) 0.00000001;
            temp2=(int) temp;
            temp=temp2;
            temp=temp/100000000;
            totCompletionRate[1] = totCompletionRate[1] + temp;
            totReward[1] = simulation2.lbbaProgram.totReward /(double) numJobs + totReward[1];
            lcm[1] = simulation2.lcm + lcm[1] / (long) trials;
            tasks[1]=simulation2.tasksMade.count+tasks[1];
               preemptions[1]=preemptions[1]+  (double) simulation2.lbbaProgram.preemptions/(double)numJobs;
            //System.out.println(simulation2.lbbaProgram.preemptions);
            stretch[1]=stretch[1]+(double) simulation2.lbbaProgram.stretch;
                 //OUTPUT THE RESULTS FOR 144
             System.out.println("\n\nV2:");
             System.out.println("Completion rate V2:");
            System.out.println(temp);
            
               System.out.println("Reward Rate V2:");
            System.out.println(simulation2.lbbaProgram.totReward);
            
               System.out.println("Tasks Made V2:");
            System.out.println(simulation2.tasksMade.count);
            
            
               System.out.println("Preemptions V2:");
            System.out.println(simulation2.lbbaProgram.preemptions);
            
        
               
               //~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~
               
            simulation3 = new Menu3(simulation1);
            startTime = System.currentTimeMillis();
            simulation3.run();

            endTime = System.currentTimeMillis();

            //  System.out.println("Milli-Seconds Elapsed:");
            // System.out.println(endTime-startTime);
            totTiming[2] = totTiming[2] + endTime - startTime;
            
            temp=(double) simulation3.lbbaProgram.totCompleted / ((double) simulation3.lbbaProgram.totCompleted + (double) simulation3.lbbaProgram.totFailed);
           temp=temp/ (double) 0.00000001;
            temp2=(int) temp;
            temp=temp2;
            temp=temp/100000000;
            totCompletionRate[2] = totCompletionRate[2] + temp;
            totReward[2] = simulation3.lbbaProgram.totReward /(double) numJobs + totReward[2];
            lcm[2] = simulation3.lcm + lcm[2] / (long) trials;
             tasks[2]=simulation3.tasksMade.count+tasks[2];
                preemptions[2]=preemptions[2]+  (double) simulation3.lbbaProgram.preemptions/(double) numJobs;
                    stretch[2]=stretch[2]+(double) simulation3.lbbaProgram.stretch;
         
                
//System.out.println(simulation3.lbbaProgram.preemptions);
             
                  //OUTPUT THE RESULTS FOR 1
             System.out.println("\n\nV3:");
             System.out.println("Completion rate V3:");
            System.out.println(temp);
            
               System.out.println("Reward Rate V3:");
            System.out.println(simulation3.lbbaProgram.totReward);
            
               System.out.println("Tasks Made V3:");
            System.out.println(simulation3.tasksMade.count);
            
            
               System.out.println("Preemptions V3:");
            System.out.println(simulation3.lbbaProgram.preemptions);
                
           
            
            //~~~~~~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~GLOBAL EDF
             simulation4 = new MenuGlobalEDF(simulation1);
            startTime = System.currentTimeMillis();
            simulation4.run();

            endTime = System.currentTimeMillis();

            //  System.out.println("Milli-Seconds Elapsed:");
            // System.out.println(endTime-startTime);
            totTiming[3] = totTiming[3] + endTime - startTime;
           
               temp=(double) simulation4.lbbaProgram.totCompleted / ((double) simulation4.lbbaProgram.totCompleted + (double) simulation4.lbbaProgram.totFailed);
            temp=temp/ (double) 0.00000001;
            temp2=(int) temp;
            temp=temp2;
            temp=temp/100000000;
             totCompletionRate[3] = totCompletionRate[3] + temp;
            lcm[3] = simulation4.lcm + lcm[3] / (long) trials;
            tasks[3]=simulation4.tasksMade.count+tasks[3];
               preemptions[3]=preemptions[3]+  (double) simulation4.lbbaProgram.preemptions/(double)numJobs;
                   stretch[3]=stretch[3]+(double) simulation4.lbbaProgram.stretch;
         totReward[3] = simulation4.lbbaProgram.totReward/(double) numJobs  + totReward[3];
         
//System.out.println(simulation2.lbbaProgram.preemptions);
            
                 //OUTPUT THE RESULTS FOR 1
             System.out.println("\n\nGLOBAL EDF:");
             System.out.println("Completion rate GLOBAL EDF:");
            System.out.println(temp);
            
                          
                    System.out.println("Reward Rate EDF:");
            System.out.println(simulation4.lbbaProgram.totReward);
            
               System.out.println("Preemptions GLOBAL EDF:");
            System.out.println(simulation4.lbbaProgram.preemptions);
           
            
             //~~~~~~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~EDFOS
              simulation5 = new MenuEDFOS(simulation1);
            startTime = System.currentTimeMillis();
            simulation5.run();

            endTime = System.currentTimeMillis();

            //  System.out.println("Milli-Seconds Elapsed:");
            // System.out.println(endTime-startTime);
            totTiming[4] = totTiming[4] + endTime - startTime;
           
               temp=(double) simulation5.lbbaProgram.totCompleted / simulation5.lbbaProgram.numJobs;
            temp=temp/ (double) 0.00000001;
            temp2=(int) temp;
            temp=temp2;
            temp=temp/100000000;
             totCompletionRate[4] = totCompletionRate[4] + temp;
            lcm[4] = simulation5.lcm + lcm[4] / (long) trials;
            tasks[4]=simulation5.tasksMade.count+tasks[4];
               preemptions[4]=preemptions[4]+  (double) simulation5.lbbaProgram.preemptions/(double)numJobs;
                   stretch[4]=stretch[1]+(double) simulation5.lbbaProgram.stretch;
         totReward[4] = simulation5.lbbaProgram.totReward/(double) numJobs  + totReward[4];

//System.out.println(simulation2.lbbaProgram.preemptions);
            
                 //OUTPUT THE RESULTS FOR 1
             System.out.println("\n\nEDF-OS:");
             System.out.println("Completion rate EDF-OS:");
            System.out.println(temp);
            
                          
                     System.out.println("Reward Rate EDF-OS:");
            System.out.println(simulation5.lbbaProgram.totReward);
            
            
               System.out.println("Preemptions ED:-OS");
            System.out.println(simulation5.lbbaProgram.preemptions);
            
            
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            
            
        }
        for (int i = 0; i < 5; i++) {
            meanTiming[i] = (double) totTiming[i] / (double) trials;
            meanReward[i] = (double) totReward[i] / (double) trials;
            meanCompletionRate[i] = (double) totCompletionRate[i] / (double) trials;
        }
   
            
        System.out.println("\nTotal Completion Rates are:");
        for (int i = 0; i < 5; i++) {
            System.out.println(totCompletionRate[i]);
        }

        System.out.println("\nMean LCM are:");
        for (int i = 0; i < 5; i++) {
            System.out.println(lcm[i]);
        }

        System.out.println("\nMean Rewards per Job:"); //
        for (int i = 0; i < 5; i++) {
            System.out.println(meanReward[i]); //mean rewards divided by mean number of jobs=mean rewadrd/job
        }

        System.out.println("\nMean Completion Rates are:");
        for (int i = 0; i < 5; i++) {
            System.out.println(meanCompletionRate[i]);
        }

        System.out.println("\nMean Timings are:");
        for (int i = 0; i < 5; i++) {
            System.out.println(meanTiming[i]);
        }
        System.out.println("\nMean Tasks are:");
        for (int i = 0; i < 5; i++) {
            System.out.println(tasks[i]/(double) trials);
        }
        
        System.out.println("\nMean Preemptions per Job:");
        for (int i = 0; i < 5; i++) {
            System.out.println(preemptions[i]/trials);
        }
        
        System.out.println("\nStretch");
       for (int i=0;i<5;i++)
       {
           
           System.out.println(this.stretch[i]/trials);   
       }
       
         System.out.println("\n\nOrder of Results"); 
        
           
           System.out.println("LBBA-bid Results");   
          System.out.println("LBBA-bnc Results"); 
           System.out.println("UBBA Results"); 
                 System.out.println("EDF Results"); 
                    System.out.println("EDF-os Results"); 
        
           

    }
    
    
    void runTest() {
        System.out.println("How many Processors?");
        Scanner sc = new Scanner(System.in);
        int numProcessors = sc.nextInt();

        System.out.println("\nHow many Trials?");
        int trials = sc.nextInt();

        this.trialTiming = new double[trials];
        lcmTrials = new double[trials];
        double numJobs=0;
        //V1

        long startTime, endTime;
        
          double idealUtilization=-1;
          System.out.println("\nWhat is your desired Utilization +/-0.05 ");
        while (idealUtilization>0.95 || idealUtilization<0.05) {
       
            idealUtilization = sc.nextDouble();
            
            if(idealUtilization<0.05 || idealUtilization>0.95)
            {
                System.out.println("Input is not allowed, must be between 0.05 and 0.95");   
                }
        
        }
        

        for (int i = 0; i < trials; i++) {
            
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
             System.out.println("\n\n Trial:");
            System.out.println(i);
            
            System.out.println("");
            simulation1 = new Menu(numProcessors,idealUtilization);
            startTime = System.currentTimeMillis();
            simulation1.run();

            endTime = System.currentTimeMillis();

            // System.out.println("Milli-Seconds Elapsed:");
            //System.out.println(endTime-startTime);
            //meanTiming[0]=meanTiming[0]+endTime-startTime; calculate l8tr sk8tr g8tr
            totTiming[0] = totTiming[0] + endTime - startTime;
            double temp=(double) simulation1.lbbaProgram.totCompleted / ((double) simulation1.lbbaProgram.totCompleted + (double) simulation1.lbbaProgram.totFailed);
            temp=temp/ (double) 0.00000001;
            int temp2=(int) temp;
            temp=temp2;
            temp=temp/100000000;
                   numJobs=simulation1.lbbaProgram.numJobs;
            totCompletionRate[0] = totCompletionRate[0] + temp;
            totReward[0] = simulation1.lbbaProgram.totReward/(double) numJobs + totReward[0];
            lcm[0] = simulation1.lcm + lcm[0] / (long) trials;
             tasks[0]=simulation1.tasksMade.count+tasks[0];
       
             
             preemptions[0]=preemptions[0]+  (double) simulation1.lbbaProgram.preemptions/(double)numJobs;
             stretch[0]=stretch[0]+simulation1.lbbaProgram.stretch;
            //System.out.println(simulation1.lbbaProgram.preemptions);
           
             //OUTPUT THE RESULTS FOR 1
             System.out.println("\n\nLCM IS:");
             System.out.println(simulation1.lcm);
             System.out.println("V1:");
             System.out.println("Completion rate V1:");
            System.out.println(temp);
            
               System.out.println("Reward Rate V1:");
            System.out.println(simulation1.lbbaProgram.totReward);
            
               System.out.println("Tasks Made V1:");
            System.out.println(simulation1.tasksMade.count);
            
            
               System.out.println("Preemptions V1:");
            System.out.println(simulation1.lbbaProgram.preemptions);
             
            if(temp<0.5)
            {
                System.out.println("\nERROR HERE I THINK!!!\n");   
            }
            
            
            
                        //~~~~~~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~GLOBAL EDF
             simulation4 = new MenuGlobalEDF(simulation1);
            startTime = System.currentTimeMillis();
            simulation4.run();

            endTime = System.currentTimeMillis();

            //  System.out.println("Milli-Seconds Elapsed:");
            // System.out.println(endTime-startTime);
            totTiming[3] = totTiming[3] + endTime - startTime;
           
               temp=(double) simulation4.lbbaProgram.totCompleted / ((double) simulation4.lbbaProgram.totCompleted + (double) simulation4.lbbaProgram.totFailed);
            temp=temp/ (double) 0.00000001;
            temp2=(int) temp;
            temp=temp2;
            temp=temp/100000000;
             totCompletionRate[3] = totCompletionRate[3] + temp;
            lcm[3] = simulation4.lcm + lcm[3] / (long) trials;
            tasks[3]=simulation4.tasksMade.count+tasks[3];
               preemptions[3]=preemptions[3]+  (double) simulation4.lbbaProgram.preemptions/(double)numJobs;
                   stretch[3]=stretch[3]+(double) simulation4.lbbaProgram.stretch;
         totReward[3] = simulation4.lbbaProgram.totReward/(double) numJobs  + totReward[3];
         
//System.out.println(simulation2.lbbaProgram.preemptions);
            
                 //OUTPUT THE RESULTS FOR 1
             System.out.println("\n\nGLOBAL EDF:");
             System.out.println("Completion rate GLOBAL EDF:");
            System.out.println(temp);
            
                          
                    System.out.println("Reward Rate EDF:");
            System.out.println(simulation4.lbbaProgram.totReward);
            
               System.out.println("Preemptions GLOBAL EDF:");
            System.out.println(simulation4.lbbaProgram.preemptions);
           
            
             //~~~~~~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~EDFOS
              simulation5 = new MenuEDFOS(simulation1);
            startTime = System.currentTimeMillis();
            simulation5.run();

            endTime = System.currentTimeMillis();

            //  System.out.println("Milli-Seconds Elapsed:");
            // System.out.println(endTime-startTime);
            totTiming[4] = totTiming[4] + endTime - startTime;
           
               temp=(double) simulation5.lbbaProgram.totCompleted / simulation5.lbbaProgram.numJobs;
            temp=temp/ (double) 0.00000001;
            temp2=(int) temp;
            temp=temp2;
            temp=temp/100000000;
             totCompletionRate[4] = totCompletionRate[4] + temp;
            lcm[4] = simulation5.lcm + lcm[4] / (long) trials;
            tasks[4]=simulation5.tasksMade.count+tasks[4];
               preemptions[4]=preemptions[4]+  (double) simulation5.lbbaProgram.preemptions/(double)numJobs;
                   stretch[4]=stretch[1]+(double) simulation5.lbbaProgram.stretch;
         totReward[4] = simulation5.lbbaProgram.totReward/(double) numJobs  + totReward[4];

//System.out.println(simulation2.lbbaProgram.preemptions);
            
                 //OUTPUT THE RESULTS FOR 1
             System.out.println("\n\nEDF-OS:");
             System.out.println("Completion rate EDF-OS:");
            System.out.println(temp);
            
                          
                     System.out.println("Reward Rate EDF-OS:");
            System.out.println(simulation5.lbbaProgram.totReward);
            
            
               System.out.println("Preemptions ED:-OS");
            System.out.println(simulation5.lbbaProgram.preemptions);
            
            
            //~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~
             
            simulation2 = new Menu2(simulation1);
            startTime = System.currentTimeMillis();
            simulation2.run();

            endTime = System.currentTimeMillis();

            //  System.out.println("Milli-Seconds Elapsed:");
            // System.out.println(endTime-startTime);
            totTiming[1] = totTiming[1] + endTime - startTime;
           
               temp=(double) simulation2.lbbaProgram.totCompleted / ((double) simulation2.lbbaProgram.totCompleted + (double) simulation2.lbbaProgram.totFailed);
            temp=temp/ (double) 0.00000001;
            temp2=(int) temp;
            temp=temp2;
            temp=temp/100000000;
            totCompletionRate[1] = totCompletionRate[1] + temp;
            totReward[1] = simulation2.lbbaProgram.totReward /(double) numJobs + totReward[1];
            lcm[1] = simulation2.lcm + lcm[1] / (long) trials;
            tasks[1]=simulation2.tasksMade.count+tasks[1];
               preemptions[1]=preemptions[1]+  (double) simulation2.lbbaProgram.preemptions/(double)numJobs;
            //System.out.println(simulation2.lbbaProgram.preemptions);
            stretch[1]=stretch[1]+(double) simulation2.lbbaProgram.stretch;
                 //OUTPUT THE RESULTS FOR 144
             System.out.println("\n\nV2:");
             System.out.println("Completion rate V2:");
            System.out.println(temp);
            
               System.out.println("Reward Rate V2:");
            System.out.println(simulation2.lbbaProgram.totReward);
            
               System.out.println("Tasks Made V2:");
            System.out.println(simulation2.tasksMade.count);
            
            
               System.out.println("Preemptions V2:");
            System.out.println(simulation2.lbbaProgram.preemptions);
            
        
               
               //~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~//~~~~~~~~~~~~~~~~~~~~~
               
            simulation3 = new Menu3(simulation1);
            startTime = System.currentTimeMillis();
            simulation3.run();

            endTime = System.currentTimeMillis();

            //  System.out.println("Milli-Seconds Elapsed:");
            // System.out.println(endTime-startTime);
            totTiming[2] = totTiming[2] + endTime - startTime;
            
            temp=(double) simulation3.lbbaProgram.totCompleted / ((double) simulation3.lbbaProgram.totCompleted + (double) simulation3.lbbaProgram.totFailed);
           temp=temp/ (double) 0.00000001;
            temp2=(int) temp;
            temp=temp2;
            temp=temp/100000000;
            totCompletionRate[2] = totCompletionRate[2] + temp;
            totReward[2] = simulation3.lbbaProgram.totReward /(double) numJobs + totReward[2];
            lcm[2] = simulation3.lcm + lcm[2] / (long) trials;
             tasks[2]=simulation3.tasksMade.count+tasks[2];
                preemptions[2]=preemptions[2]+  (double) simulation3.lbbaProgram.preemptions/(double) numJobs;
                    stretch[2]=stretch[2]+(double) simulation3.lbbaProgram.stretch;
         
                
//System.out.println(simulation3.lbbaProgram.preemptions);
             
                  //OUTPUT THE RESULTS FOR 1
             System.out.println("\n\nV3:");
             System.out.println("Completion rate V3:");
            System.out.println(temp);
            
               System.out.println("Reward Rate V3:");
            System.out.println(simulation3.lbbaProgram.totReward);
            
               System.out.println("Tasks Made V3:");
            System.out.println(simulation3.tasksMade.count);
            
            
               System.out.println("Preemptions V3:");
            System.out.println(simulation3.lbbaProgram.preemptions);
                
           
            

            
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            
            
        }
        for (int i = 0; i < 5; i++) {
            meanTiming[i] = (double) totTiming[i] / (double) trials;
            meanReward[i] = (double) totReward[i] / (double) trials;
            meanCompletionRate[i] = (double) totCompletionRate[i] / (double) trials;
        }
   
            
        System.out.println("\nTotal Completion Rates are:");
        for (int i = 0; i < 5; i++) {
            System.out.println(totCompletionRate[i]);
        }

        System.out.println("\nMean LCM are:");
        for (int i = 0; i < 5; i++) {
            System.out.println(lcm[i]);
        }

        System.out.println("\nMean Rewards per Job:"); //
        for (int i = 0; i < 5; i++) {
            System.out.println(meanReward[i]); //mean rewards divided by mean number of jobs=mean rewadrd/job
        }

        System.out.println("\nMean Completion Rates are:");
        for (int i = 0; i < 5; i++) {
            System.out.println(meanCompletionRate[i]);
        }

        System.out.println("\nMean Timings are:");
        for (int i = 0; i < 5; i++) {
            System.out.println(meanTiming[i]);
        }
        System.out.println("\nMean Tasks are:");
        for (int i = 0; i < 5; i++) {
            System.out.println(tasks[i]/(double) trials);
        }
        
        System.out.println("\nMean Preemptions per Job:");
        for (int i = 0; i < 5; i++) {
            System.out.println(preemptions[i]/trials);
        }
        
        System.out.println("\nStretch");
       for (int i=0;i<5;i++)
       {
           
           System.out.println(this.stretch[i]/trials);   
       }
       
        System.out.println("\n\nOrder of Results"); 
        
           
           System.out.println("LBBA-bid Results");   
          System.out.println("LBBA-bnc Results"); 
           System.out.println("UBBA Results"); 
                 System.out.println("EDF Results"); 
                    System.out.println("EDF-os Results"); 
           

    }
    
    

    
    
    
}
