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
public class Stack {

    int count;
    Job top, bottom;

    void add(Job newProgram, long t) {
        if (count > 0) {
            top.setNext(newProgram);
            top = top.getNext();
            count++;

            top.setD(t);
        } else if (count == 0) {
            top = newProgram;
            bottom = newProgram;
            count++;
        }
    }

    void remove() {

        if (count == 1) {
            count = 0;
            top = null;
            bottom = null;
        } else {
            Job ptr = bottom;
            count--;
            for (int i = 1; i < count - 1; i++) {
                ptr = ptr.getNext();
            }
            ptr.setNext(null);
            top = ptr; //gotta redo the top

        }
    }

}
