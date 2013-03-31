/**
 * 
 */
package jp.rutles.puzzle;

import java.util.Date;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author nao
 *
 */
public class PuzzleBoard {

	public static final int CENTER = 0; //移動方向を示す値
	public static final int NORTH = 1; //移動方向を示す値
	public static final int SOUTH = 2; //移動方向を示す値
	public static final int EAST = 3; //移動方向を示す値
	public static final int WEST = 4; //移動方向を示す値
	private Bitmap image; // 表示イメージ
	private float x,y; // ボードの表示位置
	private int[] data; //表示場所データを管理する配列
	public int place ; //現在の空きスペース位置
	private static final int row = 4;//ピースの縦位置の個数
	private static final int col = 4;//ピースの横位置の個数
	private float pW = 100f; //ピースの横幅
	private float pH = 150f; //ピースの高さ
	public int count = 0; //動かした回数
	
	public PuzzleBoard(float x, float y, float dw, float dh, Bitmap image){
		super();
		this.x = x;
		this.y = y;
		pW *= dw;
		pH *= dh;
		
		this.image = image;
		data = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,-1};
		
		place = 15; //ピースの数
	}
	
	public void init(){
		for (int i = 0; i < 200; i++){
			Random r = new Random(new Date().getTime());
			int a = r.nextInt(data.length);
			int b = r.nextInt(data.length);
			int val = data[a];
			data[a] = data[b];
			data[b] = val;
		}
		for (int i = 0; i < data.length; i++){
			if (data[i] == -1) {
				place = i;
			}
			count = 0;
		}
	}
	
	public void draw(Canvas canvas){
		int n = 0;
		for (int i = 0; i < row; i++){
			for (int j = 0; j < col; j++){
				int c = data[n] % col;
				int r = (int)(data[n] / col);
				if (data[n] != -1){
					canvas.drawBitmap(image, 
							new Rect((int)(x + c * pW), (int)(y + r * pH), (int)(x + c * pW + pW), (int)(y + r * pH + pH)),
							new Rect((int)(x + j * pW), (int)(y + i * pH), (int)(x + j * pW + pW), (int)(y + i * pH + pH)),
							new Paint());
				}
				n++;
			} 
		}
	}
	
	public void move(int move){
		int c = place % col;
		int r = (int)(place / col);
		int c2 = c;
		int r2 = r;
		switch(move){
		case NORTH:
			if (r2 < row - 1) r2 ++;
			break;
		case SOUTH:
			if (r2 > 0) r2--;
			break;
		case WEST:
			if (c2 < col - 1) c2++;
			break;
		case EAST:
			if (c2 > 0) c2--;
			break;
		}
		int n = data[r * col + c];
		data[r * col + c] = data[r2 * col + c2];
		data[r2 * col + c2] = n;
		for (int i = 0; i < data.length; i++){
			if (data[i] == -1) place = i;
		}
		count++;
	}
	
	public boolean checkFinish(){
		boolean flg = true;
		for (int i = 0; i < data.length - 1; i++){
			if (data[i] != i) flg = false;
		}
		return flg;
	}
}
