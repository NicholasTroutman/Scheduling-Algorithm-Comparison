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
public class Task { //this will assign test programs at certain time intervals

    int w, p;
    double utilization; // W/P. 
    Task next;
    
    //Used for EDF-OS
    int identifier;
    double [] f;
    int [] index;
    double [] lag;
    boolean fixed;
    int numMigrations;
    int jobCount;
    
    //r=time made
    //w=workload
    //p=period
    //d

    public Task(int p2, int w2) {
        p = p2;
        w = w2;
        utilization=(double) w/(double) p;
    }
   /* 
public Task(double utilization2)
{
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
                  newRand=33*newRand;
            newRand=(int) newRand;
            newRand=newRand+33;
            this.p=(int)newRand; //between 33-66
            
            
        }
        else if (rand==2)
        {
            double newRand= Math.random();
                  newRand=34*newRand;
            newRand=(int) newRand;
            newRand=newRand+66;
            this.p=(int)newRand; //between 67-100
            
        }
        
       double tempW=p*utilization;
       w=(int) tempW;
       
       if(w<1)
       {
        w=1;   
        utilization=p/w;
       }
       utilization=(double) w/(double) p;
    
    
    
}
    */
    public Task(double utilization2)
{
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
            newRand=7*newRand;
            newRand=(int) newRand;
            newRand=newRand+3;
            this.p=(int)newRand; //between 3-10
            
            
            
        }
        else if (rand==1)
        {//medium double newRand=Math.random();
          double newRand= Math.random();
                  newRand=10*newRand;
            newRand=(int) newRand;
            newRand=newRand+11;
            this.p=(int)newRand; //between 11-20
            
            
        }
        else if (rand==2)
        {
            double newRand= Math.random();
                  newRand=10*newRand;
            newRand=(int) newRand;
            newRand=newRand+20;
            this.p=(int)newRand; //between 21-30
            
        }
        
       double tempW=p*utilization;
       w=(int) tempW;
       
       if(w<1)
       {
        w=1;   
        utilization=p/w;
       }
       utilization=(double) w/(double) p;
    
    
    
}


    Job TestProgamMaker(long t) {
        Job newProgram = new Job(null, w, t, p);

        return (newProgram);

    }
    
    Job TestProgamMakerEDF(long t) {
        Job newProgram = new Job(null, w, t, p);

        return (newProgram);

    }

    
}