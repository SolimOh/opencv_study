package com.odap.verifyer;

import java.awt.Dimension;

/**
 * 
 * ȭ�� ���̸� �������� �������� ���� �ϴ� �༮
 * 
 * @author famersbs
 *
 */
public class ImagePanelScaleCalc_atHeight extends ImagePanelScaleCalcer {
	
	public ImagePanelScaleCalc_atHeight(){
		
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
    		// scale ó��
        	Dimension size = screen_size;
        	
        	// ���� ���
        	if( size.height < image_size.getHeight() ){
        		// ���� ���� ���
        		double h = size.height;
        		double h1 = image_size.getHeight();

        		//  calc
        		scale = h / h1;
        		
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
