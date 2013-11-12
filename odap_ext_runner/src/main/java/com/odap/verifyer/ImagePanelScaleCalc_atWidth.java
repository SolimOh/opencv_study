package com.odap.verifyer;

import java.awt.Dimension;

/**
 * 
 * ȭ�� ���̸� �������� �������� ���� �ϴ� �༮
 * 
 * @author famersbs
 *
 */
public class ImagePanelScaleCalc_atWidth extends ImagePanelScaleCalcer {

	private int f_width = 200;
	
	public ImagePanelScaleCalc_atWidth( int width ){
		f_width = width;
	}
	
	/**
     * ���� ȭ�� ������� ���Ͽ� scale �۾��� ��
     * 
     * @return true �����, false ������� ����
     */
	@Override
    public boolean CalcScale( Dimension screen_size, Dimension image_size ){
    	
    	// ����� ���� �Ǿ��� ��� ����� �ٽ� �Ѵ�.
    	if( null == last_dim || last_dim.equals( screen_size ) ){
    		
        	// ���� ���
        	if( f_width < image_size.getWidth() ){
        		// ���� ���� ���
        		double w = f_width;
        		double w1 = image_size.getWidth();

        		//  calc
        		scale = w / w1;
        		
        		return true;
        		
        	}else{
        		scale = 1;
        		return true;
        	}
        	
    	}else{
    		return false;
    	}
    	
    }
    
}
