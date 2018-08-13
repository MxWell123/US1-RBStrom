/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BVS;

/**
 *
 * @author davidecek
 */
public interface IData {
    public int porovnaj(IData data);   
    public Object getKluc();
    public Object dajTyp();       
}
