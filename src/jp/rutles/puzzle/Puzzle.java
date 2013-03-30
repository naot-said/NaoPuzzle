package jp.rutles.puzzle;

//import com.example.naopuzzle.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class Puzzle extends Activity {

	// 画面サイズ取得用
	public float disp_w; //横幅
	public float disp_h; //縦幅
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//ウィンドウタイトルバーを非表示に設定
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = getWindow();
		//フルスクリーンで表示
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//画面の管理情報呼び出し
		WindowManager manager = window.getWindowManager();
		Display disp = manager.getDefaultDisplay();
		disp_w = disp.getWidth();
		disp_h = disp.getHeight();
		
		//main.xmlをセット
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_puzzle_main, menu);
		return true;
	}

}
