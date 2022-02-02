import {
  Platform,
  NativeModules,
} from 'react-native';

const nativeModules = require('react-native').NativeModules;

const PlaySoundNative = Platform.OS === 'ios'
  ? nativeModules.PlaySound
  : NativeModules.PlaySound

const PlaySound = {
  playSound(soundPath, stream = "MUSIC") {
    return Platform.OS === 'ios'
      ? PlaySoundNative.playSound(soundPath)
      : PlaySoundNative.playSound(soundPath, stream)
  },
  stopSound() {
    PlaySoundNative.stopSound()
  },
  playSoundRepeat(soundPath) {
    PlaySoundNative.playSoundRepeat(soundPath)
  },
  playSoundMusicVolume(value) {
    Platform.OS === 'ios'
      ? PlaySoundNative.playSoundMusicVolume(value)
      : PlaySoundNative.playSoundMusicVolume(value, value)
  },
  setStreamVolume(value, stream = "MUSIC") {
    return Platform.OS === "ios"
      ? Promise.reject(new Error("Not supported"))
      : PlaySoundNative.setStreamVolume(value, stream)
  },
  adjustStreamMute(shouldMute, stream = "MUSIC") {
    Platform.OS === "android"
      ? PlaySoundNative.adjustStreamMute(shouldMute, stream)
      : null
  }
}

module.exports = PlaySound
