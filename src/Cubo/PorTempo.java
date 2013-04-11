/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cubo;

import java.util.Comparator;

/**
 *
 * @author Lucas Lima
 */
public class PorTempo implements Comparator<CuboVo> {

    @Override
    public int compare(CuboVo cuboVo1, CuboVo cuboVo2) {
        return ((Long)cuboVo1.getTempo()).compareTo(cuboVo2.getTempo());
    }
    
}
