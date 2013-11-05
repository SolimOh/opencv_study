package com.odap.verifyer;

import java.awt.Graphics;

/**
 * Image Panel �� ���� ���ñ� �������̽� 
 * 
 * @author famersbs
 *
 */
public interface ImagePanelNumberChooser {

	/**
	 * �ɼ� ���ÿ�
	 * @param id
	 * @param val
	 */
	void setOption( String id, String val );
	
	/**
	 * ����Ʈ ��� ���� !
	 * @param panel
	 * @return
	 */
	boolean startEdit( ImagePanel panel );
	
	/**
	 * ����Ʈ ��� ���� !
	 * @return
	 */
	boolean endEdit( );
	
	/**
	 * Edit ��� �ߵ��� �̹��� �׸���
	 * @param g
	 */
	void	drawEdit( Graphics g );
	
}
