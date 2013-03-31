package jp.rutles.puzzle;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
//MediaPlayerクラスをインポート
import android.media.MediaPlayer;
import android.os.Bundle;

public class MusicPlay extends Activity{
	//MediaPlayerクラスの変数作成
	static MediaPlayer sound;

	//@Override
	//public void onCreate(Bundle savedInstanceState) {
	public void setMusicIns(Context c){
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		//保存した曲のインスタンス生成
		sound = MediaPlayer.create(c, R.raw.starlight);
		sound.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}
		
	//ループ再生
	public void startMusic(){
		sound.setLooping(true);
		sound.start();
	}
	
	//再生停止
	static void stopMusic(){
		if(sound.isPlaying()){
			sound.stop();
		}
	}
}