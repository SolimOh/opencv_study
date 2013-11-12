package com.odap.verifyer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

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
	private ImagePanelScaleCalcer	scaler = null;
	private ImagePanelNumberChooser chooser = null;
	private int curedit_number_idx = 0;
	
	private boolean isChanged = false;
	
    public ImagePanel(ImagePanelScaleCalcer imagePanelScaleCalcer) {
    	changeChooser( new ImagePanelNumberChooser_NoEdit() );
    	scaler = imagePanelScaleCalcer;
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
    /*
    private void alert( String msg ){
    	JOptionPane.showMessageDialog(null,msg,"Image Load Fail",JOptionPane.WARNING_MESSAGE);
    }
    */
    
    public void loadImage( String imgPath ){
    	
    	// load resource
    	resman = verifyResMan.create(imgPath);
		repaint();
		
		isChanged = false;
    }
    
    /**
     * ���� ȭ�� ������� ���Ͽ� scale �۾��� ��
     */
    private void CalcScale(){
    	
    	BufferedImage image = resman.getImage();
    	if( scaler.CalcScale( this.getSize(), new Dimension( image.getWidth(), image.getHeight() ) ) ) {
    		
    		double w2 = scaler.getScaledpos( image.getWidth() );
    		double h2 = scaler.getScaledpos( image.getHeight() );
    		
    		// ������ �̹��� �����
    		scaled_image = image.getScaledInstance( (int)w2, (int)h2, Image.SCALE_FAST);
    		
    	}
    	
    }
    
    /**
     * ������ ����
     * @param pos
     * @return
     */
    public int getScaledpos( int pos ){
    	return scaler.getScaledpos( pos );
    }
    
    /**
     * �� ��ǥ�迡�� ����ȯ
     * @param pos
     * @return
     */
    public int getreScalepos( int pos ){
    	return scaler.getreScalepos( pos );
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
		if( null != resman && isChanged ){
			resman.saveXML();
		}
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
			
			isChanged = true;
		}
	}

};