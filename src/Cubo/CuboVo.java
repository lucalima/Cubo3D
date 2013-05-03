/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cubo;

/**
 *
 * @author Lucas Lima
 */
public class CuboVo {

    private long codigo;
    private long tempo;
    private long click;
    private long x;
    private long y;
    private long z;

    public CuboVo(long tempo, long x, long y, long z) {
        super();
        this.tempo = tempo;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CuboVo() {
    }

    public long getTempo() {
        return tempo;
    }

    public void setTempo(long tempo) {
        this.tempo = tempo;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public long getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(long z) {
        this.z = z;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (tempo ^ (tempo >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CuboVo)) {
            return false;
        }
        CuboVo other = (CuboVo) obj;
        if (tempo != other.tempo) {
            return false;
        }
        return true;
    }

    /**
     * @return the codigo
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the click
     */
    public long getClick() {
        return click;
    }

    /**
     * @param click the click to set
     */
    public void setClick(long click) {
        this.click = click;
    }
}
