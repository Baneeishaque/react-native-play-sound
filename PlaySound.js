import {
  Platform,
  NativeModules,
} from 'react-native';

const { SoundModule } = NativeModules;
const nativeModules = require('react-native').NativeModules;

module.exports = {
  PlaySound : PlaySound = (sound, stream = "MUSIC") => {
    Platform.OS === 'ios'
      ? nativeModules.SoundModule.playSound(sound)
      : NativeModules.SoundManager.playSound(sound, stream)
  },
  StopSound : StopSound = () => {
    Platform.OS === 'ios'
      ? nativeModules.SoundModule.stopSound()
      : NativeModules.SoundManager.stopSound()
  },
  PlaySoundRepeat : PlaySoundRepeat = sound => {
  Platform.OS === 'ios'
    ? nativeModules.SoundModule.playSoundRepeat(sound)
    : NativeModules.SoundManager.playSoundRepeat(sound)
  },
  PlaySoundMusicVolume : PlaySoundMusicVolume = value => {
    Platform.OS === 'ios'
      ? nativeModules.SoundModule.playSoundMusicVolume(value)
      : NativeModules.SoundManager.playSoundMusicVolume(value, value)
  },
  PlaySoundStreamVolume : PlaySoundStreamVolume = (value, stream = "MUSIC") => {
    return Platform.OS === "ios"
      ? null
      : NativeModules.SoundManager.setStreamVolume(value, stream)
  }
};
