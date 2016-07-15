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
public class FinishedList2 { //compilation of a 

    int w;
    int p;
    int completionNum;

    FinishedList2(int w2, int p2) {
        w = w2;
        p = p2;
        completionNum = 0;
    }

    FinishedList2(Task2 pMaker) {
        w = pMaker.p;
        p = pMaker.w;
        completionNum = 0;
    }

    void SetCompletionNum(int newCompletionNum) {
        completionNum = newCompletionNum;
    }

}
