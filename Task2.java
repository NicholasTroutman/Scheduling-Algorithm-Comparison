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
public class Task2 { //this will assign test programs at certain time intervals
//UNIQUE
    int jobNumber;
     int index;
    
    int w, p;
   
    //newest
    double utilization; //w/p
    Task2 next;
   
    
    //r=time made
    //w=workload
    //p=period
    //d

    public Task2(int jobNumber2,int index2, int w2, int p2, double u2, Task2 next2)
    {
        jobNumber=jobNumber2;
        index=index2;
        w=w2;
        p=p2;
        utilization=u2;
        next=next2;
        
    }
    
    
    //UNIQUE
    //add index
    public Task2(int p2, int w2, int index2) {
        p = p2;
        w = w2;
        jobNumber = 0;
        index = index2;
    }
    
        //UNIQUE ADD INDEX
 Task2(double utilization2, int index2)
{
    jobNumber=0;
    index=index2; //which number task it is
    
    utilization=utilization2;
    //random period number
    //light-->3-33
    //medium-->10-100
    //heavy-->50-250
        double rand= Math.random();
        rand=rand*100;
        rand=(int)rand;
        rand=rand%3;
        
        if(rand==0)
        {//light
            double newRand=Math.random();
            newRand=30*newRand;
            newRand=(int) newRand;
            newRand=newRand+3;
            this.p=(int)newRand; //between 3-33
            
            
            
        }
        else if (rand==1)
        {//medium double newRand=Math.random();
          double newRand= Math.random();
                  newRand=90*newRand;
            newRand=(int) newRand;
            newRand=newRand+10;
            this.p=(int)newRand; //between 10-100
            
            
        }
        else if (rand==2)
        {
            double newRand= Math.random();
                  newRand=200*newRand;
            newRand=(int) newRand;
            newRand=newRand+50;
            this.p=(int)newRand; //between 10-100
            
        }
        
       double tempW=p*utilization;
       w=(int) tempW;
       
       if(w<1)
       {
        w=1;   
        
       }
    utilization=(double) w/ (double) p;
    
    
}


    Job TestProgamMaker(long t) {
        Job newProgram = new Job(null, w, t, p);
        
        //unique
        jobNumber++;
        newProgram.setJobNumber(jobNumber);
        newProgram.index = this.index;
        
        return (newProgram);

    }

}
