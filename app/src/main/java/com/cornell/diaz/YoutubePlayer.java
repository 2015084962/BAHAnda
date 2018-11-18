package com.cornell.diaz;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayer extends YouTubeBaseActivity {

    private final String key = "AIzaSyAQxE_K0VoUsZXaD5Xm8rwCudBt-attzMg";
    private final String video_url = "ea1RJUOiNfQ";
    YouTubePlayerView player;
    YouTubePlayerFragment playerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstaid);

//        player = (YouTubePlayerView) findViewById(R.id.view);
//        player.initialize(key, new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                if(!b) {
//                    youTubePlayer.loadVideo(video_url);
//                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
//                }
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                Toast.makeText(MainActivity.this, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        playerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.fragmentyoutubeplayer);
        playerFragment.initialize(key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(video_url);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                //Toast.makeText(firstaid_activity.class, "Failed to play.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

