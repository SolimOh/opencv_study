package com.odap.verifyer;

import java.awt.Dimension;

public class ImagePanelScaleCalcer {
	
	protected double			scale = 1;
	protected Dimension		last_dim = null;
	
	
	
	/**
     * ���� ȭ�� ������� ���Ͽ� scale �۾��� ��
     * 
     * @return true �����, false ������� ����
     */
    public boolean CalcScale( Dimension screen_size, Dimension image_size ){
    	
    	if( last_dim == null ) {
    		last_dim = screen_size;
    		return true;
    	}
    	return false;
    	
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
}
