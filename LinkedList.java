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
public class LinkedList {
//front-->mid-->back

    Job front, back;
    int count;

    public LinkedList() {
        count = 0;
        front = null;
        back = null;
    }

    void add(Job newProgram) {

        if (count < 1) {
            front = newProgram;
            back = newProgram;
            count = 1;

        } else if (count > 0) {
            back.setNext(newProgram);
            back = back.getNext();
            count++;
        }
    }

    void calculateD(long t) {
        Job pointer = front;
        if (pointer != null) {
            pointer.setD(t);
            while (pointer.getNext() != null) {
                pointer = pointer.getNext();
                pointer.setD(t);

            }
        }

    }

    Job remove() //basically popping
    { //we want to remove the back

        if (count == 2) {
            Job pointer = back;
            front.setNext(null);
            back = front;
            count = 1;
            return (pointer);
        } else {
            Job pointer = front;
            Job pointer2 = back;
            for (int i = 1; i < count - 1; i++) //arrives at 2nd 2 last node
            {
                pointer = pointer.getNext();
            }
            //pointer is the last node,
            //pointer 2 is 2nd to last node
            if (pointer != front) {
                back = pointer;
                back.setNext(null);
            } else if (pointer == front) { //count==2
                front = null;
                back = null;
            }

            count--;
            if (count < 0) {
                count = 0;
            }

            return (pointer2);
        }
    }


    /*

     void insertionSortD(TestProgram head,int t) {  //front is ideally passed
     if (head != null && head.next != null) {

     TestProgram newHead = new TestProgram(null, head.getW(), head.getR(), head.p);
     newHead.setD(t);
     TestProgram pointer = head.next;

     // loop through each element in the list
     while (pointer != null) {
     // insert this element to the new list

     TestProgram innerPointer = newHead;
     TestProgram next = pointer.next;

     if (pointer.getD() <= newHead.getD()) {
     TestProgram oldHead = newHead;
     newHead = pointer;
     newHead.next = oldHead;
     } else {
     while (innerPointer.next != null) {

     if (pointer.getD() > innerPointer.getD() && pointer.getD() <= innerPointer.next.getD()) {
     TestProgram oldNext = innerPointer.next;
     innerPointer.next = pointer;
     pointer.next = oldNext;
     }

     innerPointer = innerPointer.next;
     }

     if (innerPointer.next == null && pointer.getD() > innerPointer.getD()) {
     innerPointer.next = pointer;
     pointer.next = null;
     }
     }

     // finally
     pointer = next;
     }
        
 
   
    
     }

    
    
     TestProgram pointer=front;
     for (int i=1;i<count;i++)
     {
     pointer=pointer.getNext();
     }
     back=pointer;
     }
     */
    void insertionSortD(Job node, long t) {
        if (node != null) {
            this.calculateD(t);
            // Initialize sortedList as the first node.
            Job sortedList = node;
            node = node.next;
            sortedList.next = null;

            while (node != null) {
                // Advance the nodes.
                Job current = node;
                node = node.next;

                // Find where current belongs.
                if (current.getD() < sortedList.getD()) {
                    // Current is new sorted head.
                    current.next = sortedList;
                    sortedList = current;
                } else {
                    Job search = sortedList;
                    while (search.next != null && current.getD() > search.next.getD()) {
                        search = search.next;
                    }

                    // current goes after search.
                    current.next = search.next;
                    search.next = current;
                }
            }

            this.front = sortedList;
            Job temp = front;
            while (temp.next != null) {
                temp = temp.getNext();
            }
            back = temp;

        }

    }

    void insertionSortUtilization(Job node, long t) {
        if (node != null) {

            // Initialize sortedList as the first node.
            Job sortedList = node;
            node = node.next;
            sortedList.next = null;

            while (node != null) {
                // Advance the nodes.
                Job current = node;
                node = node.next;

                // Find where current belongs.
                if (current.utilization < sortedList.utilization) {
                    // Current is new sorted head.
                    current.next = sortedList;
                    sortedList = current;
                } else {
                    Job search = sortedList;
                    while (search.next != null && current.utilization > search.next.utilization) {
                        search = search.next;
                    }

                    // current goes after search.
                    current.next = search.next;
                    search.next = current;
                }
            }

            this.front = sortedList;
            Job temp = front;
            while (temp.next != null) {
                temp = temp.getNext();
            }
            back = temp;

        }

    }

    void insertionSortDeadline(Job node, long t) { //sorts from greatest to least. Just like i Wanted, 5,4,3,2,1 etc.
        if (node != null) {

            // Initialize sortedList as the first node.
            Job sortedList = node;
            node = node.next;
            sortedList.next = null;

            while (node != null) {
                // Advance the nodes.
                Job current = node;
                node = node.next;

                // Find where current belongs.
                if (current.deadline > sortedList.deadline) {
                    // Current is new sorted head.
                    current.next = sortedList;
                    sortedList = current;
                } else {
                    Job search = sortedList;
                    while (search.next != null && current.deadline < search.next.deadline) {
                        search = search.next;
                    }

                    // current goes after search.
                    current.next = search.next;
                    search.next = current;
                }
            }

            this.front = sortedList;
            Job temp = front;
            while (temp.next != null) {
                temp = temp.getNext();
            }
            back = temp;

        }

    }

    void insertionSortW(Job head) {
        if (head != null && head.next != null) {

            Job newHead = new Job(null, head.getW(), head.getR(), head.p);
            Job pointer = head.next;

            // loop through each element in the list
            while (pointer != null) {
                // insert this element to the new list

                Job innerPointer = newHead;
                Job next = pointer.next;

                if (pointer.getW() <= newHead.getW()) {
                    Job oldHead = newHead;
                    newHead = pointer;
                    newHead.next = oldHead;
                } else {
                    while (innerPointer.next != null) {

                        if (pointer.getW() > innerPointer.getW() && pointer.getW() <= innerPointer.next.getW()) {
                            Job oldNext = innerPointer.next;
                            innerPointer.next = pointer;
                            pointer.next = oldNext;
                        }

                        innerPointer = innerPointer.next;
                    }

                    if (innerPointer.next == null && pointer.getW() > innerPointer.getW()) {
                        innerPointer.next = pointer;
                        pointer.next = null;
                    }
                }

                // finally
                pointer = next;
            }

        }

    }

    void insertionSortIdentifier(Job head) {
        if (head != null && head.next != null) {

            Job newHead = new Job(null, head.getW(), head.getR(), head.p);
            Job pointer = head.next;

            // loop through each element in the list
            while (pointer != null) {
                // insert this element to the new list

                Job innerPointer = newHead;
                Job next = pointer.next;

                if (pointer.identifier <= newHead.identifier) {
                    Job oldHead = newHead;
                    newHead = pointer;
                    newHead.next = oldHead;
                } else {
                    while (innerPointer.next != null) {

                        if (pointer.identifier > innerPointer.identifier && pointer.identifier <= innerPointer.next.identifier) {
                            Job oldNext = innerPointer.next;
                            innerPointer.next = pointer;
                            pointer.next = oldNext;
                        }

                        innerPointer = innerPointer.next;
                    }

                    if (innerPointer.next == null && pointer.identifier > innerPointer.identifier) {
                        innerPointer.next = pointer;
                        pointer.next = null;
                    }
                }

                // finally
                pointer = next;
            }

        }

    }

}//end class
