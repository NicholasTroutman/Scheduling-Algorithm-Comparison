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
public class Job { //acts as a node

    //UNique TO V2
    int jobNumber;
    int index;

    Job next;
    int  w, p, timeleft ;
    long s,r;
    double d; //benefit
    double utilization;
    
    //unique to EDF
    
    long deadline;
    
    //unique to EDFOS
    int identifier;
    
    
 //r=time made
    //w=workload
    //p=period
    //timeleft=timeleft=w (does not have to be passed)
    //s=time started (does not have to be passed)

    //constructors
    public Job() {

        next = null;

        d = 0;
        r = 0;
        w = 0;
    }

    public Job(Job newNext, int w2, long r2, int p2) {
        r = r2;
        w = w2;
        d = 0;
        p = p2;
        deadline=r2+p;
        s = -1;

        utilization = (double) w / (double) p;

        next = newNext;
        timeleft = w;
    }

    //accessors
    Job getNext() {
        return (next);
    }

    long getR() {
        return (r);
    }

     double Stretch(long t)
    {    
        return((t-r)/ (double) w);   
    
    }
    
    double getReward(long t) {
        double temp=t-r;
        
        //HERE IS WHERE WE WILL CHANGE THE REWARD RATE FUNCTION
        //RETURN ((double) w/(double (temp)); // 1/x function
        //RETURN ((double) w/(double (temp*2)); // 1/(x*2) function
        //RETURN ((double) w/(double (temp*temp)); // 1/(x^2) function
        return ((double) w / (double)(temp));

    }
    /*
  double getReward(long t) {
        return ((double) w / (2*(t - r)) );

    }    
    
      double getReward(long t) {
        return ((double) w / ((t - r)^2) );

    }   
    
      double getReward(long t) {
        return ((double) w / (2*(t - r)) );

    }   
    
    
    */

    int getW() {
        return (w);
    }

    double getD() {
        return (d);
    }

    //mutators
    
     //UNIQUE TO V2
    void setJobNumber(int num) {
        jobNumber = num;
    }
    
    
    void setNext(Job newNext) {
        next = newNext;
    }

    void setD(long t) {
        d = 1.0 / ((double) (t + w - r));

    }

    void setS(long t) {
        s = t;
    }

}
