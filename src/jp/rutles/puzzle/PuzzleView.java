/**
 * 
 */
package jp.rutles.puzzle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @author nao
 *
 */
public class PuzzleView extends View {

	private static final int btn_x = 50; //ボタンの横位置
	private static final int btn_y = 768; //ボタンの縦位置
	private static final int btn_w = 390; //ボタンの横幅
	private static final int btn_h = 40; //ボタンの縦幅
	private static final int board_x = 40; //ゲーム版の横位置
	private static final int board_y = 126; //ゲーム版の縦位置
	private static final int score_x = 60; //テキストの表示横位置
	private static final int score_y = 73; //テキストの表示縦位置
	
	private Puzzle puzzle; //Puzzleクラス
	private PuzzleBoard board; //PuzzleBoardクラス
	private Drawable back,btn1,btn2; // 使用するイメージ
	private boolean btn_down,isPlaying; // ボタンの状態、プレイ中の状態
	private int pressX,pressY,upX,upY; //ボタンを押した時、離した時 
			
	public PuzzleView(Context context) {
		super(context);
		init(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public PuzzleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	private void init(Context context){
		puzzle = (Puzzle)context;
		Resources resources = context.getResources();
		back = resources.getDrawable(R.drawable.back);
		btn1 = resources.getDrawable(R.drawable.start);
		btn1.setBounds(btn_x, btn_y, btn_x + btn_w, btn_y + btn_h);
		btn2 = resources.getDrawable(R.drawable.start2);
		btn2.setBounds(btn_x, btn_y, btn_x + btn_w, btn_y + btn_h);
		
		Bitmap img = BitmapFactory.decodeResource(resources, R.drawable.image1);
		
		board = new PuzzleBoard(board_x, board_y, img);
		btn_down = false;
		isPlaying = false;
	}
	
	@Override
	protected void onDraw(Canvas c){
		c.drawColor(Color.BLACK);
		int w = this.getWidth();
		int h = this.getHeight();
		back.setBounds(0, 0, w, h);
		back.draw(c);
		board.draw(c);
		if (btn_down){
			btn2.draw(c);
		}else {
			btn1.draw(c);
		}
		Paint p = new Paint();
		p.setTextSize(30f);
		c.drawText("count: " + board.count, score_x, score_y, p);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int action = event.getAction();
		int x = (int)event.getX();
		int y = (int)event.getY();
		switch(action){
		case MotionEvent.ACTION_DOWN:
			pressX = x;
			pressY = y;
			if (isIn(x,y,btn1.getBounds())){
				isPlaying = true;
				btn_down = true;
				board.init();
				Toast toast = Toast.makeText(puzzle, "スタート!", Toast.LENGTH_LONG);
				toast.show();
			}
			break;
		case MotionEvent.ACTION_UP:
			btn_down = false;
			upX = x;
			upY = y;
			if (isPlaying) checkMove();
			break;
		}
		invalidate();
		return true;
	}
	
	public boolean isIn(int x, int y, Rect rect){
		return x > rect.left && x < rect.right && y > rect.top && y < rect.bottom;				
	}
	
	public void checkMove(){
		int dx = upX - pressX;
		int dy = upY - pressY;
		if (dx < -100) board.move(PuzzleBoard.WEST);
		if (dx > 100) board.move(PuzzleBoard.EAST);
		if (dy < -100) board.move(PuzzleBoard.NORTH);
		if (dy > 100) board.move(PuzzleBoard.SOUTH);
		if (board.checkFinish()){
			isPlaying = false;
			Toast toast = Toast.makeText(puzzle, "おめでとう!", Toast.LENGTH_LONG);
			toast.show();
		}
	}

}
