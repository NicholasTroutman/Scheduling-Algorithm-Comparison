/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realtimemodel;

/**
 *
 * @author trout
 */
public class EDFOS {

    LinkedList TempListFixed, TempListMigrating;
    Processor[] servers;
    int numProcessors;
    int totCompleted, totFailed;
    double totReward;
    int preemptions;
    Task[] Tasks;
    double stretch;
    double numJobs;

    EDFOS(int numProcessors2) {
        this.numProcessors = numProcessors2;
        this.preemptions = 0;
        this.totReward = 0;
        stretch = 0;

        TempListFixed = new LinkedList();
        TempListMigrating = new LinkedList();

        servers = new Processor[numProcessors];

        //ADDED BECAUSE SOME CAN FAIL IN THE TEMPLIST
        totFailed = 0;

        for (int i = 0; i < numProcessors; i++) {
            servers[i] = new Processor();
            servers[i].utilization = 0;
            servers[i].counts = new int[20];
            for (int j = 0; j < 20; j++) {
                servers[i].counts[j] = 0;
            }
        }

    }

    void Assignment(Task[] taskList) {
        //Sort List wiht the biggest 
        quickSort(taskList, 0, taskList.length - 1); //sorts from largest-greatest, not dong that

        //INITIALIZE VARIABLES
        for (int i = 0; i < taskList.length; i++) {
            taskList[i].index = new int[numProcessors]; //which processor its on
            taskList[i].f = new double[numProcessors]; //amount on a processor
            taskList[i].lag = new double[numProcessors];
            taskList[i].numMigrations = 0;              //numMigrations
            for (int k = 0; k < numProcessors; k++) {
                taskList[i].f[k] = -1;
                taskList[i].index[k] = -1;
                taskList[i].lag[k] = -10;
            }

            taskList[i].identifier = i;
        }

        int i = 0;
        int j = 0;
        double tempUtilization = 0;

        while (i < taskList.length) {
            //assign utilization to tasks
            if (taskList[i].utilization <= (1 - servers[j].utilization) && taskList[i].index[0] < 0) {
                //there is room to add it to this processor
                servers[j].utilization = taskList[i].utilization;

                //change parameters to represent fixed status
                taskList[i].utilization = 0;
                taskList[i].fixed = true; //not migrating task
                taskList[i].index[0] = j;
                taskList[i].numMigrations = 1;
                taskList[i].f[j] = 1;

            } else if (taskList[i].utilization <= (1 - servers[j].utilization) && taskList[i].index[0] > -1) {
                //this will be a migrating task, that ends now.

                taskList[i].f[j] = (taskList[i].utilization) / tempUtilization; //should be accurate f

                int k = 0;  //get appropiate place in index to add to
                while (taskList[i].index[k] > -1) {
                    k++;
                    taskList[i].fixed = false;
                }
                taskList[i].index[k] = j;
                servers[j].utilization = servers[j].utilization + taskList[i].utilization;

                taskList[i].utilization = 0;
                taskList[i].fixed = false; //migrating task
                taskList[i].numMigrations++;

            } else if ((1 - servers[j].utilization == 0)) {
                //skip this processor, dont record its numMigrations or index
            } else if (taskList[i].utilization > (1 - servers[j].utilization)) {

                //this will be a migrating task, that fills the processor, but doesnt finish
                taskList[i].f[j] = (1 - servers[j].utilization) / tempUtilization; //should be accurate f

                int k = 0;  //get appropiate place in index to add to
                while (taskList[i].index[k] > -1) {
                    k++;
                }
                taskList[i].index[k] = j;
                taskList[i].utilization = taskList[i].utilization - (1 - servers[j].utilization);
                servers[j].utilization = 1;
                taskList[i].numMigrations++;

            }
            //Move processors
            j++;
            j = j % numProcessors; //cycle through

            if (taskList[i].utilization <= 0) { //maybe change after testing
                i++; //advance through
                if (i < taskList.length) {
                    tempUtilization = taskList[i].utilization;
                }

            }
        }

        this.Tasks = taskList;

        //this fixes the number of 
        for (i = 0; i < taskList.length; i++) {

            for (int k = 0; k < taskList[i].numMigrations; k++) {

                taskList[i].lag[taskList[i].index[k]] = 0;
            }

        }

    }

    void add(Job newTest) //TO BE FILLED
    {
this.numJobs++;
        if (this.Tasks[newTest.identifier].fixed == true) { //if this is a fixex task

            this.TempListFixed.add(newTest);
        } else if (this.Tasks[newTest.identifier].fixed == false) { // if this is a migrating task
            this.TempListMigrating.add(newTest);
        }

    }

    void event(long t) { //events once timer has reached limit.

        

        Job temp;
        if (this.TempListMigrating.front != null) { //if there are any new migrating tasks
            TempListMigrating.insertionSortIdentifier(this.TempListMigrating.front); //order the TempList, from smallest-largest

            while (this.TempListMigrating.front != null) {
                //TempList.calculateD(t);
                temp = TempListMigrating.remove();
                int assignedProcessor = this.getNextProcessor(temp);

                //add this task to the processor
                this.servers[this.Tasks[temp.identifier].index[assignedProcessor]].addToMigratingPool(temp);

            }
        }

        while (this.TempListFixed.front != null) {

            temp = TempListFixed.remove();
            this.servers[this.Tasks[temp.identifier].index[0]].pool.insertionSortDeadline(temp, t);

        }

        //add things to the processor now
        for (int i = 0; i < numProcessors; i++) {

            if (this.servers[i].executing.top != null) { //not empty, there is a job present
                //check for migrating tasks
                if (this.servers[i].migratingPool.front != null) {
                    //there are migrating Tasks, and something on the stack
                    if (this.Tasks[this.servers[i].executing.top.identifier].numMigrations < 2 || this.Tasks[this.servers[i].executing.top.identifier].fixed == true) { //executing is a fixed task
                        if (servers[i].migratingPool.front != null) {
                            //switch
                            temp = servers[i].executing.top;
                            servers[i].executing.top = servers[i].migratingPool.remove();
                            servers[i].pool.insertionSortDeadline(temp, t);
                            this.preemptions++;

                        }
                    } //if both migrating tasks
                    else if (this.servers[i].migratingPool.front.identifier < servers[i].executing.top.identifier) {
                        temp = servers[i].migratingPool.remove();
                        servers[i].executing.top = temp; //add to the EMPTY stack
                        this.preemptions++;
                    }
                } else if (this.servers[i].pool.front != null)//check for fixed tasks
                {
                    if (this.servers[i].pool.front.deadline < servers[i].executing.top.deadline) {
                        temp = servers[i].pool.remove();
                        servers[i].executing.top = temp; //add to the EMPTY stack
                        this.preemptions++;
                    }
                }

            } else if (this.servers[i].migratingPool.front != null) //it was empty, add now migrating
            {
                temp = servers[i].migratingPool.remove();
                servers[i].executing.top=temp;
                
                

            } else if (this.servers[i].pool.front != null) //executing was empty, and migratingPool was empty, add from fixed list
            {

                temp = servers[i].pool.remove();
                servers[i].executing.top = temp; //add to the EMPTY stack
            }

        }
    }

    int getNextProcessor(Job newJob) {
        //find which processor has the greatest lag
        if (Tasks[newJob.identifier].fixed == true) {
            return (0);

        } else {

            int processor = 0;
            int identifier = newJob.identifier;
            double tempLag = -10; //needs to be big at first, so that it chooses the 

            for (int i = 0; i < this.Tasks[newJob.identifier].numMigrations; i++) {
                
                this.Tasks[identifier].lag[this.Tasks[identifier].index[i]] = this.Tasks[identifier].lag[this.Tasks[identifier].index[i]]+ this.Tasks[identifier].f[this.Tasks[identifier].index[i]];
                double tempLag2 = this.Tasks[identifier].lag[this.Tasks[identifier].index[i]];

                if (tempLag2 > tempLag) { //tempLag will be 0 the first time, and thus smaller
                    tempLag = tempLag2;
                    processor = i;
                }
            }

            Tasks[identifier].lag[this.Tasks[identifier].index[processor]] = Tasks[identifier].lag[this.Tasks[identifier].index[processor]] - 1;
           return (processor);
        }
    }

    int GetSecondProcessor(Job newJob) {
        //find which processor has the 2nd greatest lag
        double tempF = -2;
        double tempSecond = -2;
        int processor2 = -1;
        int processor = -1;
        int identifier = newJob.identifier;

        for (int i = 0; i < this.Tasks[newJob.identifier].numMigrations; i++) {

            double tempF2 = this.Tasks[identifier].f[this.Tasks[identifier].index[i]];
            if (tempF2 > tempF) {
                tempSecond = tempF;
                processor2 = processor;
                tempF = tempF2;
                processor = this.Tasks[identifier].index[i];
            } else if (tempF2 < tempF && tempF2 > tempSecond) {
                tempSecond = tempF2;
                processor2 = this.Tasks[identifier].index[i];
            }
        }

        return (processor2);
    }

    void Ending(long t) //to be called after each second, decrements
    {     //check if one needs to be repleaced
        boolean check = false;
        for (int i = 0; i < numProcessors; i++) {

            //move deadlines of migrating tasks
            Job temp = servers[i].migratingPool.front;

            while (temp != null) {
                temp.deadline--;
                temp = temp.next;

            }
            //fixed tasks
            temp = servers[i].pool.front;
            while (temp != null) {
                temp.deadline--;
                temp = temp.next;

            }

            if (servers[i].checkDeadlineEDFOS(t) == true) { //if something empties
                check = true; //call event later
            }

        }//end of server num looop

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
        
        
       
        }

    //quicksort functions 
    private void quickSort(Task[] arr, int i, int j) {
        if (i < j) {
            int pos = partition(arr, i, j);
            quickSort(arr, i, pos - 1);
            quickSort(arr, pos + 1, j);
        }

    }

    int partition(Task[] arr, int i, int j) {
        double pivot = arr[j].utilization;
        int small = i - 1;
        for (int k = i; k < j; k++) {
            if (arr[k].utilization >= pivot) {
                small++;
                swap(arr, k, small);

            }

        }
        swap(arr, j, small + 1);
        // System.out.println("Pivot= "+arr[small+1]);
        // System.out.println(Arrays.toString(arr));
        return small + 1;

    }

    void swap(Task[] arr, int k, int small) {
        Task temp;
        temp = arr[k];
        arr[k] = arr[small];
        arr[small] = temp;

    }

}
