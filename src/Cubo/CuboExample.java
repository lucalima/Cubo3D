package Cubo;

import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.TreeSet;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author Lucas Lima
 */
public class CuboExample extends JFrame implements MouseMotionListener, MouseListener {

    private SimpleUniverse universe = null;
    long tempoInicial = 0;
    TreeSet<CuboVo> set = new TreeSet<CuboVo>(new PorTempo());
   
    // Construtor da classe GeometryExample
   
    public CuboExample() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas = new Canvas3D(config);
        container.add("Center", canvas);



        // Cria um sub-grafo de conte�do
        BranchGroup scene = criaGrafoDeCena();

        universe = new SimpleUniverse(canvas);



        // O c�digo abaixo faz com que a ViewPlatform seja movida
        // um pouco para tr�s, para que os objetos possam ser
        // visualizados
        ViewingPlatform viewingPlatform = universe.getViewingPlatform();

        viewingPlatform.setNominalViewingTransform();

        // O c�digo abaixo altera o field-of-view para 
        // permitir a visualiza��o de todos objetos
        View view = universe.getViewer().getView();
        view.setFieldOfView(view.getFieldOfView() * 1.4);

        // Adiciona "mouse behaviors" � "viewingPlatform" 
        // (equivale a trocar a posi��o do "observador virtual")
        OrbitBehavior orbit =
                new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);


        BoundingSphere bounds =
                new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        orbit.setSchedulingBounds(bounds);
        viewingPlatform.setViewPlatformBehavior(orbit);


        // Anexa o sub-grafo no universo virtual
        universe.addBranchGraph(scene);
        canvas.addMouseMotionListener(this);
        canvas.addMouseListener(this);
        setSize(350, 350);
        setVisible(true);

    }

    public boolean rotate(MouseEvent me) {
        OrbitBehavior or = new OrbitBehavior();
        or.getRotXFactor();
       // System.out.print(or);
        return true;

    }

    ///////////////////////////////////////////////////////////////////////
    // M�todo respons�vel pela cria��o do grafo de cena (ou sub-grafo)
    //    
    public BranchGroup criaGrafoDeCena() {
        // Cria o nodo raiz 
        BranchGroup objRaiz = new BranchGroup();
        objRaiz.setCapability(BranchGroup.ALLOW_BOUNDS_READ);

        // Cria o nodo TransformGroup e permite que ele possa
        // ser alterado em tempo de execu��o (TRANSFORM_WRITE).
        // Depois, adiciona-o na raiz do grafo de cena.
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRaiz.addChild(objTrans);

        // objRaiz.getLocale(addMouseMotionListener());

        // Cria um "bounds" para o background 
        BoundingSphere bounds =
                new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        // Especifica um background azul e adiciona-o no grafo
        Color3f bgColor = new Color3f(0.1f, 0.1f, 0.7f);
        Background bg = new Background(bgColor);

        bg.setApplicationBounds(bounds);
        objRaiz.addChild(bg);


        // Especifica as luzes do "ambiente" (ambiente e direcional)
        Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);
        AmbientLight luzAmb = new AmbientLight(corAmb);
        luzAmb.setInfluencingBounds(bounds);
        objRaiz.addChild(luzAmb);


        Color3f corLuz = new Color3f(0.9f, 0.9f, 0.9f);
        Vector3f direcaoLuz1 = new Vector3f(-1.0f, -1.0f, -1.0f);
        Vector3f direcaoLuz2 = new Vector3f(1.0f, -1.0f, -1.0f);
        DirectionalLight luzDir1 = new DirectionalLight(corLuz, direcaoLuz1);
        DirectionalLight luzDir2 = new DirectionalLight(corLuz, direcaoLuz2);
        luzDir1.setInfluencingBounds(bounds);
        luzDir2.setInfluencingBounds(bounds);
        objRaiz.addChild(luzDir1);
        objRaiz.addChild(luzDir2);


        // Especifica a apar�ncia do material
        Appearance app = new Appearance();
        Material material = new Material(new Color3f(0.7f, 0.1f, 0.7f),
                new Color3f(0.0f, 0.0f, 0.0f),
                new Color3f(0.7f, 0.1f, 0.7f),
                new Color3f(1.0f, 1.0f, 1.0f), 60.0f);//muda cor da letra
        app.setMaterial(material);




        Shape3D s3d = new Shape3D();
        s3d.setAppearance(app);
        s3d.setGeometry(cubeGeometry());
        objTrans.addChild(s3d);



        // Cria outro nodo TransformGroup node e permite que ele possa
        // ser alterado em tempo de execu��o (TRANSFORM_WRITE).
        // Depois, adiciona-o na raiz do grafo.
        TransformGroup textTrans = new TransformGroup();
        textTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRaiz.addChild(textTrans);


        // Cria um novo objeto que ir� aplicar as transforma��es
        // geom�tricas sobre texto e o adicina no grafo.
        Transform3D trans = new Transform3D();
        Transform3D t1 = new Transform3D();
        Vector3f vector = new Vector3f();

      /*  s3d.getLocalToVworld(t1);
        t1.get(vector);
        System.out.print(t1);*/

        Transform3D xRot = new Transform3D();
        Transform3D yRot = new Transform3D();
        Transform3D zRot = new Transform3D();
        Transform3D globalRot = new Transform3D();
        globalRot.mul(xRot, yRot);
        globalRot.mul(zRot);
        trans.mul(globalRot);
        textTrans.setTransform(trans);

        t1.rotX(Math.toRadians(-10.0));

        trans.mul(t1);
        trans.setScale(0.1);// muda o tamanho da palavra
        trans.setTranslation(new Vector3d(-0.5, 0.7, 0.0));

        textTrans.setTransform(trans);

        Font3D font3d = new Font3D(new Font("Helvetica", Font.PLAIN, 1),
                new FontExtrusion());
        Text3D textGeom = new Text3D(font3d, new String("  Lucas!"),
                new Point3f(-1.0f, 0.0f, 0.0f));
        Shape3D textShape = new Shape3D(textGeom);

        textShape.getLocalToVworld(trans);

        textShape.setAppearance(app);

        textTrans.addChild(textShape);

        // Para o Java 3D realizar otimiza��es no grafo de cena
        objRaiz.compile();

        return objRaiz;

    }

   
    private Geometry cubeGeometry() {
        GeometryInfo gi = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
        
        Point3f[] pts = new Point3f[8];
        pts[0] = new Point3f(-0.5f, 0.5f, 0.5f);
        pts[1] = new Point3f(0.5f, 0.5f, 0.5f);
        pts[2] = new Point3f(0.5f, -0.5f, 0.5f);
        pts[3] = new Point3f(-0.5f, -0.5f, 0.5f);
        pts[4] = new Point3f(-0.5f, 0.5f, -0.5f);
        pts[5] = new Point3f(0.5f, 0.5f, -0.5f);
        pts[6] = new Point3f(0.5f, -0.5f, -0.5f);
        pts[7] = new Point3f(-0.5f, -0.5f, -0.5f);
        int[] indices = {
            0, 4, 7, 3, // left face   
            6, 2, 3, 7, // bottom face 
            4, 5, 6, 7, // back face   
            5, 1, 2, 6, // right face  
            5, 4, 0, 1, // top face    
            1, 0, 3, 2 // front face 
        };
        ColorCube cc = new ColorCube(0.2);
        QuadArray geom = (QuadArray) cc.getGeometry();
       
        QuadArray quad = new QuadArray(24, QuadArray.COORDINATES);
        for (int i = 0; i < geom.getVertexCount(); i++) {
            double[] d = new double[3];
            geom.getCoordinate(i, d);
            quad.setCoordinate(i, d);
        }
        

        gi.setCoordinates(pts);
        gi.setCoordinateIndices(indices);
        NormalGenerator ng = new NormalGenerator();

        // Passar 100 como par�metro para a fun��o abaixo, faz com que
        // as "dobras" (uni�o das faces) fique mais suave do que se fosse  
        // passado um valor mais baixo
        ng.setCreaseAngle((float) Math.toRadians(100));
        //ng.setCreaseAngle( (float) Math.toRadians(45) ); 
        ng.generateNormals(gi);
        GeometryArray cube = gi.getGeometryArray();

        return cube;
    }

    // M�todo principal que permite executar a aplica��o
    //
    public static void main(String[] args) {
        CuboExample g = new CuboExample();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        CuboVo cuboVo = new CuboVo();
        cuboVo.setTempo((e.getWhen() - tempoInicial) / 1000);
        cuboVo.setX(e.getX());
        cuboVo.setY(e.getY());
        cuboVo.setZ(e.getX() ^ 2 + e.getY() ^ 2);
        set.add(cuboVo);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //  System.out.println("moveu");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("clicou");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        tempoInicial = System.currentTimeMillis();
        System.out.println("precionou");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("soltou");
        try {        
            percorreLista(set);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
       
    }

    public void percorreLista(TreeSet<CuboVo> set) throws SQLException, ClassNotFoundException {
        Iterator itr = set.iterator();
        while (itr.hasNext()) {
            CuboVo cuboVo = (CuboVo) itr.next();
            CuboBe be = new CuboBe();
            be.cadastrarCubo(cuboVo);
            System.out.println("Tempo = " + cuboVo.getTempo() + " X " + cuboVo.getX() + " Y " + cuboVo.getY() + " Z " + cuboVo.getZ());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Entrou");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Saiu");
    }
}