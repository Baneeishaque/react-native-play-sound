export type AndroidStream
    = "SYSTEM"
    | "MUSIC"
    | "NOTIFICATION"
    | "ALARM"
    | "RING"

export function playSound(soundPath: string, stream: AndroidStream): void

export function stopSound(): void

export function playSoundRepeat(soundPath: string): void

export function playSoundMusicVolume(value: number): void

/**
 * Android only. Set volume on the specified audio stream
 * @param value new volume level between 0 and 1
 * @param stream audio stream to use
 * @return the previously set volume
 */
export function setStreamVolume(value: number, stream: AndroidStream): Promise<number>

export function adjustStreamMute(shouldMute: boolean, stream: string): void

