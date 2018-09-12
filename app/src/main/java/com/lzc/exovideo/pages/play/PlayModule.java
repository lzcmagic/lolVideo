package com.lzc.exovideo.pages.play;

import com.lzc.exovideo.di.ano.scope.SingleActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class PlayModule {

    @Binds
    abstract IPlayView bindPlayView(PlayActivity playActivity);

    @SingleActivity
    @Provides
    static PlayPresenter providerPresenter(PlayActivity playActivity) {
        return new PlayPresenter(playActivity);
    }
}

