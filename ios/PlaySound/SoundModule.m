//
//  SoundModule.m
//
//  Created by Gheorghe Tomoiaga on 10/12/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "SoundModule.h"
#import <AudioToolbox/AudioToolbox.h>
#import "SoundManager.h"

@implementation SoundModule

RCT_EXPORT_MODULE(PlaySound);

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(playSound:(NSString *)soundPath)
{
  dispatch_async(dispatch_get_main_queue(), ^{
    NSString *soundUrl = [[NSBundle mainBundle] pathForResource:soundPath ofType:@"mp3"];
    [[SoundManager sharedManager] playMusic:soundUrl looping:NO];
  });
}

RCT_EXPORT_METHOD(stopSound)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        [[SoundManager sharedManager] stopMusic];
    });
}

RCT_EXPORT_METHOD(pauseSound)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        [[SoundManager sharedManager] pauseMusic];
    });
}

RCT_EXPORT_METHOD(resumeSound)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        [[SoundManager sharedManager] resumeMusic];
    });
}

RCT_EXPORT_METHOD(playSoundRepeat:(NSString *)soundPath)
{
  dispatch_async(dispatch_get_main_queue(), ^{
    NSString *soundUrl = [[NSBundle mainBundle] pathForResource:soundPath ofType:@"mp3"];
    [[SoundManager sharedManager] playMusic:soundUrl looping:YES];
  });
}

RCT_EXPORT_METHOD(playSoundMusicVolume:(float)value)
{
  dispatch_async(dispatch_get_main_queue(), ^{
    [[SoundManager sharedManager] setMusicVolume:value];
  });
}

@end

