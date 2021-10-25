import {
  Platform,
  NativeModules,
} from 'react-native';

const {SoundModule} = NativeModules;
const nativeModules = require('react-native').NativeModules;

const PlaySoundNative = Platform.OS === 'ios'
  ? nativeModules.PlaySound
  : NativeModules.PlaySound

module.exports = {
  playSound: (sound, stream = "MUSIC") => {
    Platform.OS === 'ios'
      ? PlaySoundNative.playSound(sound)
      : PlaySoundNative.playSound(sound, stream)
  },
  stopSound: () => {
      PlaySoundNative.stopSound()
  },
  playSoundRepeat: sound => {
      PlaySoundNative.playSoundRepeat(sound)
  },
  playSoundMusicVolume: value => {
    Platform.OS === 'ios'
      ? PlaySoundNative.playSoundMusicVolume(value)
      : PlaySoundNative.playSoundMusicVolume(value, value)
  },
  playSoundStreamVolume: (value, stream = "MUSIC") => {
    return Platform.OS === "ios"
      ? null
      : PlaySoundNative.setStreamVolume(value, stream)
  }
};
