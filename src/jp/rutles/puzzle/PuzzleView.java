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

	int gameImage0 = R.drawable.sonic;  //画像イメージ0
	int gameImage1 = R.drawable.image1;  //画像イメージ1
	int gameImage2 = R.drawable.shadow;  //画像イメージ2
	
	private static final float XPERIA_W = 480; //検証機のサイズ
	private static final float XPERIA_H = 854; //検証機のサイズ	
	private float btn_x = 47; //ボタンの横位置
	private float btn_y = 768; //ボタンの縦位置
	private float btn_w = 390; //ボタンの横幅
	private float btn_h = 40; //ボタンの縦幅
	private float board_x = 40; //ゲーム版の横位置
	private float board_y = 126; //ゲーム版の縦位置
	private float score_x = 60; //テキストの表示横位置
	private float score_y = 73; //テキストの表示縦位置
	
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
		setPuzzleSize();
	}
		// TODO 自動生成されたコンストラクター・スタブ
		private void init(Context context){
			puzzle = (Puzzle)context;
			btn_down = false;
			isPlaying = false;
		}
		
		private void setPuzzleSize(){
			float w = puzzle.disp_w;
			float h = puzzle.disp_h;
			float dw = w / XPERIA_W;
			float dh = h / XPERIA_H;
			
			btn_x = btn_x * dw;
			btn_y = btn_y * dh;
			btn_w = btn_w * dw;
			btn_h = btn_h * dh;
			board_x = board_x * dw;
			board_y = board_y * dh;
			score_x = score_x * dw;
			score_y = score_y * dh;

			Resources resources = puzzle.getResources();
			back = resources.getDrawable(R.drawable.back);
			back.setBounds(0, 0, (int)w, (int)h);
			btn1 = resources.getDrawable(R.drawable.start);
			btn1.setBounds((int)btn_x, (int)btn_y, (int)(btn_x + btn_w), (int)(btn_y + btn_h));
			btn2 = resources.getDrawable(R.drawable.start2);
			btn2.setBounds((int)btn_x, (int)btn_y, (int)(btn_x + btn_w), (int)(btn_y + btn_h));
			
			Bitmap img = BitmapFactory.decodeResource(resources,gameImage2); //ゲーム画像の表示
			board = new PuzzleBoard(board_x, board_y, dw, dh, img);
		}

		@Override
		protected void onDraw(Canvas c) {
			c.drawColor(Color.BLACK);
			int w = this.getWidth();
			int h = this.getHeight();
			back.setBounds(0, 0, w, h);
			back.draw(c);
			board.draw(c);
			if (btn_down){
				btn2.draw(c);
			} else {
				btn1.draw(c);
			}
			Paint p = new Paint();
			p.setTextSize(30f);
			c.drawText("count: " + board.count, score_x, score_y, p);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			int action = event.getAction();
			int x = (int)event.getX();
			int y = (int)event.getY();
			switch(action){
			case MotionEvent.ACTION_DOWN:
				pressX = (int)event.getX();
				pressY = (int)event.getY();
				if (isIn(x,y,btn1.getBounds())){
					btn_down = true;
					isPlaying = true;
					board.init();
					Toast toast = Toast.makeText(puzzle,
							"スタート！", Toast.LENGTH_LONG);
					toast.show();
				}
				break;
			case MotionEvent.ACTION_UP:
				btn_down = false;
				upX = (int)event.getX();
				upY = (int)event.getY();
				if (isPlaying) checkMove();
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			}
			invalidate();
			return true;
		}
		
		public boolean isIn(int x, int y,Rect rect){
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
				Toast toast = Toast.makeText(puzzle,
						"おめでとう！", Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}