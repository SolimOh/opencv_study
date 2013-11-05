package com.odap.verifyer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author famersbs
 *
 */
public class ImagePanel extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private verifyResMan	resman = null;
	private Image			scaled_image = null;
	private Dimension		last_dim = null;
	private double			scale = 1;
	
	private int curedit_number_idx = 0; 
	
	private ImagePanelNumberChooser chooser = null;

    public ImagePanel() {
    	changeChooser( new ImagePanelNumberChooser_NoEdit() );
    }
    
    /**
     * Chooser ����
     * @param chooser
     */
    public void changeChooser( ImagePanelNumberChooser chooser ){
    	if( null != this.chooser ) {
    		this.chooser.endEdit();
    	}
    	this.chooser = chooser;
    	chooser.startEdit( this );
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
    public int getScaledpos( int pos ){
    	return (int) ( scale * pos );
    }
    
    /**
     * �� ��ǥ�迡�� ����ȯ
     * @param pos
     * @return
     */
    public int getreScalepos( int pos ){
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
        	
        	
        	// Draw chooser Mode
        	chooser.drawEdit( g );
        }
    }

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

	/**
	 * ���� ���õ� �ѹ� ���� ������ ���� �Ѵ�.
	 * @param rect
	 */
	public void setNumberRect(Rectangle rect) {
		if( 0 < curedit_number_idx && 6 > curedit_number_idx ){
			// �ϴ� ù��° �͸� �ٲ۴�.
			Rectangle[] rects = resman.getRects();
			
			rects[curedit_number_idx - 1].x		 	= rect.x;
			rects[curedit_number_idx - 1].y		 	= rect.y;
			rects[curedit_number_idx - 1].width	 	= rect.width;
			rects[curedit_number_idx - 1].height	= rect.height;
		}
	}

};