/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BVS;

import java.util.Comparator;

/**
 *
 * @author davidecek
 */
class CustomComparator<T> implements Comparator<Integer>{

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 - o2;
    }
    
}
