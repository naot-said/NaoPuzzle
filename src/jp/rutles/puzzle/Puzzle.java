package jp.rutles.puzzle;

//import com.example.naopuzzle.R;

import android.media.AudioManager;
import android.media.MediaPlayer;
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
	
	//MediaPlayerクラスの変数作成
	//private MediaPlayer sound;
		
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
		
		//音の再生
		//保存した曲のインスタンス生成
		//sound = MediaPlayer.create(this, R.raw.starlight);
		//sound.setAudioStreamType(AudioManager.STREAM_MUSIC);
		//ループ再生
		//sound.setLooping(true);
		//sound.start();
		MusicPlay mp = new MusicPlay();
		mp.setMusicIns(this);
		mp.startMusic();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_puzzle_main, menu);
		return true;
	}
	
	//Activity終了時イベント
	@Override
	public void onDestroy(){

		//音楽停止
		MusicPlay.stopMusic();
		
		super.onDestroy();
		finish();
	}

}
