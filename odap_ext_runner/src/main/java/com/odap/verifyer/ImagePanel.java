package com.odap.verifyer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author famersbs
 *
 */
public class ImagePanel extends JPanel implements MouseMotionListener, MouseListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private verifyResMan	resman = null;
	private Image			scaled_image = null;
	private Dimension		last_dim = null;
	private double			scale = 1;
	
	private int curedit_number_idx = 0; 

    public ImagePanel() {
    }
    
    /**
     * 
     * @param msg
     */
    private void alert( String msg ){
    	JOptionPane.showMessageDialog(null,msg,"Image Load Fail",JOptionPane.WARNING_MESSAGE);
    }
    
    public void loadImage( String imgPath ){
    	
    	// load resource
    	resman = verifyResMan.create(imgPath);
    	
    	addMouseListener(this);
    	addMouseMotionListener(this);

    	
		repaint();
    }
    
    /**
     * ���� ȭ�� ������� ���Ͽ� scale �۾��� ��
     */
    private void CalcScale(){
    	
    	// ����� ���� �Ǿ��� ��� ����� �ٽ� �Ѵ�.
    	if( null == last_dim || last_dim.equals( this.getSize() ) ){
    		// scale ó��
        	Dimension size = this.getSize();
        	
        	double w2 = 1;
    		double h2 = 1;
    		
    		BufferedImage image = resman.getImage();

    		
        	// ���� ���
        	if( size.height < image.getHeight() ){
        		// ���� ���� ���
        		double w = size.width;
        		double h = size.height;
        		double w1 = image.getWidth();
        		double h1 = image.getHeight();

        		//  calc
        		scale = h / h1;
        		w2 = scale * w1;
        		h2 = h;
        		
        		scaled_image = image.getScaledInstance( (int)w2, (int)h2, Image.SCALE_FAST);
        		
        	}else{
        		scaled_image = image;
        		scale = 1;
        	}
        	
    	}
    	
    }
    
    /**
     * ������ ����
     * @param pos
     * @return
     */
    private int getScaledpos( int pos ){
    	return (int) ( scale * pos );
    }
    
    /**
     * �� ��ǥ�迡�� ����ȯ
     * @param pos
     * @return
     */
    private int getreScalepos( int pos ){
    	return (int) ( pos / scale );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        if( null != resman ){
        	
        	CalcScale();
        	
        	g.drawImage(scaled_image, 0, 0, null); // see javadoc for more info on the parameters 
        	
        	g.setColor( new Color( 0x00FF00 ) );
        	
        	// put rect
        	Rectangle[] rects = resman.getRects();
        	for( int i = 0 ; i < rects.length ; ++ i ){
        		g.drawRect( getScaledpos( rects[i].x ),
        				 	getScaledpos(rects[i].y ),
        				 	getScaledpos(rects[i].width) ,
        				 	getScaledpos(rects[i].height) );
        	}
        	
        	// put editable mode
        	if( edit_enter ){
        		g.setColor( new Color( 0xFF0000) );
        		// ���� �ÿ��� �����Ͽ� �°� ���� �Ǿ�� �Ѵ�.
        		g.drawRect( edit_rect.x , 
        					edit_rect.y ,
        					edit_rect.width ,
        					edit_rect.height );
        	}
        }
    }

    
    
    // ���콺 ó��
    private boolean 	edit_enter = false;
    private Point		edit_spt = new Point();
    private Rectangle 	edit_rect = new Rectangle();
    
	public void mousePressed(MouseEvent e) {
		
		edit_spt.x = edit_rect.x = e.getX();
		edit_rect.width = 1;
		
		edit_spt.y = edit_rect.y = e.getY();
		edit_rect.height = 1;
		
		edit_enter = true;
		
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		edit_enter = false;
		
		if( 0 < curedit_number_idx && 6 > curedit_number_idx ){
			
			// �ϴ� ù��° �͸� �ٲ۴�.
			Rectangle[] rects = resman.getRects();
			
			rects[curedit_number_idx - 1].x		 	= getreScalepos( edit_rect.x );
			rects[curedit_number_idx - 1].y		 	= getreScalepos( edit_rect.y );
			rects[curedit_number_idx - 1].width	 	= getreScalepos( edit_rect.width );
			rects[curedit_number_idx - 1].height	= getreScalepos( edit_rect.height );
		
		}
		repaint();
	}
	public void mouseDragged(MouseEvent e) {
	
		if( edit_spt.x < e.getX() ){
			edit_rect.width = e.getX() - edit_rect.x;
			edit_rect.x = edit_spt.x;
		}
		else{
			edit_rect.x = e.getX();
			edit_rect.width = edit_spt.x - e.getX();
		}
		
		if( edit_spt.y < e.getY() ){
			edit_rect.height = e.getY() - edit_rect.y;
			edit_rect.y = edit_spt.y;
		}
		else{
			edit_rect.y = e.getY();
			edit_rect.height = edit_spt.y - e.getY();
		}
		
		repaint();
		
	}

	public void mouseMoved(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	/**
	 * Save Request 
	 */
	public void saveXML() {
		if( null != resman )
			resman.saveXML();
		
	}

	/**
	 * 
	 * @param i	 0 is no edit, 1 ~ 5 Number Edit.
	 */
	public void setEditNumber(int i) {
		curedit_number_idx = i;
	}



};